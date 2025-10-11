/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author rtkn0_z8ls
 */
public class PCB {
    private static final AtomicInteger idCounter = new AtomicInteger(0);
    private final int id; 
    private final String name;
    private ProcessType processType;
    private State state;
    private int pc; /* PROGRAM COUNTER*/
    private int MAR;/* MEMORY ADRESS REGISTER*/
    private int ioReadCounter;
    private int ioWriteCounter;
    
/* Constructor de PCB*/
    
    public PCB (int id, String name,ProcessType processType) {
        if (id < 0) {
            throw new IllegalArgumentException("EL ID DEL PROCESO DEBE SER NO NEGATIVO");
        }
        this.id = idCounter.getAndIncrement();
        this.name = Objects.requireNonNull(name, "name");
        this.processType = Objects.requireNonNull(processType, "processType");
        this.state = State.NEW;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ProcessType getProcessType() {
        return processType;
    }

    public State getState() {
        return state;
    }

    public int getpc() {
        return pc;
    }

    public int getmar() {
        return MAR;
    }

    public int getIoReadCounter() {
        return ioReadCounter;
    }

    public int getIoWriteCounter() {
        return ioWriteCounter;
    }

 
    public void setpc(int pc) {
        this.pc = pc;
    }

    public void setmar(int mar) {
        this.MAR = mar;
    }

    public void incrementIoReads() {
        ioReadCounter++;
    }
    public void incrementIoWrites() {
        ioWriteCounter++;
    }

    public void transitionTo(State nextState) {
        if (nextState == null) {
            throw new IllegalArgumentException("nextState cannot be null");
        }
        if (state == State.TERMINATED) {
            throw new IllegalStateException("Terminated processes cannot transition to other states");
        }
        this.state = nextState;
    }

    public void terminate() {
        this.state = State.TERMINATED;
    }
    

}
