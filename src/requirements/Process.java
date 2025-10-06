/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package requirements;

/**
 *
 * @author Daniel
 */
public class Process {

    // Características de un proceso
    private String name;
    private int quantityInstruction;
    private boolean BoundCPU;
    private boolean BoundIO;

    // Si el proceso es I/O, debe especificarse cuantos ciclos se necesitan
    // para generar una excepción y cuantos para satisfacerla. También se debe
    // permitir la configuración de la duración de ciclo
    private int cycles;
    private int cycleDurantion;

    // Estado del proceso
    private String status;

    /**
     * Cambia el estado de un proceso
     *
     * @param value 1) Nuevo, 2) Listo, 3) Ejecución, 4) bloqueado,
     * 5) terminado y 6) suspendido
     */
    public void changeStatus(String value) {
        switch (value) {
            case "nuevo" -> {
            }
            case "listo" -> {
            }
            case "ejecucion" -> {
            }
            case "bloqueado" -> {
            }
            case "terminado" -> {
            }
            case "suspendido" -> {
            }
            default -> {
            }
        }
    }
;
}
