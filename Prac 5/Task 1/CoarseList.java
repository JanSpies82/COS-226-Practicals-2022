import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class CoarseList<T> {
    private Node<T> head;
    private Lock lock = new ReentrantLock();

    public static final String RESET = "\033[0m";
    public static final String BLACK = "\033[0;30m";
    public static final String RED = "\033[0;31m";
    public static final String GREEN = "\033[0;32m";
    public static final String YELLOW = "\033[0;33m";
    public static final String BLUE = "\033[0;34m";
    public static final String PURPLE = "\033[0;35m";
    public static final String CYAN = "\033[0;36m";
    public static final String WHITE = "\033[0;37m";

    public CoarseList() {
        head = new Node(Integer.MIN_VALUE, -1, 0);
        head.next = new Node(Integer.MAX_VALUE, -1, 0);

    }

    public boolean add(T item, int person, long time) {
        Node pred, curr;
        int key = item.hashCode();
        lock.lock();
        try {
            pred = head;
            curr = pred.next;
            while (curr.key < key) {
                pred = curr;
                curr = curr.next;
            }
            if (key == curr.key)
                return false;
            else {
                Node node = new Node(item, person, time);
                System.out.println(node.getName() + GREEN + " has been added" + RESET);
                node.next = curr;
                pred.next = node;
                return true;
            }
        } finally {
            lock.unlock();
        }
    }

    public boolean remove(T item) {
        Node pred, curr;
        int key = item.hashCode();
        lock.lock();
        printList();
        try {
            pred = head;
            curr = pred.next;
            while (curr.key < key) {
                pred = curr;
                curr = curr.next;
            }
            if (key == curr.key) {
                pred.next = curr.next;
                return true;
            } else
                return false;

        } finally {
            lock.unlock();
        }
    }

    private void printList() {
        Node curr = head.next;
        String out = RED + "List: " + RESET;
        if (curr.person != -1)
            out += "[" + curr.getName() + "]";
        while (curr.next != null) {
            curr = curr.next;
            if (curr.person != -1)
                out += "-> [" + curr.getName() + "]";
        }
        System.out.println(out);
    }

}
