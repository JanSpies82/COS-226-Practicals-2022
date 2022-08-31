public interface Consensus<T>
{
	Object decide();
	void propose(T value);
}
