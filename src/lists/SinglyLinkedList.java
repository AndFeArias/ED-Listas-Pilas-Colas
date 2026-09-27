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
     
public class SinglyLinkedList<T> implements MyList<T>{
    private Node<T> head;
    private int size;     

    public SinglyLinkedList(){
        this.head = null;
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

        Node<T> current = head;
        while(current.next != null){
            current = current.next;
        }
        return current.data;
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
        newNode.next = head;
        head = newNode;
        size++;
    }

    @Override 
    public void pushBack(T data){
        Node<T> newNode = new Node<>(data);
        if(isEmpty()){
            head = newNode;
            size++;
            return;
        }
        Node<T> current = head;
        while(current.next != null){
            current = current.next;
        }
        current.next = newNode;
        size++;
    }

    @Override 
    public T popFront(){
        if(isEmpty()){
        throw new NoSuchElementException("La lista está vacía");
        }

        T valorElimiando = head.data;
        head = head.next;
        size--;
        return valorElimiando;
    }

    @Override 
    public T popBack(){
        if(isEmpty()){
            throw new NoSuchElementException("La lista está vacía"); 
        }
        
        T elementoEliminado;

        if(head.next == null){
            elementoEliminado = head.data;
            head = null;
            size--;
            return elementoEliminado;
        }
        Node<T> current = head;
        while(current.next.next != null){
            current = current.next;
        }
        elementoEliminado = current.next.data;
        current.next = null;
        size--;
        return elementoEliminado;
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
    public boolean erase(T data){
        if(isEmpty()) return false;

        if(head.data != null && head.data.equals(data)){
            popFront();
            return true;
        }
        Node<T> prev = head;
        while(prev.next != null && !prev.next.data.equals(data)){
            prev = prev.next;
        }
        if(prev.next != null){
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


        Node<T> current = head;
        while(current.next != null && !current.next.data.equals(target)){
            current = current.next;
        }


        if(current.next != null){
            Node<T> newNode = new Node<>(data);
            newNode.next = current.next;
            current.next = newNode;
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
            size++; 
            return true;
        }

        return false;
    }
    

    public static void main(String[] args) {
    
}

} 