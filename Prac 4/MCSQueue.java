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
        // ? "Tail for " + Thread.currentThread().getName() + " " + ((Marshal)
        // Thread.currentThread()).node.name + " is "
        // + ((MCSNode) tail.get()).name
        // : "Tail for " + Thread.currentThread().getName() + " " + ((Marshal)
        // Thread.currentThread()).node.name + " is null");
        ((Marshal) Thread.currentThread()).node.prev = pred;
        if (pred != null) {
            // System.out.println(YELLOW + Thread.currentThread().getName() + " has a pred :
            // " + pred.name + RESET);
            ((Marshal) Thread.currentThread()).node.locked = true;
            pred.next = ((Marshal) Thread.currentThread()).node;
            while (((Marshal) Thread.currentThread()).node.locked) {
            }
        }
    }

    @Override
    public void unlock() {
        // MCSNode node = ((Marshal) Thread.currentThread()).node;
        String out = "";
        out += GREEN + "QUEUE: " + RESET;
        MCSNode temp = ((Marshal) Thread.currentThread()).node;
        out += "{" + temp.name + "}";
        temp = temp.next;
        while (temp != null) {
            out += "->{" + temp.name + "}";
            temp = temp.next;
        }
        System.out.println(out);

        if (((Marshal) Thread.currentThread()).node.next == null) {
            if (tail.compareAndSet(((Marshal) Thread.currentThread()).node, null)) {
                return;
            }
            while (((Marshal) Thread.currentThread()).node.next == null) {
            }
        }
        ((Marshal) Thread.currentThread()).node.next.locked = false;
    }

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
