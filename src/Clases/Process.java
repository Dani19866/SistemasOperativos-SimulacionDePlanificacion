/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;
import java.util.Objects;



/**
 *
 * @author rtkn0_z8ls
 */
public class Process {
    private final PCB pcb;     /* Informacion estructural de proceso*/
    private long cpuCycleCounter;   /* Contador de ciclos de CPU totales*/
    private int cpuBurstCounter;    /* Contador de ráfagas de CPU -- RUNING*/
    private long ioCycleCounter;   /* Contador de ciclos de E/S totales*/
    private int ioBurstCounter;   /* Contador de ráfagas de E/S -- WAITING*/

    
/* Constructor de Process*/
    public Process (PCB pcb) {
        this.pcb = Objects.requireNonNull(pcb, "pcb");
    }
    
    public PCB getPcb() {
        return pcb;
    }

    public long getCpuCycleCounter() {
        return cpuCycleCounter;
    }

    public int getCpuBurstCounter() {
        return cpuBurstCounter;
    }

    public long getIoCycleCounter() {
        return ioCycleCounter;
    }

    public int getIoBurstCounter() {
        return ioBurstCounter;
    }
    
    public void registerCpuBurst(long cycles) {
        if (cycles < 0) {
            throw new IllegalArgumentException("cycles must be non-negative");
        }
        cpuBurstCounter++;
        cpuCycleCounter += cycles;
    }
    
    public void registerIoBurst(long cycles) {
        if (cycles < 0) {
            throw new IllegalArgumentException("cycles must be non-negative");
        }
        ioBurstCounter++;
        ioCycleCounter += cycles;
        pcb.incrementIoReads();
    }
    
     public void registerIoWrite() {
        pcb.incrementIoWrites();
    }

    public void transitionProcess(State nextState) {
        pcb.transitionTo(nextState);
    }

    public void terminateProcess() {
        pcb.terminate();
    }
      
}
