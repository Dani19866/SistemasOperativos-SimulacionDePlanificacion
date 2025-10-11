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
import SchedulerTechniques.StrategyScheduler;
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

    // Estrategias de planificacion
    FB retroalimentacionMultiNivel;
    FirstComeFirstServe primeroLlegadoPrimeroServido;
    Hibrid hibrido;
    RoundRobin ronda;
    SPN procesoMasCorto;
    SRR rondaEgoista;

    // Estrategia actual
    StrategyScheduler strategy;

    public Scheduler() {
        // Inicializar cola de procesos
        this.readyProcess = new Queue<>();
        this.readySuspendedProcess = new Queue<>();
        this.blockedProcess = new Queue<>();
        this.blockedSuspendedProcess = new Queue<>();
        this.newProcess = new Queue<>();
        this.outProcess = new ArrayList<>();
        
        // Inicializar planificadores
        this.retroalimentacionMultiNivel = new FB();
        this.primeroLlegadoPrimeroServido = new FirstComeFirstServe();
        this.hibrido = new Hibrid();
        this.ronda = new RoundRobin();
        this.procesoMasCorto = new SPN();
        this.rondaEgoista = new SRR();

        // Inicializar estrategia por defecto
        this.strategy = StrategyScheduler.RoundRobin;
    }

    public void nextProcess() {
        
    }

    public void changeStrategy(StrategyScheduler strategy) {
        this.strategy = strategy;
    }
}
