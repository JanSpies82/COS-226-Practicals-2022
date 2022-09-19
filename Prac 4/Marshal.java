public class Marshal extends Thread {

	private volatile VotingStation vs;
	public volatile MCSNode node = null;

	Marshal(VotingStation _vs) {
		vs = _vs;
	}

	@Override
	public void run() {
		for (int i = 1; i <= 5; i++) {
			node = new MCSNode(i);
			vs.castBallot(i);
		}

	}

	public MCSNode getNode() {
		return node;
	}
}
