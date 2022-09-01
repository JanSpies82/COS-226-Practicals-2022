public class RMWConsensus<T> extends ConsensusProtocol<T> {
    private RMWRegister r = new RMWRegister();
    public RMWConsensus(int threadCount) {
        super(threadCount);
    }

    @Override
    public T decide(T value) {
        propose(value);
        
    }

}
