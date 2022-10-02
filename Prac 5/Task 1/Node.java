public class Node<T> {
    public T item;
    public int key;
    public Node next;

    public Node(T item) {
        this.item = item;
        this.key = item.hashCode();
        this.next = null;
    }
}
