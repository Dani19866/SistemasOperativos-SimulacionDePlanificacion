/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package requirements;

import SchedulerTechniques.FirstComeFirstServe;
import SchedulerTechniques.RoundRobin;
import SchedulerTechniques.SRT;
import SchedulerTechniques.StrategyScheduler;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import structures.StateOS;
import structures.StateProcess;

/**
 *
 * @author Daniel
 */
public class CPU extends Thread {

    // Características
    Process runningProcess;
    int quantum;
    Semaphore mutex;

    // Referencias
    OS os;
    Scheduler scheduler;

    public CPU(OS os, Scheduler scheduler) {
        // Características
        this.runningProcess = null;
        this.quantum = 0;
        mutex = new Semaphore(0);

        // Referencias
        this.os = os;
        this.scheduler = scheduler;
    }

    /**
     * Alista un proceso | Llama a la función OS_addProcess(runningProcess)
     *
     */
    public void preemptProcess() {
        this.runningProcess.pcb.setStateProcess(StateProcess.READY);
        os.returnProcessReady(runningProcess);  //se cambio a returnprocesstoready
        this.freeProcess();
    }

    /**
     * Bloquea un proceso | Llama a la función de
     * OS_blockProcess(runningProcess)
     *
     */
    public void blockProcess() {
        this.runningProcess.pcb.setStateProcess(StateProcess.BLOCKED);
        this.os.blockProcess(runningProcess);
        this.freeProcess();
    }

    /**
     * Termina un proceso | Llama a la función OS_finishProcess(runningProcess)
     *
     */
    public void finishProcess() {
        this.runningProcess.pcb.setStateProcess(StateProcess.TERMINATED);
        os.finishProcess(runningProcess);
        this.freeProcess();
    }

    /**
     * Ejecución de un proceso | Extrae el siguiente proceso del planificador
     *
     */
    @Override
public void run() {
    while (os.os_status == StateOS.ON) {
        try {
            mutex.acquire(); // CPU duerme
            os.increaseCycles();
            Process p = os.nextProcess();
            
            while (p != null) {
                
                // --- Tu lógica de ejecución (¡esta parte está bien!) ---
                System.out.println("CPU " + p.getPCB().getId() + " ha iniciado.");
                System.out.println("CPU: Ejecutando " + p.pcb.name);
                this.runningProcess = p;
                p.pcb.setStateProcess(StateProcess.RUNNING);

                if (scheduler.getStrategy() == StrategyScheduler.RoundRobin) {
                    this.quantum = os.scheduler.quantum;
                }

                // 2. Bucle de ejecución de instrucciones
                while (this.runningProcess.getRemainingInstructions() > 0) {

                    Thread.sleep(os.globalCyclesDuration);
                    this.runningProcess.executeInstruction();
                    this.increaseGlobalCycle();

                    // 2.1. Condición de parada (Terminar o Bloquear)
                    if (this.runningProcess.isTerminated() || this.runningProcess.shouldBeBlocked()) {
                        break; // 
                    }

                    // 2.2. Condiciones de parada específicas de la política
                    boolean preempted = false; // Un flag para saber si fuimos expropiados
                    switch (scheduler.getStrategy()) {
                        case RoundRobin -> {
                            this.quantum--;
                            if (quantum <= 0) {
                                this.preemptProcess(); // Esto pone runningProcess = null
                                preempted = true;    // Levantamos el flag
                            }
                        }
                        case SRT -> {
                            Process nextP = os.scheduler.readyProcess.peek();
                            if (nextP != null && this.runningProcess.getRemainingInstructions() > nextP.getRemainingInstructions()) {
                                this.preemptProcess(); // Esto pone runningProcess = null
                                preempted = true;    // Levantamos el flag
                            }
                        }
                        default -> {
                            // No hacemos nada
                        }
                    }

                    // Si fuimos expropiados, rompemos el bucle también
                    if (preempted) {
                        break;
                    }

                }
                // Si 'runningProcess' NO es null, significa que NO fuimos expropiados.
                // Por lo tanto, salimos porque el proceso terminó o se bloqueó.
                if (this.runningProcess != null) {
                    if (this.runningProcess.isTerminated()) {
                        this.finishProcess(); // Ahora sí lo finalizamos
                    } else if (this.runningProcess.shouldBeBlocked()) {
                        this.blockProcess(); // Ahora sí lo bloqueamos
                    }
                }
                p = os.nextProcess();
            } 
            System.out.println("CPU: Cola 'Ready' vacía. Suspender...");
            
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            if (this.runningProcess != null) {
                this.preemptProcess();
            }
        }
    }
}
    /**
     * Incrementar ciclo global
     */
    
    public void increaseGlobalCycle() {
    os.increaseCycles(); 
    }

    /**
     * Liberar proceso de la CPU (runningProcess = null) | Uso privado de la
     * clase
     */
    private void freeProcess() {
        this.runningProcess = null;
    }
    public void wakeUp() {
        this.mutex.release();
    }

    // <editor-fold defaultstate="collapsed" desc="Getters">
    public Process getRunningProcess() {
        return runningProcess;
    }
    // </editor-fold> 

    // <editor-fold defaultstate="collapsed" desc="Setters">
    public void setRunningProcess(Process runningProcess) {
        this.runningProcess = runningProcess;
    }
    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }
    // </editor-fold>
}
