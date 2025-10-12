/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package requirements;

import SchedulerTechniques.FB;
import SchedulerTechniques.FirstComeFirstServe;
import SchedulerTechniques.Hibrid;
import SchedulerTechniques.RoundRobin;
import SchedulerTechniques.SPN;
import SchedulerTechniques.SRR;
import SchedulerTechniques.SchedulerStrategy;
import SchedulerTechniques.StrategyScheduler;
import java.util.concurrent.Semaphore;
import structures.ArrayList;
import structures.Queue;

/**
 *
 * @author Daniel
 */
public class Scheduler {

    // Procesos en ejecución
    Process runningProcess;

    // Colas de procesos
    Queue<Process> readyProcess;            // Cola a corto plazo
    Queue<Process> readySuspendedProcess;   // Cola a mediano plazo
    Queue<Process> blockedProcess;
    Queue<Process> blockedSuspendedProcess; // Cola a mediano plazo
    Queue<Process> newProcess;              // Cola a largo plazo
    ArrayList<Process> outProcess;

    // Estrategia actual
    SchedulerStrategy currentStrategy;

    // Relog global
    int counter;

    // Quantum (Tiempo max por proceso)
    int quantum;

    // Semáforo para proteger la cola de listos
    Semaphore mutex;

    /**
     * Constructor de la planificación
     * 
     * @param runningProcess Process of CPU
     */
    public Scheduler(Process runningProcess) {
        this.runningProcess = runningProcess;
        
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
                this.blockedSuspendedProcess, this.newProcess, this.outProcess, this.runningProcess
        );
        
        // Relog y Quantum
        this.counter = 0;
        this.quantum = 0;

        // Inicializar semáforo
        this.mutex = new Semaphore(1);
        this.runningProcess = runningProcess;
    }

    /**
     * Cambia el proceso actual por otro
     * 
     */
    public void nextProcess() {
        currentStrategy.nextProcess();
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
        switch (strategyEnum) {
            case FB -> currentStrategy = new FB(
                this.readyProcess, this.readySuspendedProcess, this.blockedProcess,
                this.blockedSuspendedProcess, this.newProcess, this.outProcess, this.runningProcess
        );
            case FirstComeFirstServe -> currentStrategy = new FirstComeFirstServe(
                this.readyProcess, this.readySuspendedProcess, this.blockedProcess,
                this.blockedSuspendedProcess, this.newProcess, this.outProcess, this.runningProcess
        );
            case Hibrid -> currentStrategy = new Hibrid(
                this.readyProcess, this.readySuspendedProcess, this.blockedProcess,
                this.blockedSuspendedProcess, this.newProcess, this.outProcess, this.runningProcess
        );
            case RoundRobin -> currentStrategy = new RoundRobin(
                this.readyProcess, this.readySuspendedProcess, this.blockedProcess,
                this.blockedSuspendedProcess, this.newProcess, this.outProcess, this.runningProcess
        );
            case SPN -> currentStrategy = new SPN(
                this.readyProcess, this.readySuspendedProcess, this.blockedProcess,
                this.blockedSuspendedProcess, this.newProcess, this.outProcess, this.runningProcess
        );
            case SRR -> currentStrategy = new SRR(
                this.readyProcess, this.readySuspendedProcess, this.blockedProcess,
                this.blockedSuspendedProcess, this.newProcess, this.outProcess, this.runningProcess
        );
        }
    }

    public void addProcessScheduler(Process p) {
    }

    public void changeQuantum() {
    }
}