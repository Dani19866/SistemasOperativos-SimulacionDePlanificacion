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

    private final CPU cpu;
    public Memory memory;
    private final Disk disk;
    private final Scheduler scheduler;

    public OS(Memory memory, Disk disk) {
        this.cpu = new CPU();
        this.memory = memory;
        this.disk = disk;
        this.scheduler = new Scheduler();
    }
    
    // El OS llama al planificador para que agregue el proceso
    public void addProcess(Process p){
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
