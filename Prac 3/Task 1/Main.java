class Main {
    public static void main(String[] args) {
        System.out.println("Starting Task1");
        ConsensusThread[] threads = new ConsensusThread[2];
        RMWConsensus<Integer> consensus = new RMWConsensus<Integer>(2);
        Starter s = new Starter();

        threads[0] = new ConsensusThread(consensus, s);
        threads[1] = new ConsensusThread(consensus, s);

        for (Thread t : threads)
            t.start();
    }
}
