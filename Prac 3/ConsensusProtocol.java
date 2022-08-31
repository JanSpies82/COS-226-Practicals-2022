public abstract class ConsensusProtocol<T> implements Consensus<T> {
	public volatile Object[] proposed;

	public ConsensusProtocol(int threadCount) {
		proposed = new Object[threadCount];
	}

	public void propose(T value) {
		int i = 0;
		while (i < proposed.length && proposed[i] != null) {
			i++;
		}
		if (i < proposed.length) {
			proposed[i] = value;
		}
	}

	abstract public void decide();
}
