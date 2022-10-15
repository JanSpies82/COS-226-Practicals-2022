public class myThread extends Thread {
    public volatile myQueue q;
    public static final String RESET = "\033[0m";
    public static final String BLACK = "\033[0;30m";
    public static final String RED = "\033[0;31m";
    public static final String GREEN = "\033[0;32m";
    public static final String YELLOW = "\033[0;33m";
    public static final String BLUE = "\033[0;34m";
    public static final String PURPLE = "\033[0;35m";
    public static final String CYAN = "\033[0;36m";
    public static final String WHITE = "\033[0;37m";

    public myThread(myQueue q) {
        this.q = q;
    }

    public void run() {
        for (int i = 1; i <= 5; i++) {
            q.enq(this);
            try {
                Thread.sleep((int) Math.floor(Math.random() * (1000 - 1 + 2) + 1));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            q.deq();
        }
    }
}
