import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings({ "unchecked", "rawtypes", "generics" })
public class myQueue<T> {
    AtomicInteger head, tail, size;
    Node[] array;
    // Lock enqLock, deqLock;
    int capacity;
    // volatile Condition notFull, notEmpty;
    volatile boolean notFull, notEmpty;

    public myQueue() {
        head = new AtomicInteger(0);
        tail = new AtomicInteger(0);
        size = new AtomicInteger(0);
        array = new Node[15];
        capacity = 15;
        notFull = true;
        notEmpty = false;
        // enqLock = new ReentrantLock();
        // deqLock = new ReentrantLock();
        // notFull = enqLock.newCondition();
        // notEmpty = deqLock.newCondition();
    }

    public boolean enq(T x) {
        boolean wakeDeqs = false;
        // enqLock.lock();
        // try {
        if (!notFull || size.get() == capacity) {
            // try {
            // notFull = false;
            return false;
            // } catch (InterruptedException e) {
            // e.printStackTrace();
            // }
        }
        Node e = new Node(x);
        int t = tail.getAndSet((tail.get() + 1) % capacity);
        array[t] = e;
        // tail.set();
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
        // } finally {
        // enqLock.unlock();
        // }
        if (wakeDeqs) {
            notEmpty = true;
            // deqLock.lock();
            // try {
            // notEmpty.signalAll();
            // } finally {
            // deqLock.unlock();
            // }
        }
        return true;
    }

    public synchronized T deq() {
        T result = null;
        boolean wakeEnqs = false;
        // deqLock.lock();
        // try {
        if (!notEmpty || size.get() == 0) {
            // notEmpty = false;
            return null;
        }
        // System.out.println(myThread.CYAN + "h: " + h + myThread.RESET);
        if (array[head.get()] == null)
            return null;
        int h = head.getAndSet((head.get() + 1) % capacity);
        result = (T) array[h].value;
        array[h] = null;
        // head.set((head.get() + 1) % array.length);
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
        // } finally {
        // deqLock.unlock();
        // }

        if (wakeEnqs) {
            notFull = true;
            // enqLock.lock();
            // try {
            // notFull.signalAll();
            // } finally {
            // enqLock.unlock();
            // }
        }

        return result;
    }

}
