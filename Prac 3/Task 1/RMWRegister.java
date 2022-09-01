@SuppressWarnings("rawtypes")
public class RMWRegister<T> {
    private T value;
    private static RMWRegister instance = null;
    int lastaccessed;

    public static final String RESET = "\033[0m";
    public static final String BLACK = "\033[0;30m";
    public static final String RED = "\033[0;31m";
    public static final String GREEN = "\033[0;32m";
    public static final String YELLOW = "\033[0;33m";
    public static final String BLUE = "\033[0;34m";
    public static final String PURPLE = "\033[0;35m";
    public static final String CYAN = "\033[0;36m";
    public static final String WHITE = "\033[0;37m";

    public static RMWRegister getInstance() {
        if (instance == null) {
            synchronized (RMWRegister.class) {
                if (instance == null)
                    instance = new RMWRegister();
            }
        }
        return instance;
    }

    private RMWRegister() {
        this.value = null;
    }

    public void propose(T value) {
        if (this.value == null)
            this.value = value;
        lastaccessed = Integer.parseInt(String.valueOf(Thread.currentThread().getName().charAt(7)));
    }

    public synchronized T getAndMumble() {
        if (Integer.parseInt(String.valueOf(Thread.currentThread().getName().charAt(7))) == lastaccessed)
            return null;
        T prior = value;
        System.out.println("REGISTER VALUE: " + RED + value + RESET);
        value = null;
        return prior;
    }

}
