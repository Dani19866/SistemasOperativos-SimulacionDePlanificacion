/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package simulacion_de_planificacion;

import IGU.PanelPrincipal;
import javax.swing.UnsupportedLookAndFeelException;
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
        PanelPrincipal pp = new PanelPrincipal();
        pp.setVisible(true);
        newAppareance();

        

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