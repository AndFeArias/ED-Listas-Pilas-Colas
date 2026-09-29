import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.time.Instant;
import java.util.Random;
import lists.DoublyLinkedList;
import lists.DoublyLinkedListWithTail;
import lists.SinglyLinkedList;
import lists.SinglyLinkedListWithTail;
import stackqueue.ArrayStack;
import stackqueue.DynamicArrayQueue;

@FunctionalInterface
interface Operation {
    void apply(int i);
}

public class main {

    // Medimos el tiempo en microsegundos (µs)
    public static double exec(int size, Operation operation) {
        Instant start = Instant.now();        
        
        for (int i = 0; i < size; i++) {
            operation.apply(i);
        }

        Instant finish = Instant.now();
        long timeElapsedNanos = Duration.between(start, finish).toNanos();
        
        // Retorna en microsegundos
        return timeElapsedNanos / 1000.0;
    }

    public static void main(String[] args) {
        final int[] sizes = {10, 100, 1000, 10000, 100000};
        final int iteraciones = 5;
        Random random = new Random();

        // Nombre del archivo de salida
        String nombreArchivo = "resultados_benchmark.csv";

        try (PrintWriter writer = new PrintWriter(new FileWriter(nombreArchivo))) {
            // Encabezado de la tabla para Excel
            writer.println("Estructura,Metodo,Tamano,Iteracion,Tiempo_Microsegundos");

            System.out.println("Ejecutando pruebas y generando tabla en " + nombreArchivo + "...");

            for (int size : sizes) {
                System.out.printf("Procesando N = %d...\n", size);

                for (int iter = 1; iter <= iteraciones; iter++) {

                    // 1. SINGLY LINKED LIST (Sin Tail)
                    SinglyLinkedList<Integer> sList = new SinglyLinkedList<>();
                    registrar(writer, "SinglyLinkedList", "pushFront", size, iter, exec(size, i -> sList.pushFront(i)));
                    registrar(writer, "SinglyLinkedList", "topFront", size, iter, exec(size, i -> sList.topFront()));
                    registrar(writer, "SinglyLinkedList", "topBack", size, iter, exec(size, i -> sList.topBack()));
                    registrar(writer, "SinglyLinkedList", "getSize", size, iter, exec(size, i -> sList.size()));
                    registrar(writer, "SinglyLinkedList", "isEmpty", size, iter, exec(size, i -> sList.isEmpty()));

                    SinglyLinkedList<Integer> sListBack = new SinglyLinkedList<>();
                    registrar(writer, "SinglyLinkedList", "pushBack", size, iter, exec(size, i -> sListBack.pushBack(i)));

                    Integer targetS = size / 2;
                    registrar(writer, "SinglyLinkedList", "addAfter", size, iter, exec(size, i -> sListBack.addAfter(targetS, i)));
                    registrar(writer, "SinglyLinkedList", "addBefore", size, iter, exec(size, i -> sListBack.addBefore(targetS, i)));

                    registrar(writer, "SinglyLinkedList", "find", size, iter, exec(size, i -> sListBack.find(random.nextInt(size))));
                    registrar(writer, "SinglyLinkedList", "erase", size, iter, exec(size, i -> sListBack.erase(random.nextInt(size))));
                    registrar(writer, "SinglyLinkedList", "popFront", size, iter, exec(size, i -> { if (!sList.isEmpty()) sList.popFront(); }));
                    registrar(writer, "SinglyLinkedList", "popBack", size, iter, exec(size, i -> { if (!sListBack.isEmpty()) sListBack.popBack(); }));


                    // 2. SINGLY LINKED LIST WITH TAIL (Con Cola)
                    SinglyLinkedListWithTail<Integer> sListTail = new SinglyLinkedListWithTail<>();
                    registrar(writer, "SinglyLinkedListWithTail", "pushFront", size, iter, exec(size, i -> sListTail.pushFront(i)));
                    registrar(writer, "SinglyLinkedListWithTail", "topFront", size, iter, exec(size, i -> sListTail.topFront()));
                    registrar(writer, "SinglyLinkedListWithTail", "topBack", size, iter, exec(size, i -> sListTail.topBack()));
                    registrar(writer, "SinglyLinkedListWithTail", "getSize", size, iter, exec(size, i -> sListTail.size()));
                    registrar(writer, "SinglyLinkedListWithTail", "isEmpty", size, iter, exec(size, i -> sListTail.isEmpty()));

                    SinglyLinkedListWithTail<Integer> sListTailBack = new SinglyLinkedListWithTail<>();
                    registrar(writer, "SinglyLinkedListWithTail", "pushBack", size, iter, exec(size, i -> sListTailBack.pushBack(i)));

                    Integer targetST = size / 2;
                    registrar(writer, "SinglyLinkedListWithTail", "addAfter", size, iter, exec(size, i -> sListTailBack.addAfter(targetST, i)));
                    registrar(writer, "SinglyLinkedListWithTail", "addBefore", size, iter, exec(size, i -> sListTailBack.addBefore(targetST, i)));

                    registrar(writer, "SinglyLinkedListWithTail", "find", size, iter, exec(size, i -> sListTailBack.find(random.nextInt(size))));
                    registrar(writer, "SinglyLinkedListWithTail", "erase", size, iter, exec(size, i -> sListTailBack.erase(random.nextInt(size))));
                    registrar(writer, "SinglyLinkedListWithTail", "popFront", size, iter, exec(size, i -> { if (!sListTail.isEmpty()) sListTail.popFront(); }));
                    registrar(writer, "SinglyLinkedListWithTail", "popBack", size, iter, exec(size, i -> { if (!sListTailBack.isEmpty()) sListTailBack.popBack(); }));


                    // 3. DOUBLY LINKED LIST (Sin Tail)
                    DoublyLinkedList<Integer> dList = new DoublyLinkedList<>();
                    registrar(writer, "DoublyLinkedList", "pushFront", size, iter, exec(size, i -> dList.pushFront(i)));
                    registrar(writer, "DoublyLinkedList", "topFront", size, iter, exec(size, i -> dList.topFront()));
                    registrar(writer, "DoublyLinkedList", "topBack", size, iter, exec(size, i -> dList.topBack()));
                    registrar(writer, "DoublyLinkedList", "getSize", size, iter, exec(size, i -> dList.size()));
                    registrar(writer, "DoublyLinkedList", "isEmpty", size, iter, exec(size, i -> dList.isEmpty()));

                    DoublyLinkedList<Integer> dListBack = new DoublyLinkedList<>();
                    registrar(writer, "DoublyLinkedList", "pushBack", size, iter, exec(size, i -> dListBack.pushBack(i)));

                    Integer targetD = size / 2;
                    registrar(writer, "DoublyLinkedList", "addAfter", size, iter, exec(size, i -> dListBack.addAfter(targetD, i)));
                    registrar(writer, "DoublyLinkedList", "addBefore", size, iter, exec(size, i -> dListBack.addBefore(targetD, i)));

                    registrar(writer, "DoublyLinkedList", "find", size, iter, exec(size, i -> dListBack.find(random.nextInt(size))));
                    registrar(writer, "DoublyLinkedList", "erase", size, iter, exec(size, i -> dListBack.erase(random.nextInt(size))));
                    registrar(writer, "DoublyLinkedList", "popFront", size, iter, exec(size, i -> { if (!dList.isEmpty()) dList.popFront(); }));
                    registrar(writer, "DoublyLinkedList", "popBack", size, iter, exec(size, i -> { if (!dListBack.isEmpty()) dListBack.popBack(); }));


                    // 4. DOUBLY LINKED LIST WITH TAIL (Con Cola)
                    DoublyLinkedListWithTail<Integer> dListTail = new DoublyLinkedListWithTail<>();
                    registrar(writer, "DoublyLinkedListWithTail", "pushFront", size, iter, exec(size, i -> dListTail.pushFront(i)));
                    registrar(writer, "DoublyLinkedListWithTail", "topFront", size, iter, exec(size, i -> dListTail.topFront()));
                    registrar(writer, "DoublyLinkedListWithTail", "topBack", size, iter, exec(size, i -> dListTail.topBack()));
                    registrar(writer, "DoublyLinkedListWithTail", "getSize", size, iter, exec(size, i -> dListTail.size()));
                    registrar(writer, "DoublyLinkedListWithTail", "isEmpty", size, iter, exec(size, i -> dListTail.isEmpty()));

                    DoublyLinkedListWithTail<Integer> dListTailBack = new DoublyLinkedListWithTail<>();
                    registrar(writer, "DoublyLinkedListWithTail", "pushBack", size, iter, exec(size, i -> dListTailBack.pushBack(i)));

                    Integer targetDT = size / 2;
                    registrar(writer, "DoublyLinkedListWithTail", "addAfter", size, iter, exec(size, i -> dListTailBack.addAfter(targetDT, i)));
                    registrar(writer, "DoublyLinkedListWithTail", "addBefore", size, iter, exec(size, i -> dListTailBack.addBefore(targetDT, i)));

                    registrar(writer, "DoublyLinkedListWithTail", "find", size, iter, exec(size, i -> dListTailBack.find(random.nextInt(size))));
                    registrar(writer, "DoublyLinkedListWithTail", "erase", size, iter, exec(size, i -> dListTailBack.erase(random.nextInt(size))));
                    registrar(writer, "DoublyLinkedListWithTail", "popFront", size, iter, exec(size, i -> { if (!dListTail.isEmpty()) dListTail.popFront(); }));
                    registrar(writer, "DoublyLinkedListWithTail", "popBack", size, iter, exec(size, i -> { if (!dListTailBack.isEmpty()) dListTailBack.popBack(); }));


                    // 5. STACK (ArrayStack)
                    ArrayStack<Integer> stack = new ArrayStack<>();
                    registrar(writer, "ArrayStack", "push", size, iter, exec(size, i -> stack.push(i)));
                    registrar(writer, "ArrayStack", "peek", size, iter, exec(size, i -> { if (!stack.isEmpty()) stack.peek(); }));
                    registrar(writer, "ArrayStack", "size", size, iter, exec(size, i -> stack.size()));
                    registrar(writer, "ArrayStack", "isEmpty", size, iter, exec(size, i -> stack.isEmpty()));
                    registrar(writer, "ArrayStack", "delete", size, iter, exec(size, i -> stack.delete(random.nextInt(size))));
                    registrar(writer, "ArrayStack", "pop", size, iter, exec(size, i -> { if (!stack.isEmpty()) stack.pop(); }));


                    // 6. QUEUE (DynamicArrayQueue)
                    DynamicArrayQueue<Integer> queue = new DynamicArrayQueue<>();
                    registrar(writer, "DynamicArrayQueue", "enqueue", size, iter, exec(size, i -> queue.enqueue(i)));
                    registrar(writer, "DynamicArrayQueue", "front", size, iter, exec(size, i -> { if (!queue.isEmpty()) queue.front(); }));
                    registrar(writer, "DynamicArrayQueue", "size", size, iter, exec(size, i -> queue.size()));
                    registrar(writer, "DynamicArrayQueue", "isEmpty", size, iter, exec(size, i -> queue.isEmpty()));
                    registrar(writer, "DynamicArrayQueue", "delete", size, iter, exec(size, i -> queue.delete(random.nextInt(size))));
                    registrar(writer, "DynamicArrayQueue", "dequeue", size, iter, exec(size, i -> { if (!queue.isEmpty()) queue.dequeue(); }));
                }
            }

            System.out.println("¡Proceso finalizado con éxito!");
            System.out.println("Abre el archivo 'resultados_benchmark.csv' con Excel para ver la tabla completa.");

        } catch (IOException e) {
            System.err.println("Error al escribir el archivo CSV: " + e.getMessage());
        }
    }

    // Método auxiliar para escribir directamente una línea en el archivo CSV
    private static void registrar(PrintWriter writer, String estructura, String metodo, int tamano, int iteracion, double tiempoMicros) {
        writer.printf("%s,%s,%d,%d,%.3f\n", estructura, metodo, tamano, iteracion, tiempoMicros);
    }
}