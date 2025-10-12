/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package requirements;

/**
 *
 * @author Daniel
 */
public class CPU {
    int programCounter;
    Process runningProcess;
    int quantum;
    
    public CPU(){
        this.programCounter = 0;
        this.runningProcess = null;
        this.quantum = 0;
    }

    public Process getRunningProcess() {
        return runningProcess;
    }

    public void setRunningProcess(Process runningProcess) {
        this.runningProcess = runningProcess;
    }
}
