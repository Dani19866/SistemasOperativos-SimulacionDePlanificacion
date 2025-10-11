/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package simulacion_de_planificacion;

import javax.swing.UnsupportedLookAndFeelException;
import requirements.OS;
import requirements.PCB;

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
        OS pc = new OS();
        
    }

    public static void newAppareance() {
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
            System.err.println("No se pudo establecer la apariencia nativa de Windows.");
        }
    }
    
}
