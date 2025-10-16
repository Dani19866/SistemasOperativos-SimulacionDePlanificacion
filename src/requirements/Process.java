/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package requirements;

import structures.ProcessType;
import structures.StateProcess;

/**
 *
 * @author Daniel
 */
public class Process {

    private final PCB pcb;
    int totalInstructions;
    int remainingInstructions;

    // Si el proceso es I/O, debe especificarse cuantos ciclos se necesitan
    // para generar una excepción y cuantos para satisfacerla. También se debe
    // permitir la configuración de la duración de ciclo
    private int cycles;
    private int cycleDurantion;
    /*Chequear ^^^^^^^^^^^^^^^^^^^^^^6*/

    /* Creacion del construtor de proceso */
    /* LO HACEMOS PRIVADO, para que no se puedan llamar directamente*/
    public Process(int totalInstructions,PCB pcb) {
        if (totalInstructions <= 0) {
            throw new IllegalArgumentException("El total de instrucciones debe ser positivo.");
        }
        this.totalInstructions = totalInstructions;
        this.remainingInstructions = totalInstructions;
        this.pcb = pcb; 
    }
/*  
    /* Contructor publico para CPU-Bound
    public static Process createCpuBound(String nombre, int totalInstructions, int priority) {
        // Crea el PCB específico para CPU-Bound
        PCB pcb = PCB.createCpuBound(nombre, priority);
        return new Process(totalInstructions,pcb);
    }
    public static Process createIoBound(String nombre, int totalInstructions, int priority, int cyclesExcepcion, int cyclesCompleteIO) {
        // Crea el PCB específico para I/O-Bound
        PCB pcb = PCB.createIoBound(nombre, priority, cyclesExcepcion, cyclesCompleteIO);
        return new Process(totalInstructions,pcb);
    }
    
    */
    
    /* Ejecucion del proceso*/
     public synchronized void ExecuteIntructions(){
        if (!isFinished()){
            // actualizar contadores en el PCB 
            this.remainingInstructions--;
            pcb.increasePc();
            pcb.increaseMar();
        }
        if(isFinished()){
         pcb.setStateProcess(StateProcess.TERMINATED);
        }
     }
    
    /* Si el proceso termino, entonces se ejecuta esto*/
     public boolean isFinished() {
        return this.remainingInstructions <= 0;
        
    }
     
    // <editor-fold defaultstate="collapsed" desc="Getters"> 
    public PCB getPCB() {
        return pcb;
    }
    
    public int getRemainingInstructions() {
        return remainingInstructions;
    }
    
    public int gettotalInstructions(){
        return totalInstructions;
    }
    
    public int getExecutedInstructions(){
        return totalInstructions - remainingInstructions;
    }
    // </editor-fold> 

    
    // <editor-fold defaultstate="collapsed" desc="Setters"> 
    /* No deberia haber setters??
   
    */
    
    
    // </editor-fold> 

    
    
    
    
    
}