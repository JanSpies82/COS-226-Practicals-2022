public class Starter {
    private volatile boolean[] ready = new boolean[2];

    Starter() {
        ready[0] = true;
        ready[1] = true;
    }

    public void isReady(int i) {
        ready[i] = true;
    }

    public void isNotReady(int i) {
        ready[i] = false;
    }

    public boolean canStart() {
        return ready[0] && ready[1];
    }
}