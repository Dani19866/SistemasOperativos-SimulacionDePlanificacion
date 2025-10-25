/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package requirements;

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
    Memory memory;
    Disk disk;
    Scheduler scheduler;
    int globalCyclesDuration;
    int globalCycles;
    int currentMemoryUsage;

    public OS(Memory memory, Disk disk, int globalCyclesDuration) {
        this.memory = memory;
        this.disk = disk;
        this.scheduler = new Scheduler(this.cpu.getRunningProcess());
        this.cpu = new CPU(this, this.scheduler);
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
    }
    
    
    /**
     * Proceso -> Cola de bloqueados | Manejar bloqueo con un hilo
     *
     * @param p
     */
    public synchronized void blockProcess(Process p) {
        // 1. Modificar el estado del proceso a Bloqueado
        // 2. Encolar el proceso a la cola de Bloqueados
        // 3. Invocar un hilo para manejar aquellos procesos bloqueados
    }

    /**
     * Proceso -> Cola de terminados
     *
     * @param p
     */
    public void finishProcess(Process p) {
        // 1. Setear el estado del proceso en Terminado
        // 2. Encolar en la lista de Bloqueados
    }

    /**
     * Manejar bloqueo del proceso con HILOS | Añade el proceso con addProcess()
     *
     * @param p
     */
    public void blockProcessHandler(Process p) {
        // 1. Obtener los ciclos para completar el bloqueo
        // 2. Multiplicar ciclos por duración de ciclo = tiempo max bloqueo
        // 3. Extraer el proceso de la cola (descolar el proceso)
        // 4. Reiniciar el contador de bloqueo de ese proceso
        // 5. Verificar si no está terminado
        //      a. Si no está terminado, entonces se modifica el estado (Ready)
        //         y se añade el proceso (addProcess)
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
                int neededMemory = p.getRemainingInstructions();
                
                // Si la cantidad de memoria que el proceos necesita mas la que
                // todavia se esta usando es menor o igual que el tamanio
                // total de la memoria
                if(currentMemoryUsage + neededMemory <= this.memory.memorySize.getSize()){ 
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
                    
                    if (this.currentMemoryUsage + neededMemory <= this.memory.memorySize.getSize()) {
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
        return memory.memorySize.getSize();
    }

    public int getDisk() {
        return disk.memorySize.getSize();
    }

    public void getSpecifications() {
        System.out.println("Memoria RAM: " + getMemory() + " Kb" + "\nMemoria en disco: " + getDisk() + " Kb");

    }
    // </editor-fold> 

    // <editor-fold defaultstate="collapsed" desc="Setters">
    public void setGlobalCyclesDuration(int globalCyclesDuration) {
        this.globalCyclesDuration = globalCyclesDuration;
    }
    // </editor-fold> 
}
