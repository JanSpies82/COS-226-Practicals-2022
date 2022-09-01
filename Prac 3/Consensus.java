public interface Consensus<T>
{
	T decide(T value);
	void propose(T value);
}
