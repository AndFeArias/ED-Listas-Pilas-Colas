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

    public static void main(String[] args) {
    
    }


}
