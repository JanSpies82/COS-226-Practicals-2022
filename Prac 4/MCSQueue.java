import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

@SuppressWarnings({ "rawtypes", "unchecked" })

public class MCSQueue implements Lock {
    AtomicReference tail;
    public static final String RESET = "\033[0m";
    public static final String BLACK = "\033[0;30m";
    public static final String RED = "\033[0;31m";
    public static final String GREEN = "\033[0;32m";
    public static final String YELLOW = "\033[0;33m";
    public static final String BLUE = "\033[0;34m";
    public static final String PURPLE = "\033[0;35m";
    public static final String CYAN = "\033[0;36m";
    public static final String WHITE = "\033[0;37m";

    public MCSQueue() {
        tail = new AtomicReference(null);
    }

    @Override
    public void lock() {
        // MCSNode node = ((Marshal) Thread.currentThread()).node;
        MCSNode pred = (MCSNode) tail.getAndSet(((Marshal) Thread.currentThread()).node);
        // System.out.println(tail.get() != null
        //         ? "Tail for " + Thread.currentThread().getName() + " " + ((Marshal) Thread.currentThread()).node.name + " is "
        //                 + ((MCSNode) tail.get()).name
        //         : "Tail for " + Thread.currentThread().getName() + " " + ((Marshal) Thread.currentThread()).node.name + " is null");
                ((Marshal) Thread.currentThread()).node.prev = pred;
        if (pred != null) {
            // System.out.println(YELLOW + Thread.currentThread().getName() + " has a pred : " + pred.name + RESET);
            ((Marshal) Thread.currentThread()).node.locked = true;
            pred.next = ((Marshal) Thread.currentThread()).node;
            while (((Marshal) Thread.currentThread()).node.locked) {
            }
        }
    }

    @Override
    public void unlock() {
        MCSNode node = ((Marshal) Thread.currentThread()).node;
        if (node.next == null) {
            // System.out.println(Thread.currentThread().getName() + " " + node.name + " has no next");
            if (tail.compareAndSet(node, null)) {
                return;
            }
            while (node.next == null) {
            }
        }
        node.next.locked = false;
    }

    // private MCSNode getCurrentNode() {
    //     MCSNode node = (MCSNode) tail.get();
    //     while (node.prev != null && node.prev.locked) {
    //         node = node.prev;
    //     }
    //     return node;
    // }

    @Override
    public void lockInterruptibly() throws InterruptedException {
    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public Condition newCondition() {
        return null;
    }

}
