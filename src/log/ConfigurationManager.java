/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package log;

import java.io.File;
import javax.swing.JFileChooser;
import structures.ArrayList;
import requirements.Process;

/**
 *
 * @author Daniel
 */
public class ConfigurationManager {

    // Cargar el archivo de origen de la configuración
    private String filePath;

    // Lista de procesos por cargar
    private ArrayList<Process> listProcess;

    /**
     * Retorna la ruta del archivo actual de config.
     *
     * @return config path
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Busca y setea el archivo de config mediante el explorador de archivos
     *
     * @return false if appers error | true if found file
     */
    public boolean setFilePath() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecciona un archivo");

        // FileNameExtensionFilter filterTxt = new FileNameExtensionFilter("Archivos de Texto (*.txt)", "txt");
        // fileChooser.addChoosableFileFilter(filterTxt);
        // Mostrar el diálogo para seleccionar el archivo
        int resultado = fileChooser.showOpenDialog(null);

        // Procesar la selección del usuario
        if (resultado == JFileChooser.APPROVE_OPTION) {

            try {
                File archivoSeleccionado = fileChooser.getSelectedFile();
                if (archivoSeleccionado.exists() && archivoSeleccionado.isFile()) {
                    this.filePath = archivoSeleccionado.getAbsolutePath();
                    return true;

                } else {
                    System.err.println("El archivo seleccionado no es válido o no existe.");
                    return false;
                }
            } catch (Exception e) {
                System.err.println("Error al obtener la ruta del archivo: " + e.getMessage());
                return false;
            }

        } else if (resultado == JFileChooser.CANCEL_OPTION) {
            System.out.println("El usuario canceló la selección.");
            return false;

        } else if (resultado == JFileChooser.ERROR_OPTION) {
            System.err.println("Ocurrió un error en el selector de archivos.");
            return false;
        }
        return false;
    }

    public ArrayList<Process> getListProcess() {
        return listProcess;
    }

    public void setListProcess(ArrayList<Process> listProcess) {
        this.listProcess = listProcess;
    }

}
