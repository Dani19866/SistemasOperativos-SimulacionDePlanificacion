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

    private final PCB pcb;

    // Si el proceso es I/O, debe especificarse cuantos ciclos se necesitan
    // para generar una excepción y cuantos para satisfacerla. También se debe
    // permitir la configuración de la duración de ciclo
    private int cycles;
    private int cycleDurantion;

    public Process(PCB pcb) {
        this.pcb = pcb;
        
    }

}
