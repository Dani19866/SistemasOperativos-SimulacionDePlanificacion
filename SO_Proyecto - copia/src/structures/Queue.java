/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package structures;

import java.util.NoSuchElementException;

/**
 * Implementa una estructura de datos de tipo Cola (Queue) utilizando una lista enlazada.
 * Una cola sigue el principio FIFO (First-In, First-Out), donde el primer elemento
 * en entrar es el primero en salir.
 *
 * @author Daniel
 * @param <T> El tipo de dato que se almacenará en la cola.
 */
public class Queue<T> {

    private Node<T> front; // Apunta al primer elemento de la cola (cabeza).
    private Node<T> rear;  // Apunta al último elemento de la cola (cola).
    private int size;      // Almacena el número de elementos en la cola.

    /**
     * Constructor que inicializa una cola vacía.
     */
    public Queue() {
        this.front = null;
        this.rear = null;
        this.size = 0;
    }

    /**
     * Añade un elemento al final de la cola (encolar).
     * Su complejidad es O(1).
     * @param value El elemento que se va a añadir.
     */
    public void enqueue(T value) {
        Node<T> newNode = new Node<>(value);
        if (isEmpty()) {
            // Si la cola está vacía, el nuevo nodo es tanto el frente como el final.
            front = newNode;
        } else {
            // Si no, el nodo actual al final de la cola apuntará al nuevo nodo.
            rear.next = newNode;
        }
        // El nuevo nodo se convierte en el nuevo final de la cola.
        rear = newNode;
        size++;
    }

    /**
     * Elimina y devuelve el elemento del frente de la cola (desencolar).
     * Su complejidad es O(1).
     * @return El elemento que estaba al frente de la cola.
     * @throws NoSuchElementException si se intenta desencolar de una cola vacía.
     */
    public T dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("No se puede desencolar de una cola vacía.");
        }
        T data = front.data; // Se guarda el dato del nodo frontal.
        front = front.next;  // El frente ahora es el siguiente nodo.

        if (front == null) {
            // Si después de eliminar, la cola queda vacía, 'rear' también debe ser nulo.
            rear = null;
        }
        size--;
        return data;
    }

    /**
     * Devuelve el elemento del frente de la cola sin eliminarlo.
     * Su complejidad es O(1).
     * @return El elemento en el frente de la cola.
     * @throws NoSuchElementException si la cola está vacía.
     */
    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("La cola está vacía.");
        }
        return front.data;
    }

    /**
     * Comprueba si la cola está vacía.
     * @return `true` si la cola no contiene elementos, `false` en caso contrario.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Devuelve el número de elementos que hay en la cola.
     * @return El tamaño actual de la cola.
     */
    public int size() {
        return size;
    }
    /** 
     * Devuelve el tamanio 
     * @return 
     */
    
    /**
     * Elimina un nodo específico de la cola, sin importar su posición.
     * Su complejidad es O(n) porque debe buscar el nodo.
     * @param value El elemento que se va a eliminar.
     * @return true si el elemento fue encontrado y eliminado, false si no.
     */
    public boolean remove(T value) {
        if (isEmpty()) {
            return false;
        }
            // Caso 1: El nodo a eliminar es el 'front'
            if (front.data.equals(value)) {
                // dequeue() ya maneja el caso de que sea el único nodo
                // o de que 'front' deba avanzar.
                dequeue(); 
                return true;
            }
            // Caso 2: El nodo a eliminar está en medio o al final
            Node<T> current = front;
            Node<T> previous = null;

            // Iteramos hasta encontrar el valor o llegar al final
            while (current != null && !current.data.equals(value)) {
                previous = current;
                current = current.next;
            }
            // Si current es null, significa que no encontramos el valor
            if (current == null) {
                return false;
            }
            // Saltamos el nodo 'current'
            previous.next = current.next;
            
            // Caso 3: Era el nodo a eliminar el rear
            if (current == rear) {
                // Si es así, el nuevo 'rear' es el nodo 'previous'
                rear = previous;
            }

            size--;
            return true;
    }

    
    
    

    /**
     * Devuelve una representación en formato de cadena de la cola.
     * Muestra los elementos desde el frente hacia el final.
     * @return Una cadena con el contenido de la cola.
     */
    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Front -> [");
        Node<T> current = front;
        while (current != null) {
            sb.append(current.data);
            if (current.next != null) {
                sb.append(", ");
            }
            current = current.next;
        }
        sb.append("] <- Rear");
        return sb.toString();
    }
}