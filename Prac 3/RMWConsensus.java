import java.util.concurrent.atomic.AtomicInteger;

public class RMWConsensus<T> extends ConsensusProtocol<T> {
    // private RMWRegister r = new RMWRegister();
    private final int FIRST = -1;
    private AtomicInteger counter = new AtomicInteger(FIRST);

    public RMWConsensus(int threadCount) {
        super(threadCount);
    }

    @Override
    public T decide(T value) {
        propose(value);
        if (counter.getAndSet(Integer.parseInt(String.valueOf(Thread.currentThread().getName().charAt(7)))) == FIRST)
            return (T) proposed[Integer.parseInt(String.valueOf(Thread.currentThread().getName().charAt(7)))];
        else
            return (T) proposed[(Integer.parseInt(String.valueOf(Thread.currentThread().getName().charAt(7))) + 1) % 2];
    }

}
