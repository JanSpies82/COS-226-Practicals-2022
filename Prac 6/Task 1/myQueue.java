import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@SuppressWarnings({ "unchecked", "rawtypes", "generics" })
public class myQueue<T> {
    AtomicInteger head, tail, size;
    Node[] array;
    Lock enqLock, deqLock;
    int capacity;
    volatile Condition notFull, notEmpty;
    volatile boolean first = true;

    public myQueue() {
        head = new AtomicInteger(0);
        tail = new AtomicInteger(0);
        size = new AtomicInteger(0);
        array = new Node[5];
        capacity = 5;
        enqLock = new ReentrantLock();
        deqLock = new ReentrantLock();
        notFull = enqLock.newCondition();
        notEmpty = deqLock.newCondition();
    }

    public void enq(T x) {
        boolean wakeDeqs = false;
        enqLock.lock();
        try {
            while (size.get() == capacity) {
                try {
                    notFull.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Node e = new Node(x);
            array[tail.get()] = e;
            tail.set((tail.get() + 1) % array.length);
            // synchronized (notEmpty){
            // size.getAndIncrement();
            // TODO fix signalAll
            // synchronized (notEmpty) {
            if (size.getAndIncrement() == 0/* && !first */)
                wakeDeqs = true;
            // }
            // first = false;
            // }
            // wakeDeqs = true;
            System.out.println(myThread.GREEN + "Enqueued " + x + myThread.RESET);
            String out = "QUEUE [" + size.get() + "]: ";
            int i = head.get();
            boolean first = true;
            while (i != tail.get() || first) {
                out += "{" + array[i].value + "} -> ";
                i = (i + 1) % array.length;
                first = false;
            }
            System.out.println(out);
            // } catch (Exception e) {
        } finally {
            enqLock.unlock();
        }
        // synchronized (notEmpty) {
        if (wakeDeqs) {
            deqLock.lock();
            try {
                notEmpty.signalAll();
            } finally {
                deqLock.unlock();
            }
        }
        // notEmpty.signalAll();
        // }
    }

    public synchronized T deq() {
        T result = null;
        boolean wakeEnqs = false;
        deqLock.lock();
        try {
            while (size.get() == 0) {
                try {
                    // synchronized (notEmpty) {
                    notEmpty.await();
                    // }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            result = (T) array[head.get()].value;
            array[head.get()] = null;
            head.set((head.get() + 1) % array.length);
            // TODO fix signalAll
            // synchronized (notFull) {
            if (size.getAndDecrement() == capacity)
                wakeEnqs = true;
            // }
            System.out.println(myThread.RED + "Dequeued " + result + myThread.RESET);
            String out = "QUEUE [" + size.get() + "]: ";
            int i = head.get();
            while (i != tail.get()) {
                out += "{" + array[i].value + "} -> ";
                i = (i + 1) % array.length;
            }
            System.out.println(out);
            // } catch (Exception e) {
        } finally {
            deqLock.unlock();
        }

        // synchronized (notFull) {
        // if (wakeEnqs)
        // notFull.signalAll();
        // }
        if (wakeEnqs) {
            enqLock.lock();
            try {
                notFull.signalAll();
            } finally {
                enqLock.unlock();
            }
        }

        return result;
    }

}
