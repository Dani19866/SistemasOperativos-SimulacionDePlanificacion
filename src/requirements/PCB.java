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
    int priory;

   



    // Características de un I/O-Bound
    int cyclesExcepcion;        // Ciclos de un proceso antes de solicitar operación E/S
    int cyclesCompleteIO;       // Scheduler: Ciclos que el proecso permanecerá bloqueado
    int cyclesExecute;          // Contador para la ráfaga de CPU actual

    // Auditoría
    float timeProcess;          // Tiempo de procesador utilizado

    int tiempoLlegada;
    
    // --- NUEVOS CAMPOS PLANIFICACION PARA MLFQ (FB) ---
    private int prioridadMLFQ;      // Nivel de cola (1=Alta, 2=Media, 3=Baja)
    private boolean agotoQuantumMLFQ; // Flag de comunicación CPU -> Planificador


    /**
     * Constructor para procesos CPU-Bound
     *
     * @param name Nombre del proceso
     * @param processType Tipo de proceso
     * @param priory
     * @param memorySpace
     */

    public PCB(String name, ProcessType processType, int priory) {


        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.processType = processType;
        this.stateProcess = StateProcess.NEW;
        this.pc = 0;
        this.mar = 0;
        this.priory = priory;

        
        
        // --- INICIALIZACIÓN PARA MLFQ ---
        this.prioridadMLFQ = 0; // 0 = Proceso nuevo
        this.agotoQuantumMLFQ = false;

    }

    /**
     * Constructor para procesos I/O-Bound
     *
     * @param name
     * @param processType
     * @param cyclesExcepcion
     * @param cyclesCompleteIO
     * @param priory
     * @param memorySpace
     */

    public PCB(String name, ProcessType processType, int cyclesExcepcion, int cyclesCompleteIO, int priory) {

        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.processType = processType;
        this.stateProcess = StateProcess.NEW;
        this.pc = 0;
        this.mar = 0;
        this.priory = priory;

        


        // Asignamos los valores específicos para la E/S
        this.cyclesExcepcion = cyclesExcepcion;
        this.cyclesCompleteIO = cyclesCompleteIO;
        this.cyclesExecute = 0; // El contador siempre empieza en cero

        
        // --- INICIALIZACIÓN PARA MLFQ ---
        this.prioridadMLFQ = 0; // 0 = Proceso nuevo
        this.agotoQuantumMLFQ = false;


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

    /**
     * Incrementar program counter
     *
     * Si el procesos está RUNNING, se incrementa
     *
     */
    public void increasePc() {
        this.pc++;
    }

    /**
     * Incrementar Memory Address Register
     *
     * Si el procesos está RUNNING, se incrementa
     *
     */
    public void increaseMar() {
        this.mar++;
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


    
    public boolean haAgotadoQuantumMLFQ() {
        return agotoQuantumMLFQ;
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

    
     public int getTiempoLlegada() {
        return tiempoLlegada;
    }
    public int getPriory() {
        return priory;
        
    }
    
    public int getPrioridadMLFQ() {
        return prioridadMLFQ;
    }
    
     //
     //</editor-fold> 

    // <editor-fold defaultstate="collapsed" desc="Setters">
    public void setStateProcess(StateProcess stateProcess) {
        this.stateProcess = stateProcess;
    }

    public void setProcessType(ProcessType processType) {
        this.processType = processType;
    }

    public void setTiempoLlegada(int tiempoLlegada) {
        this.tiempoLlegada = tiempoLlegada;
    }
    
    public void setPriory(int priory){
        this.priory = priory;
    }
    
    
    
    public void setFlagAgotadoQuantumMLFQ(boolean agotoQuantumMLFQ) {
        this.agotoQuantumMLFQ = agotoQuantumMLFQ;
    }
    
    public void setPrioridadMLFQ(int prioridadMLFQ) {
        this.prioridadMLFQ = prioridadMLFQ;
    }
    //

    // </editor-fold> 
}
