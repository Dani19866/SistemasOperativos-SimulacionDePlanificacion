/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package requirements;

import java.util.UUID;

/**
 *
 * @author Daniel
 */
public class PCB {
    private String id;
    private String status;
    private final String name;
    private int statusProgramCounter;
    private String statusMAR;
    
    public PCB(String name, int statusProgramCounter){
        setId();
        this.name = name;
        this.statusProgramCounter = statusProgramCounter;
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

    /**
     * Devolver el status del proceso
     * 
     * @return status
     */
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Devolver el nombre del proceso 
     * 
     * @return Nombre del proceso
     */
    public String getName() {
        return name;
    }

    public int getStatusProgramCounter() {
        return statusProgramCounter;
    }

    public void setStatusProgramCounter(int statusProgramCounter) {
        this.statusProgramCounter = statusProgramCounter;
    }

    public String getStatusMAR() {
        return statusMAR;
    }

    public void setStatusMAR(String statusMAR) {
        this.statusMAR = statusMAR;
    }
    
    
    
}
