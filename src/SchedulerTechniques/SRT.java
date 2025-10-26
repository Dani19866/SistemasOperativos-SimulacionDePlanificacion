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
public class SRT extends SchedulerStrategy{

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

    public SRT(
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

        this.cpu = cpu;;

    }

    @Override
    public Process nextProcess() {

       try{
           mutex.acquire();
           if (readyProcess.isEmpty()){
               return null;
           }
           Process bestProcess = null;
           Queue<Process> queueAuxSRT =  new Queue<>(); //  Cola auxiliar temporal para SRT
           
           // Inicializamos el primer proceso de la cola listos como mejor proceso 
           if(!readyProcess.isEmpty()){
              bestProcess = readyProcess.dequeue();
           } else {
              return null; // La cola esta vacia. 
           }
           
           //Obtenemos la longitus de la cola de listos e iteramos sobre los procesos restantes 
           int sizereadyProcess = readyProcess.size();
           for (int i=0; i< sizereadyProcess;i++){
              Process actualProcess = readyProcess.dequeue();
              
              //Comparamos con el mejor proceso que corre actualmente 
              if (actualProcess.getRemainingInstructions()<bestProcess.getRemainingInstructions()){
                  queueAuxSRT.enqueue(bestProcess); // Ingresamos a la cola aux el mejor proceso
                  bestProcess = actualProcess;      // Actualizamos valores
              }else {
                  queueAuxSRT.enqueue(actualProcess);  // El actualProcess no es mejor que bestProcess, se encola.
              }
           }
           
           // Reencolamos todos los procesos de la cola aux a la cola Listo 
           int sizeAuxSRT = queueAuxSRT.size();
           for (int j=0; j< sizeAuxSRT; j++){
               readyProcess.enqueue(queueAuxSRT.dequeue());
           } 
            return bestProcess;
            
               
           
       }catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } 
        finally {
            mutex.release();
        }
        return null; //aqui chequear !!!!
    }
    
    public void addProcess(Process p) {
        try {
            mutex.acquire();
            readyProcess.enqueue(p); // Agrega el proceso al final de la cola
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