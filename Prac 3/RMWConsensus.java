@SuppressWarnings({ "rawtypes", "unchecked" })
public class RMWConsensus<T> extends ConsensusProtocol<T> {
    private static volatile RMWRegister r;
    public static final String RESET = "\033[0m";
    public static final String BLACK = "\033[0;30m";
    public static final String RED = "\033[0;31m";
    public static final String GREEN = "\033[0;32m";
    public static final String YELLOW = "\033[0;33m";
    public static final String BLUE = "\033[0;34m";
    public static final String PURPLE = "\033[0;35m";
    public static final String CYAN = "\033[0;36m";
    public static final String WHITE = "\033[0;37m";

    public RMWConsensus(int threadCount) {
        super(threadCount);
        r = RMWRegister.getInstance();
    }

    @Override
    public void propose(T value) {
        super.propose(value);
        r.propose(value);
    }

    @Override
    public T decide() {
        T v = (T) r.getAndMumble();
        int me = Integer.parseInt(String.valueOf(Thread.currentThread().getName().charAt(7)));
        // if ((v ==
        // proposed[Integer.parseInt(String.valueOf(Thread.currentThread().getName().charAt(7)))])
        // || (v == null &&
        // proposed[(Integer.parseInt(String.valueOf(Thread.currentThread().getName().charAt(7)))+1)%2]
        // == null))
        // return (T)
        // proposed[Integer.parseInt(String.valueOf(Thread.currentThread().getName().charAt(7)))];
        // else
        // return (T)
        // proposed[(Integer.parseInt(String.valueOf(Thread.currentThread().getName().charAt(7)))
        // + 1) % 2];

        if ((v == proposed[me]) || (v == null && proposed[(me + 1) % 2] == null)) {
            System.out.println(GREEN + "Thread " + me + ": other proposed - " + proposed[(me + 1) % 2] + RESET);
            return (T) proposed[me];
        } else
            return (T) proposed[(me + 1) % 2];
    }

}
