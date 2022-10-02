public class Guard extends Thread {
    private volatile Gallery g;
    public int person;

    public static final String RESET = "\033[0m";
    public static final String RED = "\033[0;31m";
    public static final String GREEN = "\033[0;32m";
    public static final String YELLOW = "\033[0;33m";
    public static final String BLUE = "\033[0;34m";

    public Guard(Gallery g) {
        this.g = g;
    }

    public void run() {
        for (int i = 1; i <= 2; i++) {
            person = i;
            long time = (int) Math.floor(Math.random() * (1000 + 1 - 100 + 1) + 100);
            g.enter(person, time);
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            g.exit();
        }
    }
}
