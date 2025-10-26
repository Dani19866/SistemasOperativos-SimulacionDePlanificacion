/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package requirements;

import SchedulerTechniques.StrategyScheduler;
import java.util.Set;
import requirements.Process;
import structures.ProcessType;
import structures.StateOS;
import structures.StateProcess;

/**
 *
 * @author Daniel
 */
public class OS {

    CPU cpu;
    StateOS os_status;
    Scheduler scheduler;
    int globalCyclesDuration;
    int globalCycles;
    int currentMemoryUsage;
    int totalMemorySize;      // Límite de memoria (en instrucciones)
    int totalDiskSize;
    private boolean simulacionIniciada = false;

    public OS(int memorySize, int diskSize, int globalCyclesDuration) {
        this.totalMemorySize = memorySize;
        this.totalDiskSize = diskSize;
        this.cpu = new CPU(this, null);
        this.scheduler = new Scheduler(this.cpu);
        this.cpu.setScheduler(this.scheduler);
        this.os_status = StateOS.ON;
        this.globalCyclesDuration = globalCyclesDuration;
        this.globalCycles = 0;
        int currentMemoryUsage = 0;
    }

    /**
     * PLANIFICADOR: Siguiente proceso a ejecutar SEGÚN estrategia
     */
    public Process nextProcess() {
        return this.scheduler.nextProcess();
    }
    
    /**
     * PLANIFICADOR: Agregar proceso -> Cola de listos SEGÚN estrategia
     *

     * @param p
     */
    public void addProcess(Process p) {
        //scheduler.addProcessScheduler(p);
        // Asignamos el tiempo de llegada
        p.getPCB().setTiempoLlegada(this.globalCycles);
        p.getPCB().setStateProcess(StateProcess.NEW);
        scheduler.newProcess.enqueue(p);  // Colocamos el proceso en la cola de nuevos 
        
        this.checkAndLoadProcesses();   // Mueve de New a Ready si hay espacio 

    }
    /**
     * CPU llama cuando se necesita devolver un proceso que fue SUSPENDIDO.
     * @param p 
     */
    public void returnProcessReady(Process p ){
        p.getPCB().setStateProcess(StateProcess.READY);
        scheduler.readyProcess.enqueue(p); // encolamos a la cola de Listos 
        System.out.println("SO: Agregado a Ready -> " + p.getPCB().getName());
        this.tryToWakeUpCPU();
        
    }
    
    /**
     * Proceso -> Cola de bloqueados | Manejar bloqueo con un hilo
     *
     * @param p
     */
    public synchronized void blockProcess(Process p) {
        // 1. Modificar el estado del proceso a Bloqueado
        p.getPCB().setStateProcess(StateProcess.BLOCKED);
        // 2. Encolar el proceso a la cola de Bloqueados
        scheduler.blockedProcess.enqueue(p);
        System.out.println("SO: Proceso bloqueado -> " + p.getPCB().getName());
        // 3. Invocar un hilo para manejar aquellos procesos bloqueados
        // Espera de I/O
        Thread IOThread = new Thread(() -> {
            blockProcessHandler(p);
        });
        IOThread.start(); // Inicia el hilo
    }
        
        
    

    /**
     * Proceso -> Cola de terminados
     *
     * @param p
     */
    public void finishProcess(Process p) {
        // 1. Setear el estado del proceso en Terminado
        p.getPCB().setStateProcess(StateProcess.TERMINATED);
        // 2. Encolar en la lista de Bloqueados
        scheduler.outProcess.add(p);
        // 3. Liberar Memoria
        currentMemoryUsage -= p.getInstructions();
        System.out.println("OS (finishProcess): Proceso '" + p.getPCB().getName());
        
        //4. Avisamos al planificador que hay espacio disponible 
        this.checkAndLoadProcesses();
        
    }
    

