import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class myQueue<T> {
    AtomicInteger head, tail, size;
    Node[] array;
    Lock enqLock, deqLock;
    static volatile Condition notFull, notEmpty;

    public myQueue() {
        head = new AtomicInteger(0);
        tail = new AtomicInteger(0);
        size = new AtomicInteger(0);
        array = new Node[5];
        // for (int i = 0; i < 5; i++)
        // array[i] = null;
        enqLock = new ReentrantLock();
        deqLock = new ReentrantLock();
        notFull = enqLock.newCondition();
        notEmpty = deqLock.newCondition();
    }

    public boolean enq(T x) {
        boolean wakeDeqs = false;
        enqLock.lock();
        try {
            while (size.get() == array.length) {
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

            // if (size.getAndIncrement() == 0)
            //     notEmpty.signalAll();

            // }
            // wakeDeqs = true;
        } finally {
            enqLock.unlock();
        }
        // synchronized (notEmpty) {
        // if (wakeDeqs)
        // notEmpty.signalAll();
        // }

        System.out.println(myThread.GREEN + "Enqueued " + x + myThread.RESET);
        return true;
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
            if (size.getAndDecrement() == array.length)
                wakeEnqs = true;
        } finally {
            deqLock.unlock();
        }

        // synchronized (notFull) {
        //     if (wakeEnqs)
        //         notFull.signalAll();
        // }

        System.out.println(myThread.RED + "Dequeued " + result + myThread.RESET);
        return result;
    }

}
