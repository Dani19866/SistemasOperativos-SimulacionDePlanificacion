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
    int cycleDuration;

    public OS(Memory memory, Disk disk, int cycleDuration) {
        this.cpu = new CPU();
        this.memory = memory;
        this.disk = disk;
        this.scheduler = new Scheduler(this.cpu.getRunningProcess());
        this.runningOS = true;
        this.cycleDuration = cycleDuration;
    }

    /**
     * El Sistema Operativo llama al planificador para que
     * añada el proceso en la cola de listos.
     * 
     * @param p
     */
    public void addProcess(Process p) {
        scheduler.addProcessScheduler(p);
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
}
