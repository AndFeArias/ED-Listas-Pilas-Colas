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

public class DoublyLinkedListWithTail<T> implements MyList<T>{
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public DoublyLinkedListWithTail(){
        head = null;
        tail = null;
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

        return tail.data;
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
        newNode.next = head;
        newNode.prev = null;

        if(!isEmpty()){
            head.prev = newNode;
        }else{
            tail = newNode;
        }
        head = newNode;
        size++;
    }

    @Override 
    public void pushBack(T data){
        Node<T> newNode = new Node<>(data);

        if(tail == null){
            tail = newNode;
            head = newNode;
        }else{
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }
    
    @Override
    public T popFront() {
        if(isEmpty()) throw new NoSuchElementException("No se puede eliminar nada: La lista esta vacia");

        T eliminado = head.data;
        head = head.next;
        if(!isEmpty()){
            head.prev = null;
        }else tail = null;
        size--;
        return eliminado;
    }

    @Override 
    public T popBack(){
        if(isEmpty()) throw new NoSuchElementException("No se puede eliminar nada: La lista esta vacia");

        T eliminado = tail.data;
        if(head == tail){
            head = null;
            tail = null;
        }else{
            tail = tail.prev;
            tail.next = null;
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
    public boolean erase(T data){
        if(isEmpty()) return false;

        if(head.data.equals(data)){
            popFront();
            return true;
        }else if(tail.data.equals(data)){
            popBack();
            return true;
        }

        Node<T> curr = head;

        while(curr != null && !curr.data.equals(data)){
            curr = curr.next;
        }

        if(curr != null){
            curr.prev.next = curr.next;
            curr.next.prev = curr.prev;
            size--;
            return true;
        }

        return false;
    }

    @Override 
    public boolean addBefore(T target, T data){
        if(isEmpty()) return false;

        if(head.data.equals(target)){
            pushFront(data);
            return true;
        }
        Node<T> newNodo = new Node<>(data);

        Node<T> curr = head;
        while(curr != null && !curr.data.equals(target)){
            curr = curr.next;
        }
        if(curr != null){
            newNodo.next = curr;
            newNodo.prev = curr.prev;

            curr.prev.next = newNodo;
            curr.prev = newNodo;
            size++;
            return true;
        }

        return false;
    }

    @Override public boolean addAfter(T target, T data){
        if(isEmpty()) return false;


        Node<T> curr = head;
        while(curr != null && !curr.data.equals(target)){
            curr = curr.next;
        }
        if(curr != null){

            if(curr == tail){
                pushBack(data);
                return true;
            }
            Node<T> newNode = new Node<>(data);
            
            newNode.prev = curr;
            newNode.next = curr.next;
            curr.next.prev = newNode;
            curr.next = newNode;
            size++;
            return true;
        }
        return false;
    }

}