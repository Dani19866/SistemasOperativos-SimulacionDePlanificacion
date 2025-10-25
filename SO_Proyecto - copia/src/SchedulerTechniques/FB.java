/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SchedulerTechniques;

import java.util.concurrent.Semaphore;
import structures.ArrayList;
import structures.Queue;
import requirements.Process;
import requirements.Scheduler;
import structures.ProcessType;

/**
 *
 * @author Daniel
 */
public class FB extends SchedulerStrategy{ 
    private final Semaphore mutex = new Semaphore(1);
    // Las 3 colas de listos, una para cada nivel de prioridad
    private Queue<Process> colaQ1; 
    private Queue<Process> colaQ2;
    private Queue<Process> colaQ3;
    
    // Procesos en ejecución
    Process runningProcess;

    // Colas de procesos
    Queue<Process> readyProcess;
    Queue<Process> readySuspendedProcess;
    Queue<Process> blockedProcess;
    Queue<Process> blockedSuspendedProcess;
    Queue<Process> newProcess;
    ArrayList<Process> outProcess;
    
    int quantumQ1;
    int quantumQ2;

    public FB(
            Queue<Process> readyProcess,
            Queue<Process> readySuspendedProcess,
            Queue<Process> blockedProcess,
            Queue<Process> blockedSuspendedProcess,
            Queue<Process> newProcess,
            ArrayList<Process> outProcess,
            Process runningProcess
    ) { //super(); // Llama al constructor de Planificador (que inicializa el mutex)
        this.readyProcess = readyProcess;
        this.readySuspendedProcess = readySuspendedProcess;
        this.blockedProcess = blockedProcess;
        this.blockedSuspendedProcess = blockedSuspendedProcess;
        this.newProcess = newProcess;
        this.outProcess = outProcess;
        this.runningProcess = runningProcess;
        this.colaQ1 = new Queue<>();
        this.colaQ2 = new Queue<>();
        this.colaQ3 = new Queue<>();
        this.quantumQ1 = 2;
        this.quantumQ2 = 4;
        
    }
    
    public void addProcess (Process p) {
       try{ 
           mutex.acquire();
          p.getPCB().setTiempoLlegada(getTimeGlobal()); 
          p.getPCB().setPrioridadMLFQ(1);
          p.getPCB().setStateProcess(structures.StateProcess.READY);
          colaQ1.enqueue(p); // Encolar en Q1
       }catch(InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            mutex.release();   
       }
    }
    
    
    //Decide dónde re-encolar un proceso que estaba en la CPU.
    public void returnProcessToReady(Process p) {
        try {
            mutex.acquire();
            p.getPCB().setStateProcess(structures.StateProcess.READY);
            
            
            boolean agotoQuantum = p.haAgotadoQuantum();
            int prioridadAnterior = p.getPrioridadMLFQ();
            
            if(agotoQuantum){
                // Cambiamos de prioridad el proceso 
                if (prioridadAnterior == 1) {
                    p.setPrioridadMLFQ(2); // Baja de prioridad a Q2
                    colaQ2.enqueue(p);
                } else if (prioridadAnterior == 2) {
                    p.setPrioridadMLFQ(3); // Baja de prioridad  a Q3
                    colaQ3.enqueue(p);
                } else {
                     colaQ3.enqueue(p); // 
                }
            }else{
                //No agoto quantum, vuelve a la cola Q1
                p.setPrioridadMLFQ(1);
                colaQ1.enqueue(p); 
            }
            // Limpiar el flag para la próxima vez
            p.resetFlagQuantum();
        }catch(InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            mutex.release();
        }
    }
    /**
     * Obtiene el siguiente proceso a ejecutar.
     * Siempre saca de la cola de mayor prioridad 
     */
    public Process nextProcess(){
        try{
            mutex.acquire();
            // 1. Alta Prioridad
            if (!colaQ1.isEmpty()) {
                return colaQ1.dequeue();
            }
            
            // 2. Prioridad Media
            if (!colaQ2.isEmpty()) {
                return colaQ2.dequeue();
            }
            
            // 3. Prioridad Baja
            if (!colaQ3.isEmpty()) {
                return colaQ3.dequeue();
            }
            
            // Todas las colas están vacías
            return null;
            
        }catch(InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        } finally {
            mutex.release();
        }
            
     }
    
        /**
         * El simulador necesita saber qué quantum aplicar.
         *@param p El proceso que se va a ejecutar.
         *@return El número de ticks (quantum) que este proceso puede usar.
         */
    
    public int getQuantumProcess(Process p) {
        int prioridad = p.getPrioridadMLFQ();
        if(prioridad == 1){
            return quantumQ1;
        } else if(prioridad== 2){
            return quantumQ2;
        } else {
            // Q3 es FCFS, por lo que su quamtun es infinito 
            // Devolvemos sus instrucciones restantes para que termine.
            return p.getRemainingInstructions(); 
        }
    }
    
        /**
         * Revisa las colas de la Politica de Planificacion (Q1,Q2,Q3)
        * @return 
         */
    public boolean isReadyEmpty() {
        try {
            mutex.acquire();
            return colaQ1.isEmpty() && colaQ2.isEmpty() && colaQ3.isEmpty();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return true;
        } finally {
            mutex.release();
        }
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
