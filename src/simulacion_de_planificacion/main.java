/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package simulacion_de_planificacion;

import IGU.PanelPrincipal;
import javax.swing.UnsupportedLookAndFeelException;
import requirements.Disk;
import requirements.Memory;
import requirements.OS;
import requirements.PCB;
import structures.MemorySizeKb;
import requirements.Process;
import structures.ProcessType;

/**
 *
 * @author Daniel
 */
public class main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        newAppareance();

        Memory memory = new Memory(MemorySizeKb.KB_EQUALS_TO_8_GB);
        Disk disk = new Disk(MemorySizeKb.KB_EQUALS_TO_32_GB);
           
        PanelPrincipal x = new PanelPrincipal();
        x.setVisible(true);
    }

    // <editor-fold defaultstate="collapsed" desc="Actualizar apariencia">    
    public static void newAppareance() {
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
            System.err.println("No se pudo establecer la apariencia nativa de Windows.");
        }
    }
    // </editor-fold> 
}