/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
// Shortest Job First
package SchedulerTechniques;

import java.util.concurrent.Semaphore;
import structures.ArrayList;
import structures.Queue;
import requirements.Process;
import structures.ProcessType;

/**
 *
 * @author Daniel
 */
public class SJF extends SchedulerStrategy{
    private final Semaphore mutex = new Semaphore(1);

    // Procesos en ejecución
    Process runningProcess;

    // Colas de procesos
    Queue<Process> readyProcess;
    Queue<Process> readySuspendedProcess;
    Queue<Process> blockedProcess;
    Queue<Process> blockedSuspendedProcess;
    Queue<Process> newProcess;
    ArrayList<Process> outProcess;

    public SJF(
            Queue<Process> readyProcess,
            Queue<Process> readySuspendedProcess,
            Queue<Process> blockedProcess,
            Queue<Process> blockedSuspendedProcess,
            Queue<Process> newProcess,
            ArrayList<Process> outProcess,
            Process runningProcess
    ) {
        this.readyProcess = readyProcess;
        this.readySuspendedProcess = readySuspendedProcess;
        this.blockedProcess = blockedProcess;
        this.blockedSuspendedProcess = blockedSuspendedProcess;
        this.newProcess = newProcess;
        this.outProcess = outProcess;
        this.runningProcess = runningProcess;
    }

    @Override
    public Process nextProcess() {
        try{
            mutex.acquire();
            if (readyProcess.isEmpty()) {
                return null;
            }
            Process bestProcess = null;
            Queue<Process> queueAuxSJF = new Queue<>();
            
            if(!readyProcess.isEmpty()){
                bestProcess = readyProcess.dequeue();
            }else{
                return null; // La cola esta vacia 
            }
            
            int sizereadyProcess = readyProcess.size();
            for (int i=0;i<sizereadyProcess;i++){
                Process actualProcess = readyProcess.dequeue(); // Desecolamos el proceso de la cola Listos
                
// Verificar esta fila !!!!!!
                if (actualProcess.getInstructions()<bestProcess.getInstructions()){ 
                    queueAuxSJF.enqueue(bestProcess);
                    bestProcess = actualProcess;
                }else{
                    queueAuxSJF.enqueue(actualProcess);
                }    
            }
            int sizequeueAuxSJF = queueAuxSJF.size();
            for (int j=0; j<sizequeueAuxSJF; j++){
                readyProcess.enqueue(queueAuxSJF.dequeue());
            }
            return bestProcess;
        }catch(InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        } finally{
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