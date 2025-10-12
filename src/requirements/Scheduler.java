/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package requirements;

<<<<<<< HEAD
import SchedulerTechniques.FB;
import SchedulerTechniques.FirstComeFirstServe;
import SchedulerTechniques.Hibrid;
import SchedulerTechniques.RoundRobin;
import SchedulerTechniques.SPN;
import SchedulerTechniques.SRR;
import SchedulerTechniques.StrategyScheduler;
import structures.ArrayList;
import structures.Queue;

=======
>>>>>>> 72d9b4ef3f8d84445d4f18e02fcdeabdea4dad77
/**
 *
 * @author Daniel
 */
public class Scheduler {
<<<<<<< HEAD

    // Procesos en ejecución
    Process runningProcess;

    // Colas de procesos
    Queue<Process> readyProcess;            // Cola a corto plazo
    Queue<Process> readySuspendedProcess;   // Cola a mediano plazo
    Queue<Process> blockedProcess;
    Queue<Process> blockedSuspendedProcess; // Cola a mediano plazo
    Queue<Process> newProcess;              // Cola a largo plazo
    ArrayList<Process> outProcess;
    
    // Estrategias de planificacion
    FB retroalimentacionMultiNivel;
    FirstComeFirstServe primeroLlegadoPrimeroServido;
    Hibrid hibrido;
    RoundRobin ronda;
    SPN procesoMasCorto;
    SRR rondaEgoista;

    // Estrategia actual
    StrategyScheduler strategy;
    
    // Relog global
    long counter;
    
    // Quantum (Tiempo max por proceso)
    long quantum;

    public Scheduler() {
        // Inicializar cola de procesos
        this.readyProcess = new Queue<>();
        this.readySuspendedProcess = new Queue<>();
        this.blockedProcess = new Queue<>();
        this.blockedSuspendedProcess = new Queue<>();
        this.newProcess = new Queue<>();
        this.outProcess = new ArrayList<>();

        // Inicializar planificador por defecto: RoundRobin
        this.retroalimentacionMultiNivel = new FB(
                this.readyProcess,
                this.readySuspendedProcess,
                this.blockedProcess,
                this.blockedSuspendedProcess,
                this.newProcess,
                this.outProcess,
                this.runningProcess
        );
        this.primeroLlegadoPrimeroServido = new FirstComeFirstServe(
                this.readyProcess,
                this.readySuspendedProcess,
                this.blockedProcess,
                this.blockedSuspendedProcess,
                this.newProcess,
                this.outProcess,
                this.runningProcess
        );
        this.hibrido = new Hibrid(
                this.readyProcess,
                this.readySuspendedProcess,
                this.blockedProcess,
                this.blockedSuspendedProcess,
                this.newProcess,
                this.outProcess,
                this.runningProcess
        );
        this.ronda = new RoundRobin(
                this.readyProcess,
                this.readySuspendedProcess,
                this.blockedProcess,
                this.blockedSuspendedProcess,
                this.newProcess,
                this.outProcess,
                this.runningProcess
        );
        this.procesoMasCorto = new SPN(
                this.readyProcess,
                this.readySuspendedProcess,
                this.blockedProcess,
                this.blockedSuspendedProcess,
                this.newProcess,
                this.outProcess,
                this.runningProcess
        );
        this.rondaEgoista = new SRR(
                this.readyProcess,
                this.readySuspendedProcess,
                this.blockedProcess,
                this.blockedSuspendedProcess,
                this.newProcess,
                this.outProcess,
                this.runningProcess
        );

        // Inicializar estrategia por defecto
        this.strategy = StrategyScheduler.RoundRobin;
    }
    
    public void nextProcess() {
        switch (strategy) {
            case FB ->retroalimentacionMultiNivel.nextProcess();
            case FirstComeFirstServe -> primeroLlegadoPrimeroServido.nextProcess();
            case Hibrid -> hibrido.nextProcess();
            case RoundRobin -> ronda.nextProcess();
            case SPN -> procesoMasCorto.nextProcess();
            case SRR ->  rondaEgoista.nextProcess();
        }
    }

    /**
     * Modifica la técnica del planificador
     *
     * @param strategy
     */
    public void changeStrategy(StrategyScheduler strategy) {
        this.strategy = strategy;
    }
    
    public void addProcessScheduler(Process p){
        
    }
    
    public void changeQuantum(){
        
    }
}
=======
    
}
>>>>>>> 72d9b4ef3f8d84445d4f18e02fcdeabdea4dad77
