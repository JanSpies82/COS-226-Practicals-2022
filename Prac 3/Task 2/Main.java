import java.text.DecimalFormat;
import java.util.Arrays;

public class Main {

    public myThread[][] createArrays() {
        myThread[][] threads = new myThread[6][];
        threads[0] = new myThread[1];
        threads[1] = new myThread[2];
        threads[2] = new myThread[5];
        threads[3] = new myThread[15];
        threads[4] = new myThread[30];
        threads[5] = new myThread[50];
        for (int i = 0; i < threads.length; i++) {
            for (int j = 0; j < threads[i].length; j++) {
                threads[i][j] = new myThread();
            }
        }
        return threads;
    }

    public void initializeTAS(myThread[][] t, TASLock lock) {
        for (int i = 0; i < t.length; i++) {
            for (int j = 0; j < t[i].length; j++) {
                t[i][j].setLock(lock);
            }
        }
    }

    public void initializeTTAS(myThread[][] t, TTASLock lock) {
        for (int i = 0; i < t.length; i++) {
            for (int j = 0; j < t[i].length; j++) {
                t[i][j].setLock(lock);
            }
        }
    }

    public double[] timeTASLock() {
        TASLock taslock = new TASLock();
        double[] tasTimes = new double[6];
        myThread[][] tasThreads = createArrays();
        initializeTAS(tasThreads, taslock);

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

    public double[] timeTTASLock() {
        TTASLock ttaslock = new TTASLock();
        double[] ttasTimes = new double[6];
        myThread[][] ttasThreads = createArrays();
        initializeTTAS(ttasThreads, ttaslock);

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
        double[] tasTimes = new Main().timeTASLock();
        double[] ttasTimes = new Main().timeTTASLock();

        System.out.println("Number of threads: [1, 2, 5, 15, 30, 50]");
        System.out.println("---------------------------------------------");
        new Main().printTimes("TASLock", tasTimes);
        new Main().printTimes("TTASLock", ttasTimes);
        System.out.println("---------------------------------------------");

    }
}
