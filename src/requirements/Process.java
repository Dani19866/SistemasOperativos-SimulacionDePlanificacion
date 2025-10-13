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

    public PCB pcb;
    int instructions;
    int countInstructions;

    /**
     * Constructor para procesos CPU-Bound
     *
     * @param name
     * @param processType
     * @param instructions
     */
    public Process(String name, ProcessType processType, int instructions) {
        this.pcb = new PCB(name, processType);
        this.instructions = instructions;
        this.countInstructions = 0;
    }

    /**
     * Constructor para procesos I/O-Bound
     *
     * @param name
     * @param processType
     * @param cyclesExcepcion
     * @param cyclesCompleteIO
     * @param instructions
     */
    public Process(String name, ProcessType processType, int cyclesExcepcion, int cyclesCompleteIO, int instructions) {
        this.pcb = new PCB(name, processType, cyclesExcepcion, cyclesCompleteIO);
        this.instructions = instructions;
        this.countInstructions = 0;
    }

    /**
     * Ejecutar una instrucción del proceso.
     * Verifica si no está terminado
     * También setea automáticamente si ha llegado a sus instrucciones totales
     */
    public void executeInstruction() {
        // Verificar si no está terminado
        if (!this.isTerminated()) {
            
            // Incrementar Program Counter (PC) y Memory Address (MAR)
            this.pcb.increasePc();
            this.pcb.increaseMar();
            
            // Incrementar número de instrucciones
            this.countInstructions++;
            
            // Verificar si ha llegado a su punto de instrucciones
            if (this.instructions == this.countInstructions){
                this.pcb.setStateProcess(StateProcess.TERMINATED);
            }
        }
    }
    
    /**
     * Verifica si el proceso debe bloquearse por una operación de E/S
     * Solo es válido para procesos I/O-Bound
     * 
     * Básicamente simula una ráfaga de CPU.  Un proceso I/O-Bound se ejecuta 
     * por un número determinado de ciclos (ciclosExcepcion) y, al cumplirse, 
     * solicita una operación de E/S, lo que causa que se bloquee.
     * 
     * @return 
     */
    public boolean shouldBeBlocked(){
        return this.pcb.blockForIO();
    }
    
    /**
     * Comprobar si un proceso terminó
     * 
     * @return boolean
     */
    public boolean isTerminated() {
        return this.pcb.getStateProcess() == StateProcess.TERMINATED;
    }
    
    /**
     * Reinicia el contador de operaciones I/O
     * Se debe llamar a este método cuando el proceso sale de la cola de bloqueados.
     */
    public void restartBurstCounter() {
        this.pcb.restartCyclesExecuteIO();
    }
}
