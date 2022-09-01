public class Starter {
    private volatile boolean[] ready = new boolean[2];

    Starter() {
        ready[0] = true;
        ready[1] = true;
    }

    public void isReady(int i) {
        ready[i] = true;
        // System.out.println(i + " is ready");
    }

    public void isNotReady(int i) {
        ready[i] = false;
        // System.out.println(i + " is not ready");
    }

    public boolean canStart() {
        // if (ready[0] && ready[1])
        //     System.out.println("canStart: " + (ready[0] && ready[1]));
        return ready[0] && ready[1];
    }
}