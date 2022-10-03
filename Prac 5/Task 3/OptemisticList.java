@SuppressWarnings({ "unchecked", "rawtypes" })
public class OptemisticList<T> {
    private Node<T> head;

    public static final String RESET = "\033[0m";
    public static final String BLACK = "\033[0;30m";
    public static final String RED = "\033[0;31m";
    public static final String GREEN = "\033[0;32m";
    public static final String YELLOW = "\033[0;33m";
    public static final String BLUE = "\033[0;34m";
    public static final String PURPLE = "\033[0;35m";
    public static final String CYAN = "\033[0;36m";
    public static final String WHITE = "\033[0;37m";

    public OptemisticList() {
        head = new Node(Integer.MIN_VALUE, -1, 0);
        head.next = new Node(Integer.MAX_VALUE, -1, 0);

    }

    private boolean validate(Node pred, Node curr) {
        Node node = head;
        while (node.key <= pred.key) {
            if (node == pred)
                return pred.next == curr;
            node = node.next;
        }
        return false;
    }

    public boolean add(T item, int person, long time) {
        Node pred = head, curr = pred.next;
        int key = item.hashCode();
        while (curr.key <= key) {
            if (item == curr.item)
                break;
            pred = curr;
            curr = curr.next;
        }
        try {
            pred.lock.lock();
            curr.lock.lock();
            if (validate(pred, curr)) {
                if (key == curr.key)
                    return false;
                else {
                    Node node = new Node(item, person, time);
                    node.next = curr;
                    pred.next = node;
                    System.out.println(node.getName() + GREEN + " has been added" + RESET);
                    return true;
                }
            } else
                return false;
        } finally {
            pred.lock.unlock();
            curr.lock.unlock();
        }
    }

    public boolean remove(T item) {
        Node pred = head, curr = head.next;
        int key = item.hashCode();
        printList();

        while (curr.key <= key) {
            if (item == curr.item)
                break;
            pred = curr;
            curr = curr.next;
        }
        try {
            pred.lock.lock();
            curr.lock.lock();
            if (validate(pred, curr)) {
                if (curr.item == item) {
                    pred.next = curr.next;
                    return true;
                } else
                    return false;
            }
            return false;
        } finally {
            curr.lock.unlock();
            pred.lock.unlock();
        }
    }

    private void printList() {
        Node curr = head.next;
        String out = RED + "List: " + RESET;
        if (curr.person != -1)
            out += "[" + YELLOW + curr.tName + RESET + " (" + BLUE + "P-" +
                    +curr.person + RESET + ", " + ((curr.time - (System.currentTimeMillis() - curr.startTime) > 0)
                            ? (curr.time - (System.currentTimeMillis() - curr.startTime))
                            : 0)
                    + "ms)"
                    + "]";
        while (curr.next != null) {
            curr = curr.next;
            if (curr.person != -1)
                out += " -> [" + YELLOW + curr.tName + RESET + " (" + BLUE + "P-" +
                        +curr.person + RESET + ", "
                        + ((curr.time - (System.currentTimeMillis() - curr.startTime) > 0)
                                ? (curr.time - (System.currentTimeMillis() - curr.startTime))
                                : 0)
                        + "ms)" + "]";
        }
        System.out.println(out);
    }

}
