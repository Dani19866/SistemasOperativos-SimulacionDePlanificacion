/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package requirements;

import structures.ProcessType;
import structures.StateProcess;

/**
 *
 * @author Daniel
 */
public class Process {

<<<<<<< HEAD
    private final PCB pcb;
=======
    private PCB pcb;
    private String name;
    private int quantityInstruction;
    private ProcessType processType;
    private StateProcess stateProcess;
>>>>>>> 72d9b4ef3f8d84445d4f18e02fcdeabdea4dad77

    // Si el proceso es I/O, debe especificarse cuantos ciclos se necesitan
    // para generar una excepción y cuantos para satisfacerla. También se debe
    // permitir la configuración de la duración de ciclo
    private int cycles;
    private int cycleDurantion;

    public Process(PCB pcb) {
        this.pcb = pcb;
<<<<<<< HEAD
        
=======
>>>>>>> 72d9b4ef3f8d84445d4f18e02fcdeabdea4dad77
    }

}
