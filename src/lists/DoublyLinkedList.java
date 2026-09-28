package lists;
import interfaces.MyList;
import java.util.NoSuchElementException;

class Node<T>{
    T data;
    Node<T> next;
    Node<T> prev;

    public Node(T data){
        this.data = data;
        this.next = null;
        this.prev = null;
    }
}

public class DoublyLinkedList<T> implements MyList<T>{
    private Node<T> head;
    private int size;

    public DoublyLinkedList(){
        head = null;
        size = 0;
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
        if (current.next != null) sb.append(" <-> ");
        current = current.next;
    }
    return sb.append("]").toString();
    }

    @Override 
    public void pushFront(T data){  
        Node<T> newNode = new Node<>(data);
        if(!isEmpty()) head.prev = newNode;
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

        Node<T> curr = head;
        while(curr.next != null){
            curr = curr.next;
        }

        curr.next = newNode;
        newNode.prev = curr;
        size++;
    }
    
    @Override
    public T popFront() {
        
        return null;
    }

}