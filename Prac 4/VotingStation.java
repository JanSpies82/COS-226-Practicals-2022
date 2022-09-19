import java.util.concurrent.locks.Lock;

public class VotingStation {
	Lock l;
	public static final String RESET = "\033[0m";
	public static final String BLACK = "\033[0;30m";
	public static final String RED = "\033[0;31m";
	public static final String GREEN = "\033[0;32m";
	public static final String YELLOW = "\033[0;33m";
	public static final String BLUE = "\033[0;34m";
	public static final String PURPLE = "\033[0;35m";
	public static final String CYAN = "\033[0;36m";
	public static final String WHITE = "\033[0;37m";

	public VotingStation(Lock l) {
		this.l = l;
	}

	public void castBallot(int person) {
		System.out.println(Thread.currentThread().getName() + " Person " + person + " enters voting station" + RESET);
		try {
			l.lock();

			System.out.println(YELLOW + Thread.currentThread().getName() + " Person " + person + " casts a ballot" + RESET);
			try {
				Thread.sleep((int) Math.floor(Math.random() * (1000 + 1 - 200 + 1) + 200));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} finally {
			System.out.println(Thread.currentThread().getName() + " Person " + person + " exits" + RESET);
			l.unlock();
		}
	}
}
