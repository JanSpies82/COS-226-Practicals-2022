public interface Consensus<T> {
	T decide();
	void propose(T value);
}
