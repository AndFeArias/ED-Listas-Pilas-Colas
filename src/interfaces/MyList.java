package interfaces;

public interface MyList<T>{
    void pushFront(T data);
    void pushBack(T data);
    T popFront();
    T popBack();
    boolean find(T data);
    boolean erase(T data);
    boolean addBefore(T target, T data);
    boolean addAfter(T target, T data);

    //adicionales
    boolean isEmpty();
    int size();
    T topFront();
    T topBack();
    String toString();
}