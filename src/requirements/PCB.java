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

    private String id;
    private final String name;
    private final StateProcess stateProcess;
    private final ProcessType processType;
    private int PC;
    private int MAR;

    public PCB(String name, int statusProgramCounter, ProcessType processType) {
        setId();
        this.name = name;
        this.processType = processType;
        this.stateProcess = StateProcess.NEW;
    }

    /**
     * Se obtiene el ID del PCB
     *
     * @return ID
     */
    public String getId() {
        return id;
    }

    /**
     * Generación de ID mediante UUID
     * Se ejecuta desde el constructor
     *
     */
    private void setId() {
        this.id = UUID.randomUUID().toString();
    }

}
