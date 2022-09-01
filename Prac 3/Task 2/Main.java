import java.text.DecimalFormat;
import java.util.concurrent.locks.Lock;

public class Main {

    public myThread[][] createArrays(int n) {
        myThread[][] threads = new myThread[6][];
        threads[0] = new myThread[1];
        threads[1] = new myThread[2];
        threads[2] = new myThread[5];
        threads[3] = new myThread[15];
        threads[4] = new myThread[30];
        threads[5] = new myThread[50];
        for (int i = 0; i < threads.length; i++) {
            for (int j = 0; j < threads[i].length; j++) {
                threads[i][j] = new myThread(n);
            }
        }
        return threads;
    }

    public void initializeLock(myThread[][] t, Lock lock) {
        for (int i = 0; i < t.length; i++) {
            for (int j = 0; j < t[i].length; j++) {
                t[i][j].setLock(lock);
            }
        }
    }

    public double[] timeTASLock(int n) {
        TASLock taslock = new TASLock();
        double[] tasTimes = new double[6];
        myThread[][] tasThreads = createArrays(n);
        initializeLock(tasThreads, taslock);

        for (int i = 0; i < tasThreads.length; i++) {
            long startTime = System.nanoTime();
            for (int j = 0; j < tasThreads[i].length; j++) {
                tasThreads[i][j].start();
            }
            long endTime = System.nanoTime();
            tasTimes[i] = (endTime - startTime) / 1000000.0;
            for (int j = 0; j < tasThreads[i].length; j++) {
                try {
                    tasThreads[i][j].join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            DecimalFormat f = new DecimalFormat("0.0");
            System.out.println("TASLock-" + i + ": " + f.format(tasTimes[i]) + " ms");
        }
        return tasTimes;
    }

    public double[] timeTTASLock(int n) {
        TTASLock ttaslock = new TTASLock();
        double[] ttasTimes = new double[6];
        myThread[][] ttasThreads = createArrays(n);
        initializeLock(ttasThreads, ttaslock);

        for (int i = 0; i < ttasThreads.length; i++) {
            long startTime = System.nanoTime();
            for (int j = 0; j < ttasThreads[i].length; j++) {
                ttasThreads[i][j].start();
            }
            long endTime = System.nanoTime();
            ttasTimes[i] = (endTime - startTime) / 1000000.0;
            for (int j = 0; j < ttasThreads[i].length; j++) {
                try {
                    ttasThreads[i][j].join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            DecimalFormat f = new DecimalFormat("0.0");
            System.out.println("TTASLock-" + i + ": " + f.format(ttasTimes[i]) + " ms");
        }
        return ttasTimes;
    }

    public double[] timeBackOffLock(int n) {
        BackoffLock backofflock = new BackoffLock();
        double[] backoffTimes = new double[6];
        myThread[][] backoffThreads = createArrays(n);
        initializeLock(backoffThreads, backofflock);

        for (int i = 0; i < backoffThreads.length; i++) {
            long startTime = System.nanoTime();
            for (int j = 0; j < backoffThreads[i].length; j++) {
                backoffThreads[i][j].start();
            }
            long endTime = System.nanoTime();
            backoffTimes[i] = (endTime - startTime) / 1000000.0;
            for (int j = 0; j < backoffThreads[i].length; j++) {
                try {
                    backoffThreads[i][j].join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            DecimalFormat f = new DecimalFormat("0.0");
            System.out.println("BackoffLock-" + i + ": " + f.format(backoffTimes[i]) + " ms");
        }
        return backoffTimes;
    }

    public void printTimes(String lock, double[] times) {
        System.out.print(lock + ": [");
        DecimalFormat f = new DecimalFormat("0.0");
        for (int i = 0; i < times.length - 1; i++) {
            System.out.print(f.format(times[i]) + ", ");
        }
        System.out.print(f.format(times[times.length - 1]) + "]");
        System.out.print(" times in ms\n");
    }

    public static void main(String[] args) {
        System.out.print("Please enter how many times each thread should enter its critical section: ");
        int n = Integer.parseInt(System.console().readLine());

        double[] tasTimes = new Main().timeTASLock(n);
        double[] ttasTimes = new Main().timeTTASLock(n);
        double[] backoffTimes = new Main().timeBackOffLock(n);

        System.out.println("\nNumber of threads: [1, 2, 5, 15, 30, 50]");
        System.out.println("---------------------------------------------");
        new Main().printTimes("TASLock", tasTimes);
        new Main().printTimes("TTASLock", ttasTimes);
        new Main().printTimes("BackoffLock", backoffTimes);
        System.out.println("---------------------------------------------");

    }
}
