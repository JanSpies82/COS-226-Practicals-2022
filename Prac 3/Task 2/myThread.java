import java.util.concurrent.locks.Lock;

public class myThread {
    private volatile Lock lock;

    public myThread(Lock lock) {
        this.lock = lock;
    }

    public void setLock(Lock lock) {
        this.lock = lock;
    }

    public void run() {
        lock.lock();
        try {
            Thread.currentThread().sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
