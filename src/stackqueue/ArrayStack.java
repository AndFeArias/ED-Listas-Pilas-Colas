package stackqueue;
import interfaces.MyStack;
import java.util.EmptyStackException;

public class ArrayStack<T> implements MyStack<T> {

    private T[] data;
    private int size;
    private static final int INITIAL_CAPACITY = 10;

    @SuppressWarnings("unchecked")
    public ArrayStack() {
        this.data = (T[]) new Object[INITIAL_CAPACITY];
        this.size = 0;
    }

    @Override
    public void push(T x) {
        if (size == data.length) {
            resize(data.length * 2); // Duplicamos la capacidad si el arreglo está lleno
        }
        data[size] = x;
        size++;
    }

    @Override
    public T pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        size--;
        T item = data[size];
        data[size] = null; // Liberamos la referencia para el Garbage Collector

        // Reducimos el tamaño a la mitad si la cantidad de elementos es 1/4 de la capacidad
        if (size > 0 && size == data.length / 4 && data.length > INITIAL_CAPACITY) {
            resize(data.length / 2);
        }

        return item;
    }

    @Override
    public T peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return data[size - 1];
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void delete(T n) {
        if (isEmpty()) {
            return;
        }

        // Buscamos la primera ocurrencia de 'n' desde el tope hacia la base
        int indexFound = -1;
        for (int i = size - 1; i >= 0; i--) {
            if ((n == null && data[i] == null) || (n != null && n.equals(data[i]))) {
                indexFound = i;
                break;
            }
        }

        // Si se encontró el elemento, desplazamos los elementos posteriores hacia la izquierda
        if (indexFound != -1) {
            for (int i = indexFound; i < size - 1; i++) {
                data[i] = data[i + 1];
            }
            data[size - 1] = null; // Eliminamos la última referencia duplicada
            size--;

            // Ajustamos el tamaño del arreglo si se redujo significativamente
            if (size > 0 && size == data.length / 4) {
                resize(data.length / 2);
            }
        }
    }

    // Método auxiliar privado para redimensionar el arreglo
    @SuppressWarnings("unchecked")
    private void resize(int newCapacity) {
        T[] newData = (T[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newData[i] = data[i];
        }
        data = newData;
    }
}