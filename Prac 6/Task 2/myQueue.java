import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings({ "unchecked", "rawtypes", "generics" })
public class myQueue<T> {
    AtomicInteger head, tail, size;
    Node[] array;
    int capacity;
    volatile boolean notFull, notEmpty;

    public myQueue() {
        head = new AtomicInteger(0);
        tail = new AtomicInteger(0);
        size = new AtomicInteger(0);
        array = new Node[15];
        capacity = 15;
        notFull = true;
        notEmpty = false;
    }

    public boolean enq(T x) {
        boolean wakeDeqs = false;
        if (!notFull || size.get() == capacity) {
            return false;
        }
        Node e = new Node(x);
        int t = tail.getAndSet((tail.get() + 1) % capacity);
        array[t] = e;
        if (size.getAndIncrement() == 0)
            wakeDeqs = true;

        String out = myThread.GREEN + "Enqueued " + x + myThread.RESET + "\n";
        out += "QUEUE [" + size.get() + "]: ";
        int i = head.get();
        boolean first = true;
        while (i != tail.get() || first) {
            out += "{" + array[i].value + "} -> ";
            i = (i + 1) % array.length;
            first = false;
        }
        System.out.println(out);

        if (wakeDeqs) {
            notEmpty = true;
        }
        return true;
    }

    public synchronized T deq() {
        T result = null;
        boolean wakeEnqs = false;
        if (!notEmpty || size.get() == 0) {
            return null;
        }
        if (array[head.get()] == null)
            return null;
        int h = head.getAndSet((head.get() + 1) % capacity);
        result = (T) array[h].value;
        array[h] = null;
        if (size.getAndDecrement() == capacity)
            wakeEnqs = true;

        String out = myThread.RED + "Dequeued " + result + myThread.RESET + "\n";
        out += "QUEUE [" + size.get() + "]: ";
        int i = head.get();
        while (i != tail.get()) {
            out += "{" + array[i].value + "} -> ";
            i = (i + 1) % array.length;
        }
        System.out.println(out);

        if (wakeEnqs) {
            notFull = true;
        }

        return result;
    }

}
