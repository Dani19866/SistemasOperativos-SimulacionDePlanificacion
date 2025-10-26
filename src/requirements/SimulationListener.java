/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package requirements;
import java.util.EventListener;
/**
 *
 * @author rtkn0_z8ls
 */
public interface SimulationListener extends EventListener {
    /**
     * El OS llamará a este método en la GUI (PanelPrincipal)
     * cada vez que una cola de procesos (Listos, Bloqueados, etc.)
     * haya sido modificada.
     */
    void onProcessQueuesChanged();
}
