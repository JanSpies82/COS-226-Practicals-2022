public abstract class ConsensusProtocol<T> implements Consensus<T> {
	public volatile Object[] proposed;

	public ConsensusProtocol(int threadCount) {
		proposed = new Object[threadCount];
	}

	public void propose(T value) {
		proposed[proposed[0] == null ? 0 : 1] = value;
	}

	abstract public Object decide();
}
