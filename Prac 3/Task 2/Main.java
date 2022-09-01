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

    public static void main(String[] args) {
        TASLock taslock = new TASLock();
        long[] tasTimes = new long[6];

        myThread[][] threads = new Main().createArrays();
        new Main().initializeTAS(threads, taslock);

        for (int i = 0; i < 5; i++) {
            long startTime = System.nanoTime();
            //////////////////////////////////////
            for (int j = 0; j < threads[i].length; j++) {
                threads[i][j].start();
            }
            // switch (i) {
            // case 0: {
            // for (int k = 0; k < 1; k++)
            // threads[k].start();
            // break;
            // }
            // case 1: {
            // for (int k = 0; k < 2; k++)
            // threads[k].start();
            // break;
            // }
            // case 2: {
            // for (int k = 0; k < 5; k++)
            // threads[k].start();
            // break;
            // }
            // case 3: {
            // for (int k = 0; k < 15; k++)
            // threads[k].start();
            // break;
            // }
            // case 4: {
            // for (int k = 0; k < 50; k++)
            // threads[k].start();
            // break;
            // }
            // case 5: {
            // for (int k = 0; k < 100; k++)
            // threads[k].start();
            // break;
            // }
            // default:
            // break;
            // }
            //////////////////////////////////////
            long stopTime = System.nanoTime();
            long elapsedTime = Math.round((stopTime - startTime) / 1000);
            System.out.println(elapsedTime + " us");
            tasTimes[i] = elapsedTime;
        }

        System.out.println("Number of threads: [1, 2, 5, 15, 30, 50]");
        System.out.println("---------------------------------------------");
        System.out.println("TASLock: " + Arrays.toString(tasTimes) + " time in us");
        System.out.println("---------------------------------------------");

    }
}
// Will test times for amounts of threads:
// 1, 2, 5, 15, 50, 100
