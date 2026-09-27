package lists;
import interfaces.MyList;
import java.util.NoSuchElementException;

class Node<T>{
    T data;
    Node<T> next;

    public Node(T data){
        this.data = data;
        this.next = null;
    }
}
     
public class SinglyLinkedListWithTail<T> implements MyList<T>{
    private Node<T> head;
    private Node<T> tail;
    private int size;     

    public SinglyLinkedListWithTail(){
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    @Override 
    public boolean isEmpty(){
        return head == null;
    }

    @Override 
    public int size(){
        return size;
    }

    @Override 
    public T topFront(){
        if(isEmpty()) throw new NoSuchElementException("No se puede acceder al elemento: La lista esta vacia");

        return head.data;
    }

    @Override 
    public T topBack(){
        if(isEmpty()) throw new NoSuchElementException("No se puede acceder al elemento: La lista esta vacia");
        
        return tail.data;
    } 

    @Override //tener cuidado al momento del analisis
    public String toString() {
        if (isEmpty()) return "[]";
        StringBuilder sb = new StringBuilder("[");
        Node<T> current = head;
        while (current != null) {
            sb.append(current.data);
            if (current.next != null) sb.append(" -> ");
            current = current.next;
        }
        return sb.append("]").toString();
    }


    @Override 
    public void pushFront(T data){
        Node<T> newNode = new Node<>(data);
        if(isEmpty()){
            head = newNode;
            tail = newNode;
        }else{
            newNode.next = head;
            head = newNode;
        }
        size++;
    }   

    @Override 
    public void pushBack(T data){
        Node<T> newNode = new Node<>(data);
        if(isEmpty()){
            head = newNode;
            tail = newNode;
        }else{
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override 
    public T popFront(){
        if(isEmpty()) throw new NoSuchElementException("No se puede eliminar nada, la lista esta vacia");

        T eliminado = head.data;
        head = head.next;
        size--;
        if(head == null) tail = null;

        return eliminado;
    }

    @Override 
    public T popBack(){
        if(isEmpty()) throw new NoSuchElementException("No se puede eliminar nada, la lista esta vacia");
        T eliminado = tail.data;
    
        if(head == tail){
            head = null;
            tail = null;
        }else{
            Node<T> current = head;
            while(current.next != tail){
                current = current.next;
            }
            current.next = null;
            tail = current;
            
        }
    size--; 
    return eliminado;
    }

    @Override 
    public boolean find(T data){
        Node<T> current = head;
        while(current != null){
            if(current.data != null && current.data.equals(data)) return true;
            current = current.next;
        }

        return false;
    }

    @Override 
    public boolean erase(T data) {
        if(isEmpty()) throw new NoSuchElementException("No se puede eliminar nada, la lista esta vacia");

        if(head.data.equals(data)){
            popFront();
            return true;
        }


        Node<T> prev = head;
        while(prev.next != null && !prev.next.data.equals(data)){
            prev = prev.next;
        }

        if(prev.next != null){
            if(prev.next == tail){
                tail = prev;
            }

            prev.next = prev.next.next;
            size--;
            return true;
        }

        return false;
    }

    @Override
    public boolean addBefore(T target, T data) {
        if(isEmpty()) return false;

        if(head.data != null && head.data.equals(target)){
            pushFront(data);
            return true;
        }

        Node<T> prev = head;
        while(prev.next != null && !prev.next.data.equals(target)){
            prev = prev.next;
        }
        if(prev.next != null){
            Node<T> newNode = new Node<>(data);
            newNode.next = prev.next;
            prev.next = newNode;
            size++;
            return true;
        }
        
        return false;
    }

    @Override 
    public boolean addAfter(T target, T data){
        if(isEmpty()) return false;
        
        Node<T> current = head;
        while(current != null && !current.data.equals(target)){
            current = current.next;
        }

        if(current != null){
            Node<T> newNode = new Node<>(data);           
            newNode.next = current.next;
            current.next = newNode;

            if(current == tail){
                tail = newNode;
            } 
            size++;
            return true;
        }
        
        return false;
    }

    
    public class Main {
    public static void main(String[] args) {
        SinglyLinkedListWithTail<Integer> lista = new SinglyLinkedListWithTail<>();

        System.out.println("=== 1. PRUEBAS DE INSERCIÓN BÁSICA ===");
        System.out.println("¿Está vacía?: " + lista.isEmpty()); // true
        
        lista.pushFront(10);
        lista.pushBack(20);
        lista.pushFront(5);
        lista.pushBack(30);
        // Esperado: [5 -> 10 -> 20 -> 30]
        System.out.println("Lista actual: " + lista); 
        System.out.println("Tamaño: " + lista.size()); // 4
        System.out.println("Primero (topFront): " + lista.topFront()); // 5
        System.out.println("Último (topBack): " + lista.topBack()); // 30

        System.out.println("\n=== 2. PRUEBAS DE INSERCIÓN INTERMEDIA ===");
        // Insertar antes del 20 -> [5 -> 10 -> 15 -> 20 -> 30]
        lista.addBefore(20, 15);
        // Insertar después del 30 (afecta al tail) -> [5 -> 10 -> 15 -> 20 -> 30 -> 35]
        lista.addAfter(30, 35);
        System.out.println("Lista tras inserciones: " + lista);
        System.out.println("Nuevo último (debe ser 35): " + lista.topBack());

        System.out.println("\n=== 3. PRUEBAS DE BÚSQUEDA ===");
        System.out.println("¿Existe el 15?: " + lista.find(15)); // true
        System.out.println("¿Existe el 99?: " + lista.find(99)); // false

        System.out.println("\n=== 4. PRUEBAS DE ELIMINACIÓN DE EXTREMOS ===");
        System.out.println("Eliminado al frente: " + lista.popFront()); // 5
        System.out.println("Eliminado atrás: " + lista.popBack()); // 35
        System.out.println("Lista remanente: " + lista); // [10 -> 15 -> 20 -> 30]
        System.out.println("Primero: " + lista.topFront() + " | Último: " + lista.topBack());

        System.out.println("\n=== 5. PRUEBAS DE ELIMINACIÓN INTERMEDIA (erase) ===");
        // Eliminar elemento intermedio
        lista.erase(15); 
        System.out.println("Tras borrar 15: " + lista); // [10 -> 20 -> 30]
        
        // Eliminar el último elemento mediante erase (debe mover el tail al 20)
        lista.erase(30); 
        System.out.println("Tras borrar el último (30): " + lista); // [10 -> 20]
        System.out.println("Validando nuevo Último: " + lista.topBack()); // 20

        System.out.println("\n=== 6. VACIANDO LA LISTA ===");
        lista.popFront(); // Quita 10
        lista.popFront(); // Quita 20 (Lista queda vacía)
        System.out.println("Lista final: " + lista); // []
        System.out.println("¿Está vacía al final?: " + lista.isEmpty()); // true
        System.out.println("Tamaño final: " + lista.size()); // 0
    }
}
}