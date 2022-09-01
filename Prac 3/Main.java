import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        System.out.print("Please select either Task1 or Task2: ");
        Scanner input = new Scanner(System.in);
        String task = input.nextLine();
        if (task.equals("1")) {
            Task1();
        } else if (task.equals("2")) {
            Task2();
        } else {
            System.out.println("Invalid input");
        }
        input.close();
    }

    public static void Task1() {
        System.out.println("Starting Task1");
        ConsensusThread[] threads = new ConsensusThread[2];
        RMWConsensus<Integer> consensus = new RMWConsensus<Integer>(2);

        for (int i = 0; i < 2; i++)
            threads[i] = new ConsensusThread(consensus);

        // for (int g = 0; g < 3; g++) {
            for (Thread t : threads)
                t.start();
        // }

    }

    public static void Task2() {
        System.out.println("Hello world2");
    }
}