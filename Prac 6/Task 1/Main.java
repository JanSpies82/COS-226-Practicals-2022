class Main {
    public static void main(String[] args) {
        myQueue q = new myQueue();
        myThread[] threads = new myThread[/* (int) Math.floor(Math.random() * (7 - 1 + 2) + 1) */7];
        for (int i = 0; i < threads.length; i++)
            threads[i] = new myThread(q);

        for (Thread t : threads)
            t.start();
    }
}