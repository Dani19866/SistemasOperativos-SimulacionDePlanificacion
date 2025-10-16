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
    private final int priority; /* Atributo NUEVO*/
    int pc;
    int mar;

    // Características de un I/O-Bound
    int cyclesExcepcion;        // Ciclos de un proceso antes de solicitar operación E/S
    int cyclesCompleteIO;       // Scheduler: Ciclos que el proecso permanecerá bloqueado
    int cyclesExecute;          // Contador para la ráfaga de CPU actual

    /**
     * Constructor para procesos CPU-Bound
     *
     * @param name Nombre del proceso
     * @param processType Tipo de proceso
     */
    private PCB(String name,int priority, ProcessType processType) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.priority = priority;
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
    private PCB(String name,int priority, ProcessType processType, int cyclesExcepcion, int cyclesCompleteIO) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.priority = priority;
        this.processType = processType;
        this.stateProcess = StateProcess.NEW;
        this.pc = 0;
        this.mar = 0;

        // Asignamos los valores específicos para la E/S
        this.cyclesExcepcion = cyclesExcepcion;
        this.cyclesCompleteIO = cyclesCompleteIO;
        this.cyclesExecute = 0; // El contador siempre empieza en cero
    }
/** Arriba hay un error logico, debemos validar que el proceso que entre al constructor para 
   i/o bound sea realmente un i/o boud*/
 
  /*  
   // MÉTODO PÚBLICO para CPU-Bound
    public static PCB createCpuBound(String name, int priority) {
        return new PCB(name,priority, ProcessType.CPU_BOUND); // Llama al primer constructor privado
    }

    // MÉTODO PÚBLICO para I/O-Bound
    public static PCB createIoBound(String name,int priority, int cyclesExcepcion, int cyclesCompleteIO) {
        return new PCB(name,priority, ProcessType.IO_BOUND, cyclesExcepcion, cyclesCompleteIO); // Llama al segundo constructor privado
    }
    
    */

    
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
     /**
     * Incrementar PC
     */
    public void increasePc() {
        pc++;
    }

    /**
     * Incrementar MAR
     */
    public void increaseMar() {
        mar++;
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
