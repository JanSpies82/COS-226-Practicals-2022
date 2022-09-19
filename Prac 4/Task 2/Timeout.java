import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

@SuppressWarnings({ "rawtypes", "unchecked" })

public class Timeout implements Lock {
    static MCSNode AVAILABLE = new MCSNode();
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

    public Timeout() {
        tail = new AtomicReference(null);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        // ((Marshal) Thread.currentThread()).node
        ((Marshal) Thread.currentThread()).node.prev = null;
        MCSNode myPred = (MCSNode) tail.getAndSet(((Marshal) Thread.currentThread()).node);
        if (myPred == null || myPred.prev == AVAILABLE) {
            ((Marshal) Thread.currentThread()).node.status = "WAITING";
            return true;
        }
        myPred.next = ((Marshal) Thread.currentThread()).node;

        long startTime = System.nanoTime();
        while (System.nanoTime() - startTime < unit.toNanos(time)) {
            System.out.print("");
            MCSNode predPred = myPred.prev;
            if (predPred == AVAILABLE) {
                ((Marshal) Thread.currentThread()).node.status = "WAITING";
                return true;
            } else if (predPred != null)
                myPred = predPred;
        }
        if (!tail.compareAndSet(((Marshal) Thread.currentThread()).node, myPred))
            ((Marshal) Thread.currentThread()).node.prev = myPred;
        System.out.println(RED + ((Marshal) Thread.currentThread()).node.name + " timed out" + RESET);
        ((Marshal) Thread.currentThread()).node.status = "TIMED OUT";
        return false;
    }

    @Override
    public void unlock() {
        String out = "";
        out += GREEN + "QUEUE: " + RESET;
        MCSNode temp = ((Marshal) Thread.currentThread()).node;
        out += "{" + temp.name + "}";
        temp = temp.next;
        while (temp != null && temp.status != "TIMED OUT") {
            out += "->{" + temp.name + "}";
            temp = temp.next;
        }

        MCSNode nexts = null;
        if (temp != null && temp.status == "TIMED OUT") {
            nexts = temp.next;
        }

        while (nexts != null && nexts.status != "TIMED OUT") {
            out += "->{" + nexts.name + "}";
            nexts = nexts.next;
        }
        System.out.println(out);

        if (!tail.compareAndSet(((Marshal) Thread.currentThread()).node, null))
            ((Marshal) Thread.currentThread()).node.prev = AVAILABLE;
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
    }

    @Override
    public void lock() {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public Condition newCondition() {
        return null;
    }

}
