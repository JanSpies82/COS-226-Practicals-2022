public class RMWRegister {
    private int value;

    public synchronized boolean compareAndSet(int expected, int update) {
        if (value == expected) {
            value = update;
            return true;
        }
        return false;
    }

}
