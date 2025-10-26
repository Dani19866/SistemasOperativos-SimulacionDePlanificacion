/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package requirements;

import SchedulerTechniques.FB;
import SchedulerTechniques.FirstComeFirstServe;
import SchedulerTechniques.SRT;
import SchedulerTechniques.RoundRobin;

import SchedulerTechniques.HRRN;
import SchedulerTechniques.SJF;
import SchedulerTechniques.SchedulerStrategy;
import SchedulerTechniques.StrategyScheduler;
import java.util.concurrent.Semaphore;
import structures.ArrayList;
import structures.Queue;
import requirements.Process;

/**
 *
 * @author Daniel
 */
public class Scheduler {

    // Procesos en ejecución
    CPU cpu;  // referencia a cpu
    // Colas de procesos
    Queue<Process> readyProcess;            // Cola a corto plazo
    Queue<Process> readySuspendedProcess;   // Cola a mediano plazo
    Queue<Process> blockedProcess;
    Queue<Process> blockedSuspendedProcess; // Cola a mediano plazo
    Queue<Process> newProcess;              // Cola a largo plazo
    ArrayList<Process> outProcess;

    // Estrategia actual
    SchedulerStrategy currentStrategy;
    StrategyScheduler typeStrategy;

    // Quantum (Tiempo max por proceso)
    int quantum;

    // Semáforo para proteger la cola de listos
    Semaphore mutex;

    
    

    /**
     * Constructor de la planificación
     *

     * @param cpu La instancia de la CPU
     */
    public Scheduler(CPU cpu) {
        this.cpu = cpu;

        // Inicializar cola de procesos
        this.readyProcess = new Queue<>();
        this.readySuspendedProcess = new Queue<>();
        this.blockedProcess = new Queue<>();
        this.blockedSuspendedProcess = new Queue<>();
        this.newProcess = new Queue<>();
        this.outProcess = new ArrayList<>();

        


        // Inicializar planificador por defecto: RoundRobin
        this.currentStrategy = new RoundRobin(
                this.readyProcess, this.readySuspendedProcess, this.blockedProcess,

                this.blockedSuspendedProcess, this.newProcess, this.outProcess, this.cpu
              

        );
        this.typeStrategy = StrategyScheduler.RoundRobin;

        // Relog y Quantum
        this.quantum = 0;

        // Inicializar semáforo
        this.mutex = new Semaphore(1);

        this.cpu = cpu;

    }

    /**
     * Cambia el proceso actual por otro
     *
     * @return 
     */
    public Process nextProcess() {
        return currentStrategy.nextProcess();
    }

    /**
     * Modifica la técnica del planificador.
     * Aquí es donde creamos el nuevo objeto de estrategia, "bajo demanda".
     *
     * @param strategyEnum
     */
    public void changeStrategy(StrategyScheduler strategyEnum) {
        // El switch se mueve aquí, que es su lugar lógico.
        // Crea el objeto SOLO cuando el usuario pide cambiar de estrategia.

        
        Process currentRunning = (this.cpu != null) ? this.cpu.getRunningProcess() : null;

        switch (strategyEnum) {
            case FB:
                currentStrategy = new FB(
                        this.readyProcess, this.readySuspendedProcess, this.blockedProcess,

                        this.blockedSuspendedProcess, this.newProcess, this.outProcess, this.cpu
                );
                this.typeStrategy = StrategyScheduler.FB;
            

                
            case FirstComeFirstServe:
                currentStrategy = new FirstComeFirstServe(
                        this.readyProcess, this.readySuspendedProcess, this.blockedProcess,

                        this.blockedSuspendedProcess, this.newProcess, this.outProcess, this.cpu
                );
                this.typeStrategy = StrategyScheduler.FirstComeFirstServe;
            

                
            case SRT:
                currentStrategy = new SRT(
                        this.readyProcess, this.readySuspendedProcess, this.blockedProcess,

                        this.blockedSuspendedProcess, this.newProcess, this.outProcess, this.cpu
                );
                this.typeStrategy = StrategyScheduler.SRT;
           

                
            case RoundRobin:
                currentStrategy = new RoundRobin(
                        this.readyProcess, this.readySuspendedProcess, this.blockedProcess,

                        this.blockedSuspendedProcess, this.newProcess, this.outProcess, this.cpu
                );
                this.typeStrategy = StrategyScheduler.RoundRobin;
            
            case HRRN:
                currentStrategy = new HRRN(
                        this.readyProcess, this.readySuspendedProcess, this.blockedProcess,
                        this.blockedSuspendedProcess, this.newProcess, this.outProcess, this.cpu
                );
                this.typeStrategy = StrategyScheduler.HRRN;
        
                
            case SJF:
                currentStrategy = new SJF(
                        this.readyProcess, this.readySuspendedProcess, this.blockedProcess,
                        this.blockedSuspendedProcess, this.newProcess, this.outProcess, this.cpu
                );
                this.typeStrategy = StrategyScheduler.SJF;
        }
    }

    /**
     * Añade un proceso nuevo a la cola de nuevos
     *
     * @param p
     */
    public void addProcessScheduler(Process p) {

            // es lo mismo que os.returnProcessReady

    }

    /**
     * Modifica el quantum del sistema operativo 
     * @param quantum
     */
    public void changeQuantum(int quantum) {
        this.setQuantum(quantum);
    }
    

    

    /**
     * Devuelve la estrategia que se está usando en ese momento 
     * @return 
     */
    public StrategyScheduler getStrategy(){
        return typeStrategy;
    }
    
    // <editor-fold defaultstate="collapsed" desc="Getters">
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Setters">
    private void setQuantum(int quantum) {
        this.quantum = quantum;
    }
    // </editor-fold>

}
