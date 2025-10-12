/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package structures;

import java.util.Arrays;
import java.util.Objects;

/**
 *
 * @author Daniel
 */
public class ArrayList<T> {

    private Object[] elements; // Arreglo para almacenar los datos.
    private int size = 0;      // Número actual de elementos en la lista.

    /**
     * Constructor que inicializa la lista con una capacidad de 0.
     * El arreglo crecerá en la primera inserción.
     */
    public ArrayList() {
        this.elements = new Object[0];
    }

    /**
     * Devuelve el número de elementos en la lista.
     *
     * @return El tamaño de la lista.
     */
    public int size() {
        return this.size;
    }

    /**
     * Verifica si la lista está vacía.
     *
     * @return true si la lista no contiene elementos.
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * Ajusta la capacidad del arreglo interno.
     * - Si está lleno, duplica su capacidad.
     * - Si está menos de un cuarto lleno, reduce su capacidad a la mitad.
     */
    private void resize() {
        // Crecer: si el tamaño alcanza la capacidad máxima
        if (size == elements.length) {
            int newCapacity = (elements.length == 0) ? 10 : elements.length * 2; // Inicia en 10 si es la primera vez
            elements = Arrays.copyOf(elements, newCapacity);
        } // Reducir: si el tamaño es menor que 1/4 de la capacidad y la capacidad es mayor que 10
        else if (size > 0 && size <= elements.length / 4 && elements.length > 10) {
            int newCapacity = elements.length / 2;
            elements = Arrays.copyOf(elements, newCapacity);
        }
    }

    /**
     * Agrega un elemento al final de la lista.
     *
     * @param element El elemento a agregar.
     */
    public void add(T element) {
        resize();
        elements[size] = element;
        size++;
    }

    /**
     * Inserta un elemento en una posición específica.
     *
     * @param index El índice donde se insertará el elemento.
     * @param element El elemento a insertar.
     * @throws IndexOutOfBoundsException si el índice está fuera del rango.
     */
    public void add(int index, T element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + index);
        }
        resize();
        System.arraycopy(elements, index, elements, index + 1, size - index);
        elements[index] = element;
        size++;
    }

    /**
     * Obtiene el elemento en una posición específica.
     *
     * @param index El índice del elemento a obtener.
     * @return El elemento en la posición especificada.
     * @throws IndexOutOfBoundsException si el índice está fuera del rango.
     */
    @SuppressWarnings("unchecked")
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + index);
        }
        return (T) elements[index];
    }

    /**
     * Elimina el elemento en una posición específica y ajusta la capacidad si
     * es necesario.
     *
     * @param index El índice del elemento a eliminar.
     * @return El elemento que fue eliminado.
     * @throws IndexOutOfBoundsException si el índice está fuera del rango.
     */
    @SuppressWarnings("unchecked")
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + index);
        }
        T removedElement = (T) elements[index];
        int numToMove = size - index - 1;
        if (numToMove > 0) {
            System.arraycopy(elements, index + 1, elements, index, numToMove);
        }
        size--;
        elements[size] = null; // Liberar referencia

        resize(); // Comprobar si es necesario reducir el tamaño del arreglo
        return removedElement;
    }

    /**
     * Busca un elemento en la lista y devuelve su primera ocurrencia.
     * Utiliza el método .equals() para la comparación.
     *
     * @param element El elemento a buscar.
     * @return El índice de la primera ocurrencia del elemento, o -1 si no se
     * encuentra.
     */
    public int indexOf(T element) {
        for (int i = 0; i < size; i++) {
            // Usamos Objects.equals para manejar de forma segura el caso de que 'element' sea null.
            if (Objects.equals(element, elements[i])) {
                return i;
            }
        }
        return -1; // No se encontró el elemento
    }

    /**
     * Devuelve la capacidad actual del arreglo interno (para depuración).
     *
     * @return La longitud del arreglo interno.
     */
    public int getCapacity() {
        return elements.length;
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < size; i++) {
            sb.append(elements[i]);
            if (i < size - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
