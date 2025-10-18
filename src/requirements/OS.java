/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package requirements;

import requirements.Process;

/**
 *
 * @author Daniel
 */
public class OS {

    CPU cpu;
    Memory memory;
    Disk disk;
    Scheduler scheduler;
    boolean runningOS;
    int globalCyclesDuration;
    int globalCycles;

    public OS(Memory memory, Disk disk, int globalCyclesDuration) {
        this.cpu = new CPU();
        this.memory = memory;
        this.disk = disk;
        this.scheduler = new Scheduler(this.cpu.getRunningProcess());
        this.runningOS = true;
        this.globalCyclesDuration = globalCyclesDuration;
        this.globalCycles = 0;
    }

    /**
     * Siguiente proceso a ejecutar
     */
    public void nextProcess() {
        this.scheduler.nextProcess();
    }
    
    /**
     * Proceso -> Cola de listos
     *
     * @param p
     */
    public void addProcess(Process p) {
        scheduler.addProcessScheduler(p);
    }
    
    /**
     * Proceso -> Cola de bloqueados | Manejar bloqueo con un hilo
     * @param p
     */
    public synchronized void blockProcess(Process p){
        // 1. Modificar el estado del proceso a Bloqueado
        // 2. Encolar el proceso a la cola de Bloqueados
        // 3. Invocar un hilo para manejar aquellos procesos bloqueados
    }
    
    /**
     * Proceso -> Cola de terminados
     * @param p
     */
    public void finishProcess(Process p){
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
