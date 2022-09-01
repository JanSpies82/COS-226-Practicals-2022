import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // System.out.print("Please select either Task1 or Task2: ");
        // Scanner input = new Scanner(System.in);
        // String task = input.nextLine();
        String task = "1";
        if (task.equals("1")) {
            Task1();
        } else if (task.equals("2")) {
            Task2();
        } else {
            System.out.println("Invalid input");
        }
        // input.close();
    }

    public static void Task1() {
        System.out.println("Starting Task1");
        ConsensusThread[] threads = new ConsensusThread[2];
        RMWConsensus<Integer> consensus = new RMWConsensus<Integer>(2);
        Starter s = new Starter();

        threads[0] = new ConsensusThread(consensus, s);
        threads[1] = new ConsensusThread(consensus, s);

        for (Thread t : threads)
            t.start();
    }

    public static void Task2() {
        System.out.println("Hello world2");
    }
}
