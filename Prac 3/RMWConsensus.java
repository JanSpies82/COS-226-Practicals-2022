public class RMWConsensus<T> extends ConsensusProtocol<T> {
    private static volatile RMWRegister r;

    public RMWConsensus(int threadCount) {
        super(threadCount);
    }

    @Override
    public void propose(T value) {
        super.propose(value);
        r = RMWRegister.getInstance(value);
        // r = new RMWRegister<T>(value);
    }

    @Override
    public T decide() {
        if (r.getAndMumble() != proposed[Integer.parseInt(String.valueOf(Thread.currentThread().getName().charAt(7)))])
            return (T) proposed[Integer.parseInt(String.valueOf(Thread.currentThread().getName().charAt(7)))];
        else
            return (T) proposed[(Integer.parseInt(String.valueOf(Thread.currentThread().getName().charAt(7))) + 1) % 2];
    }

}
