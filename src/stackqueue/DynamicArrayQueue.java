package stackqueue;
import interfaces.MyQueue;
import java.util.NoSuchElementException;

public class DynamicArrayQueue<T> implements MyQueue<T> {

    private T[] array;
    private int front;
    private int rear;
    private int count;
    private static final int INITIAL_CAPACITY = 10;

    @SuppressWarnings("unchecked")
    public DynamicArrayQueue() {
        this.array = (T[]) new Object[INITIAL_CAPACITY];
        this.front = 0;
        this.rear = 0;
        this.count = 0;
    }

    @Override
    public void enqueue(T x) {
        if (count == array.length) {
            resize(array.length * 2);
        }
        array[rear] = x;
        rear = (rear + 1) % array.length; // Avanza circularmente
        count++;
    }

    @Override
    public T dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("La cola está vacía");
        }
        T item = array[front];
        array[front] = null; // Evita fugas de memoria (loitering)
        front = (front + 1) % array.length; // Avanza circularmente
        count--;

        // Reducción opcional si el tamaño cae al 25% de la capacidad
        if (count > 0 && count == array.length / 4 && array.length > INITIAL_CAPACITY) {
            resize(array.length / 2);
        }

        return item;
    }

    @Override
    public T front() {
        if (isEmpty()) {
            throw new NoSuchElementException("La cola está vacía");
        }
        return array[front];
    }

    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public void delete(T n) {
        if (isEmpty()) return;

        // 1. Buscar si el elemento existe
        int targetIndex = -1;
        for (int i = 0; i < count; i++) {
            int actualIndex = (front + i) % array.length;
            if ((n == null && array[actualIndex] == null) || (n != null && n.equals(array[actualIndex]))) {
                targetIndex = i; // Guardamos la posición relativa (0 a count-1)
                break;
            }
        }

        // 2. Si se encontró, recreamos la estructura saltándonos ese elemento
        if (targetIndex != -1) {
            // Creamos un nuevo arreglo temporal del mismo tamaño actual
            @SuppressWarnings("unchecked")
            T[] newArray = (T[]) new Object[array.length];
            int newIdx = 0;

            for (int i = 0; i < count; i++) {
                if (i == targetIndex) continue; // Saltamos el elemento a eliminar
                newArray[newIdx++] = array[(front + i) % array.length];
            }

            // Reajustamos los punteros al nuevo estado linealizado
            this.array = newArray;
            this.front = 0;
            this.rear = newIdx;
            this.count--;

            // Opcional: Reducir tamaño si cae por debajo del 25% tras borrar
            if (count > 0 && count == array.length / 4 && array.length > INITIAL_CAPACITY) {
                resize(array.length / 2);
            }
        }
    }

    // Método auxiliar para redimensionar el arreglo
    @SuppressWarnings("unchecked")
    private void resize(int newCapacity) {
        T[] newArray = (T[]) new Object[newCapacity];
        for (int i = 0; i < count; i++) {
            newArray[i] = array[(front + i) % array.length];
        }
        this.array = newArray;
        this.front = 0;
        this.rear = count;
    }
}
