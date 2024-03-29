import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class BackoffLock implements Lock {
    AtomicBoolean state = new AtomicBoolean(false);

    @Override
    public void lock() {
        int delay = 1;
        while (true) {
            while (state.get()) {
            }
            if (!state.getAndSet(true)) {
                return;
            }
            try {
                TimeUnit.MILLISECONDS.sleep((long) Math.random() % delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (delay < 1024) {
                delay *= 2;
            }
        }
    }

    @Override
    public void unlock() {
        state.set(false);
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
