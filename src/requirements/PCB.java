/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package requirements;

import java.util.UUID;
import structures.ProcessType;
import structures.StateProcess;

/**
 *
 * @author Daniel
 */
public class PCB {

    String id;
    String name;
    StateProcess stateProcess;
    ProcessType processType;
    int pc;
    int mar;

    // Características de un I/O-Bound
    int cyclesExcepcion;        // Ciclos de un proceso antes de solicitar operación E/S
    int cyclesCompleteIO;       // Ciclos que el proecso permanecerá bloqueado. Utilizar en el Scheduler
    int cyclesExecute;          // Contador para la ráfaga de CPU actual

    /**
     * Constructor para procesos CPU-Bound
     *
     * @param name Nombre del proceso
     * @param processType Tipo de proceso
     */
    public PCB(String name, ProcessType processType) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.processType = processType;
        this.stateProcess = StateProcess.NEW;
        this.pc = 0;
        this.mar = 0;
    }

    /**
     * Constructor para procesos I/O-Bound
     *
     * @param name
     * @param processType
     * @param cyclesExcepcion
     * @param cyclesCompleteIO
     */
    public PCB(String name, ProcessType processType, int cyclesExcepcion, int cyclesCompleteIO) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.processType = processType;
        this.stateProcess = StateProcess.NEW;
        this.pc = 0;
        this.mar = 0;

        // Asignamos los valores específicos para la E/S
        this.cyclesExcepcion = cyclesExcepcion;
        this.cyclesCompleteIO = cyclesCompleteIO;
        this.cyclesExecute = 0; // El contador siempre empieza en cero
    }

    /**
     * Revisa si el proceso debe ser bloqueado por una operación de E/S.
     * Incrementa el contador de ciclos en cada llamada.
     *
     * @return true 
     */
    public boolean blockForIO() {
        // Esta lógica solo aplica a procesos I/O-Bound
        if (this.processType != ProcessType.IO_BOUND) {
            return false;
        }

        // Si los ciclos ejecutados alcanzan el umbral, debe bloquearse
        this.cyclesExecute++;
        return this.cyclesExecute >= this.cyclesExcepcion;
    }

    /**
     * Reinicia el contador de la ráfaga de CPU.
     * 
     */
    public void restartCyclesExecuteIO() {
        this.cyclesExecute = 0;
    }

    // <editor-fold defaultstate="collapsed" desc="Getters">
    public String getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }

    public StateProcess getStateProcess() {
        return stateProcess;
    }

    public ProcessType getProcessType() {
        return processType;
    }

    public int getPc() {
        return pc;
    }

    public int getMar() {
        return mar;
    }

    public int getCyclesExcepcion() {
        return cyclesExcepcion;
    }

    public int getCyclesCompleteIO() {
        return cyclesCompleteIO;
    }

    public int getCyclesExecute() {
        return cyclesExecute;
    }
    // </editor-fold> 
    
    // <editor-fold defaultstate="collapsed" desc="Setters">
    public void setStateProcess(StateProcess stateProcess) {
        this.stateProcess = stateProcess;
    }

    public void setProcessType(ProcessType processType) {
        this.processType = processType;
    }

    public void setPc(int pc) {
        this.pc = pc;
    }

    public void setMar(int mar) {
        this.mar = mar;
    }
    // </editor-fold> 
}
