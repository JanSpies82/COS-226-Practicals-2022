public class ConsensusThread extends Thread {
	private Consensus<Integer> consensus;
	private volatile Starter s;

	public static final String RESET = "\033[0m";
	public static final String BLACK = "\033[0;30m";
	public static final String RED = "\033[0;31m";
	public static final String GREEN = "\033[0;32m";
	public static final String YELLOW = "\033[0;33m";
	public static final String BLUE = "\033[0;34m";
	public static final String PURPLE = "\033[0;35m";
	public static final String CYAN = "\033[0;36m";
	public static final String WHITE = "\033[0;37m";

	ConsensusThread(Consensus<Integer> consensusObject, Starter s) {
		consensus = consensusObject;
		this.s = s;
	}

	public void run() {
		for (int j = 0; j < 5; j++) {
			while (!s.canStart()) {
			}
			int amount = (int) Math.floor(Math.random() * (200 + 1 - 100 + 1) + 100);
			double time = (int) Math.floor(Math.random() * (50 + 1 - 100 + 1) + 100);
			System.out.println(
					Thread.currentThread().getName() + " proposes " + CYAN + amount + RESET +" and is about to sleep for " + time);
			consensus.propose(amount);
			s.isNotReady(Integer.parseInt(String.valueOf(Thread.currentThread().getName().charAt(7))));
			try {
				Thread.sleep((long) time);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out
					.println(Thread.currentThread().getName() + " has decided " + YELLOW + consensus.decide() + RESET);
			s.isReady(Integer.parseInt(String.valueOf(Thread.currentThread().getName().charAt(7))));
		}
	}
}
