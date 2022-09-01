public class ConsensusThread extends Thread {
	private Consensus<Integer> consensus;

	ConsensusThread(Consensus<Integer> consensusObject) {
		consensus = consensusObject;
	}

	public void run() {
		int amount = (int) Math.floor(Math.random() * (200 + 1 - 100 + 1) + 100);
		double time = (int) Math.floor(Math.random() * (50 + 1 - 100 + 1) + 100);
		System.out.println(
				Thread.currentThread().getName() + " proposes " + amount + " and is about to sleep for " + time);
		consensus.propose(amount);
		try {
			Thread.sleep((long) time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + " has decided " + consensus.decide());
	}
}
