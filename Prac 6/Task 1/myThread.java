public class myThread extends Thread {
    public volatile myQueue q;

    public myThread(myQueue q) {
        this.q = q;
    }

    public void run() {
        for (int i=1; i<=5; i++){
            
        }
    }
}
