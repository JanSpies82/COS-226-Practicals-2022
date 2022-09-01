import java.util.concurrent.locks.Lock;

public class myThread extends Thread {
    private volatile Lock lock;
    private int num;

    public myThread(int num) {
        this.num = num;
    }

    public void setLock(Lock lock) {
        this.lock = lock;
    }

    public void run() {
        for (int h = 0; h < num; h++) {
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
}