    /**
     * Manejar bloqueo del proceso con HILOS | Añade el proceso con addProcess()
     *
     * @param p
     */
    public void blockProcessHandler(Process p) {
        try{
        // 1. Obtener los ciclos para completar el bloqueo
        int cyclesToWait = p.getPCB().getCyclesCompleteIO();
        // 2. Multiplicar ciclos por duración de ciclo = tiempo max bloqueo
        int TimeMaxBlock = (int) cyclesToWait * this.globalCyclesDuration;
        //3. Simulamos la espera
        Thread.sleep(TimeMaxBlock);
        // 4. Termino el proceso. Reiniciar el contador 
        p.restartBurstCounter();
        // 5. Desbloquear el proceso
        //Usamos synchronized, para que los procesos no accendan al mismo tiempo
        synchronized (this.scheduler) {
        // 6. Verificar si no está terminado
            if(p.getPCB().getStateProcess() == StateProcess.BLOCKED){
                // Si entra, significa que sigue en memoria.
                // Lo movemos de Blocked a Ready
            // a. Extraemos el proceso de la cola (descolar el proceso). 
            //    usamos metodo de queue -> remove()
                scheduler.blockedProcess.remove(p);
            //  b. Si no está terminado, modificar estado y añadir a listos
            if (!p.isTerminated()) {
                    p.getPCB().setStateProcess(StateProcess.READY);
                    this.scheduler.readyProcess.enqueue(p);
                }
            } else if(p.getPCB().getStateProcess() == StateProcess.SUSPENDED_BLOCKED){
                // Lo movemos de BLOCKED_SUSPENDED -> READY_SUSPENDED
                this.scheduler.blockedSuspendedProcess.remove(p);
                
                if (!p.isTerminated()) {
                        p.getPCB().setStateProcess(StateProcess.SUSPENDED_READY);
                        this.scheduler.readySuspendedProcess.enqueue(p);
                }
                
            }
        } // Fin del bloque synchronized
        
        }catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Hilo de E/S interrumpido para " + p.getPCB().getName());
            }
        this.tryToWakeUpCPU();
    }
    
    /**
     * Si hay espacio, intenta cargar los procesos a memoria 
     * desde las cola de suspendidos a listos 
     * Tambien carga los proceso a Listos
     * Se llama cuando un proceso TERMINA o cuando uno NUEVO llega.
    */
    private synchronized void checkAndLoadProcesses() {
        boolean memoryFree;
        do{
            memoryFree = false;
            
            if(!scheduler.readySuspendedProcess.isEmpty()){
                Process p = this.scheduler.readySuspendedProcess.peek();
                int neededMemory = p.getInstructions();
                
                // Si la cantidad de memoria que el proceos necesita mas la que
                // todavia se esta usando es menor o igual que el tamanio
                // total de la memoria
                if(currentMemoryUsage + neededMemory <= this.totalMemorySize){ 
                    // Hay espacio, se carga el proceso a memoria 
                   p = scheduler.readySuspendedProcess.dequeue();
                   currentMemoryUsage += neededMemory; //Actualizamos el contador de memoria usada
                   p.getPCB().setStateProcess(StateProcess.READY);
                   scheduler.readyProcess.enqueue(p);
                   memoryFree = true; // Cargamos un proceso 
                }
            }
                // No se reanudaron los procesos, cargamos nuevos procesos
                if (!memoryFree && !this.scheduler.newProcess.isEmpty()){
                    Process p = this.scheduler.newProcess.peek();
                    int neededMemory = p.getInstructions();
                    
                    if (this.currentMemoryUsage + neededMemory <= this.totalMemorySize) {
                        // Hay espacio, se carga 
                        p = this.scheduler.newProcess.dequeue();
                        this.currentMemoryUsage += neededMemory; //Actualizamos el contador de memoria usada
                        p.getPCB().setStateProcess(StateProcess.READY);
                        this.scheduler.readyProcess.enqueue(p);
                        memoryFree = true; // Cargamos un proceso 
                        
                } else {
                    // No hay espacio. Intentamos suspender a alguien.
                    //if (trySuspendBlockedProcess()) { FALTA ESTE METODO 
                        memoryFree = true;
                    }
                }
            } while (memoryFree); // Repetir mientras logremos mover procesos
            this.tryToWakeUpCPU();
    }
    
    /**
     * Intenta suspender el primer proceso en la cola de bloqueados
     * para liberar memoria.
     * Llamado por checkAndLoadProcesses() cuando no hay memoria.
     * * @return true si se logró suspender y liberar memoria.
     */
    private synchronized boolean trySuspendBlockedProcess() {
        if (this.scheduler.blockedProcess.isEmpty()) {
            return false; // No hay a quién suspender
        } 
        // 1. Sacamos al primer proceso bloqueado
        Process p = scheduler.blockedProcess.dequeue();
        
        // 2. Cambiamos su estado y lo movemos a la cola de suspendidos
        p.getPCB().setStateProcess(StateProcess.SUSPENDED_BLOCKED);
        scheduler.blockedSuspendedProcess.enqueue(p);
        
        // 3. Liberamos memoria!!!!!!
        this.currentMemoryUsage -= p.getInstructions();
        System.out.println("MEMORIA: El " + p.getPCB().getName() + " suspendido. Memoria liberada."); // Para verificar
        
        // Se Supone que este metodo es llamado checkAndLoadProcesses(). Una vez termine:
        // 4. El hilo que lo estaba esperando (blockProcessHandler) 
        //se encargará de moverlo a READY_SUSPENDED cuando termine su E/S.
        return true;
        
    }
     public void startSimulation() {
        this.cpu.start();
    }
    
    /**
     * Incrementa los ciclos del CPU
     */
    public void increaseCycles() {
        this.globalCycles++;
    }

    /**
     * Obtener el proceso que está corriendo actualmente
     *
     * @return
     */
    public Process getRunningProcess() {
        return cpu.runningProcess;
    }
    
    // <editor-fold defaultstate="collapsed" desc="Getters">
    public int getGlobalCyclesDuration() {
        return globalCyclesDuration;
    }

    public int getGlobalCycles() {
        return globalCycles;
    }

    public int getMemory() {
        return this.totalMemorySize;
    }
    
    public int getDisk(){
        return this.totalDiskSize;
    }

    public void getSpecifications() {
        System.out.println("Memoria RAM: " + getMemory() + " Kb" + "\nMemoria en disco: " + getDisk() + " Kb");

    }
    public synchronized void tryToWakeUpCPU() {
        // Solo despierta a la CPU si NO está ejecutando un proceso
        if (simulacionIniciada && cpu.getRunningProcess() == null) {
            System.out.println("OS: ¡Despertando a la CPU! (Trabajo nuevo en Ready)");
            this.cpu.wakeUp();
        }
        // Si ya está corriendo, no hacemos nada.
        // La CPU tomará el siguiente proceso cuando termine el actual.
    }
   
    
    // </editor-fold> 

    // <editor-fold defaultstate="collapsed" desc="Setters">
    public void setGlobalCyclesDuration(int globalCyclesDuration) {
        this.globalCyclesDuration = globalCyclesDuration;
    }
    /**
    * Método público (puente) para permitir que la GUI cambie
    * la estrategia de planificación del Scheduler.
    *
    * @param strategyEnum La nueva estrategia (enum) a configurar.
    */
   public void setSchedulingStrategy(StrategyScheduler strategyEnum) {
       if (this.scheduler != null) {
           // Llama al método que SÍ existe en tu Scheduler
           this.scheduler.changeStrategy(strategyEnum);
           
           if (!this.simulacionIniciada) {
            this.simulacionIniciada = true; // ¡Levantamos la bandera!
            System.out.println("--- Carga Completa. ---");
            } else {
                // Si ya estaba iniciada, solo notificamos el cambio (útil si cambias de estrategia a mitad de simulación)
                System.out.println("--- Estrategia de planificación actualizada a: " + strategyEnum.toString() + " ---");
            }
           tryToWakeUpCPU();
       }
   }
    // </editor-fold> 

}
