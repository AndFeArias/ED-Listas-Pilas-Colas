import java.time.Duration;
import java.time.Instant;
import java.util.Random;
import lists.DoublyLinkedList;
import lists.DoublyLinkedListWithTail;
import lists.SinglyLinkedList;
import lists.SinglyLinkedListWithTail;
import stackqueue.ArrayStack;
import stackqueue.DynamicArrayQueue;

interface Operation {
    void apply(int i);
}

public class main {

    public static double exec(int size, String method, Operation operation) {
        Instant start = Instant.now();        
        
        for (int i = 0; i < size; i++) {
            operation.apply(i);
        }

        Instant finish = Instant.now();
        double timeElapsedMicros = Duration.between(start, finish).toNanos() / 1000.0;
        
        System.out.printf("%-35s | N: %-7d | Tiempo: %10.3f µs\n", method, size, timeElapsedMicros);
        return timeElapsedMicros;
    }

    public static void main(String[] args) {
        // Tamaños de entrada especificados en el laboratorio (10^1 hasta 10^5)
        final int[] sizes = {10, 100, 1000, 10000, 100000};
        Random random = new Random();

        System.out.println("===============================================================================");
        System.out.println("            MEDICIÓN DE TIEMPOS DE EJECUCIÓN (BENCHMARK)                      ");
        System.out.println("===============================================================================\n");

        for (int size : sizes) {
            System.out.printf("\n>>> EVALUANDO TAMAÑO DE ENTRADA N = %d <<<\n\n", size);

            // Se ejecutan 5 repeticiones para estabilizar y promediar si se desea
            for (int iter = 1; iter <= 5; iter++) {
                System.out.println("--- Iteración " + iter + " ---");

                // -------------------------------------------------------------
                // 1. SINGLY LINKED LIST (Sin Tail)
                // -------------------------------------------------------------
                SinglyLinkedList sList = new SinglyLinkedList<>();
                exec(size, "SinglyList - pushFront", sList::pushFront);

                SinglyLinkedList sListBack = new SinglyLinkedList<>();
                exec(size, "SinglyList - pushBack", sListBack::pushBack);

                exec(size, "SinglyList - popFront", i -> {
                    if (!sList.isEmpty()) sList.popFront();
                });

                exec(size, "SinglyList - popBack", i -> {
                    if (!sListBack.isEmpty()) sListBack.popBack();
                });

                // Re-poblar para probar find y erase
                SinglyLinkedList sListearch = new SinglyLinkedList<>();
                for (int i = 0; i < size; i++) sListearch.pushFront(i);
                
                exec(size, "SinglyList - find", i -> sListearch.find(random.nextInt(size)));
                exec(size, "SinglyList - erase", i -> sListearch.erase(random.nextInt(size)));


                // -------------------------------------------------------------
                // 2. SINGLY LINKED LIST WITH TAIL (Con Cola)
                // -------------------------------------------------------------
                SinglyLinkedListWithTail sListTail = new SinglyLinkedListWithTail<>();
                exec(size, "SinglyListTail - pushFront", sListTail::pushFront);

                SinglyLinkedListWithTail sListTailBack = new SinglyLinkedListWithTail<>();
                exec(size, "SinglyListTail - pushBack", sListTailBack::pushBack);

                exec(size, "SinglyListTail - popFront", i -> {
                    if (!sListTail.isEmpty()) sListTail.popFront();
                });

                exec(size, "SinglyListTail - popBack", i -> {
                    if (!sListTailBack.isEmpty()) sListTailBack.popBack();
                });


                // -------------------------------------------------------------
                // 3. DOUBLY LINKED LIST (Sin Tail)
                // -------------------------------------------------------------
                DoublyLinkedList dList = new DoublyLinkedList<>();
                exec(size, "DoublyList - pushFront", dList::pushFront);

                DoublyLinkedList dListBack = new DoublyLinkedList<>();
                exec(size, "DoublyList - pushBack", dListBack::pushBack);

                exec(size, "DoublyList - popFront", i -> {
                    if (!dList.isEmpty()) dList.popFront();
                });

                exec(size, "DoublyList - popBack", i -> {
                    if (!dListBack.isEmpty()) dListBack.popBack();
                });


                // -------------------------------------------------------------
                // 4. DOUBLY LINKED LIST WITH TAIL (Con Cola)
                // -------------------------------------------------------------
                DoublyLinkedListWithTail dListTail = new DoublyLinkedListWithTail<>();
                exec(size, "DoublyListTail - pushFront", dListTail::pushFront);

                DoublyLinkedListWithTail dListTailBack = new DoublyLinkedListWithTail<>();
                exec(size, "DoublyListTail - pushBack", dListTailBack::pushBack);

                exec(size, "DoublyListTail - popFront", i -> {
                    if (!dListTail.isEmpty()) dListTail.popFront();
                });

                exec(size, "DoublyListTail - popBack", i -> {
                    if (!dListTailBack.isEmpty()) dListTailBack.popBack();
                });


                // -------------------------------------------------------------
                // 5. DYNAMIC ARRAY STACK (Pila)
                // -------------------------------------------------------------
                ArrayStack stack = new ArrayStack<>();
                exec(size, "ArrayStack - push", stack::push);
                exec(size, "ArrayStack - pop", i -> {
                    if (!stack.isEmpty()) stack.pop();
                });


                // -------------------------------------------------------------
                // 6. DYNAMIC ARRAY QUEUE (Cola Circular)
                // -------------------------------------------------------------
                DynamicArrayQueue queue = new DynamicArrayQueue<>();
                exec(size, "ArrayQueue - enqueue", queue::enqueue);
                exec(size, "ArrayQueue - dequeue", i -> {
                    if (!queue.isEmpty()) queue.dequeue();
                });

                System.out.println("-------------------------------------------------------------------------------");
            }
        }
    }
}