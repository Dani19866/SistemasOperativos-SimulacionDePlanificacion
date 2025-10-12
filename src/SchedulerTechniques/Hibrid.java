/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SchedulerTechniques;

import structures.ArrayList;
import structures.Queue;
import requirements.Process;

/**
 *
 * @author Daniel
 */
public class Hibrid {

    // Procesos en ejecución
    Process runningProcess;

    // Colas de procesos
    Queue<Process> readyProcess;
    Queue<Process> readySuspendedProcess;
    Queue<Process> blockedProcess;
    Queue<Process> blockedSuspendedProcess;
    Queue<Process> newProcess;
    ArrayList<Process> outProcess;

    public Hibrid(
            Queue<Process> readyProcess,
            Queue<Process> readySuspendedProcess,
            Queue<Process> blockedProcess,
            Queue<Process> blockedSuspendedProcess,
            Queue<Process> newProcess,
            ArrayList<Process> outProcess,
            Process runningProcess
    ) {
        this.readyProcess = readyProcess;
        this.readySuspendedProcess = readySuspendedProcess;
        this.blockedProcess = blockedProcess;
        this.blockedSuspendedProcess = blockedSuspendedProcess;
        this.newProcess = newProcess;
        this.outProcess = outProcess;
        this.runningProcess = runningProcess;
    }

    public void nextProcess() {

    }
}
