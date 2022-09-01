public abstract class ConsensusProtocol<T> implements Consensus<T> {
	public volatile Object[] proposed;

	public ConsensusProtocol(int threadCount) {
		proposed = new Object[threadCount];
	}

	public void propose(T value) {
		proposed[Integer.parseInt(String.valueOf(Thread.currentThread().getName().charAt(7)))] = value;
	}

	abstract public T decide(T value);
}
