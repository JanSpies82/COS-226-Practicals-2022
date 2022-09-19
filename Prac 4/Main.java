public class Main {
    public static void main(String args[]) throws InterruptedException {
        VotingStation vs = new VotingStation(new MCSQueue());
        int size = 5;
        Marshal[] marshals = new Marshal[size];

        for (int i = 0; i < size; i++)
            marshals[i] = new Marshal(vs);

        for (Marshal m : marshals){
            m.start();
            // Thread.sleep(5);
        }
    }
}
