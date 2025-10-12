/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package structures;

/**
 *
 * representa los nodos de la lista enlazada y la Queue.
 * Cada nodo contiene el dato y una referencia al siguiente nodo.
 * 
 * @author Daniel
 * @param <T>
 */
public class Node<T> {

    public final T data; // El dato almacenado en el nodo.
    public Node<T> next; // Referencia al siguiente nodo.

    public Node(T data) {
        this.data = data;
        this.next = null; // Por defecto, el siguiente nodo es nulo.
    }
}
