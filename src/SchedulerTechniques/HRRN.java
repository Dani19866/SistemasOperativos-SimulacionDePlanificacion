/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//Highest Response Ratio Next
package SchedulerTechniques;

import java.util.concurrent.Semaphore;
import structures.ArrayList;
import structures.Queue;
import requirements.Process;
import structures.ProcessType;

/**
 *
 * @author Nicole
 */
public class HRRN extends SchedulerStrategy{
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

    public HRRN(
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
            
            double maxRatio = -1;
            Process select = null;
            Queue<Process> queueAuxHRRN = new Queue<>(); 
            
            int sizereadyProcess = readyProcess.size();
            for(int i=0;i<sizereadyProcess;i++){
                Process actualProcess = readyProcess.dequeue();
                double ratio = responseRatio(actualProcess);
                
                
                if(ratio > maxRatio){
                    maxRatio = ratio;
                    select = actualProcess;
                }
                queueAuxHRRN.enqueue(actualProcess);
            }
             // Re-encolar todos los procesos (excepto el seleccionado) de la cola auxiliar de vuelta a la cola de listos,
            // manteniendo el orden original relativo de los procesos no seleccionados.
            int sizequeueAux = queueAuxHRRN.size();
            for (int j=0;j<sizequeueAux;j++){
                Process processAux = queueAuxHRRN.dequeue();
                if (processAux != select){
                    readyProcess.enqueue(processAux);
                }
            }
            return select;   // Retornar el proceso seleccionado para ejecución
            
        }catch(InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        }finally {
            mutex.release();
        }
    }
    
    public void addProcess(Process p) {
        try {
            mutex.acquire();
            p.getPCB().setTiempoLlegada(getTimeGlobal());
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
    
      private double responseRatio(Process p) {
          int waitingTime = getTimeGlobal()- p.getPCB().getTiempoLlegada();
          int rafagaTime = p.getInstructions();
          return (waitingTime + rafagaTime)/ (double) rafagaTime;
      }
      
      
    private int timeGlobal = 0;

    public int getTimeGlobal() {
        return timeGlobal;
    }

    public void setTimeGlobal(int TimeGlobal) {
        this.timeGlobal = TimeGlobal;
    }

    
    public void incrementarTimeGlobal() {
        this.timeGlobal++;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
}