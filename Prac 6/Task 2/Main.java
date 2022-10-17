class Main {
    @SuppressWarnings({"rawtypes", "generics" })
    public static void main(String[] args) {
        myQueue q = new myQueue();
        myThread[] threads = new myThread[(int) Math.floor(Math.random() * (9 - 2 + 2) + 2) ];
        System.out.println("Number of threads: " + threads.length);
        for (int i = 0; i < threads.length; i++)
            threads[i] = new myThread(q);

        for (Thread t : threads)
            t.start();
    }
}