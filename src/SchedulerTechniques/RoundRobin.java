/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SchedulerTechniques;



import java.util.concurrent.Semaphore;
import requirements.CPU;

import structures.ArrayList;
import structures.Queue;
import requirements.Process;
import structures.ProcessType;

/**
 *
 * @author Daniel
 */
public class RoundRobin extends SchedulerStrategy{


     private final Semaphore mutex = new Semaphore(1);
    // Procesos en ejecución
    CPU cpu;
    


    // Colas de procesos
    Queue<Process> readyProcess;
    Queue<Process> readySuspendedProcess;
    Queue<Process> blockedProcess;
    Queue<Process> blockedSuspendedProcess;
    Queue<Process> newProcess;
    ArrayList<Process> outProcess;

   
     


    public RoundRobin(
            Queue<Process> readyProcess,
            Queue<Process> readySuspendedProcess,
            Queue<Process> blockedProcess,
            Queue<Process> blockedSuspendedProcess,
            Queue<Process> newProcess,
            ArrayList<Process> outProcess,

            CPU cpu
            
            

    ) {
        this.readyProcess = readyProcess;
        this.readySuspendedProcess = readySuspendedProcess;
        this.blockedProcess = blockedProcess;
        this.blockedSuspendedProcess = blockedSuspendedProcess;
        this.newProcess = newProcess;
        this.outProcess = outProcess;

        this.cpu =cpu;

    }

    @Override
    public Process nextProcess() {

    try {
        mutex.acquire();                      
        return readyProcess.isEmpty()
               ? null
               : readyProcess.dequeue();       
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();   
        return null;
    } finally {
        mutex.release();    
    }
}
    public void addProcess(Process p) {
        try {
            mutex.acquire();
            readyProcess.enqueue(p);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            mutex.release();
        }
    }
    
     public boolean isEmpty() {
        return readyProcess.isEmpty();
    }
}
    
