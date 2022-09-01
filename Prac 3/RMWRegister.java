public class RMWRegister<T> {
    private T value;
    private static RMWRegister instance = null;

    public static RMWRegister getInstance(Object value) {
        if (instance == null) {
            synchronized (RMWRegister.class) {
                if (instance == null) {
                    instance = new RMWRegister(value);
                    System.out.println("New instance created by " +
                            Thread.currentThread().getName());
                }
            }
        }
        return instance;
    }

    private RMWRegister(Object value) {
        this.value = (T) value;
    }

    public synchronized T getAndMumble() {
        T prior = value;
        value = null;
        return prior;
    }

}
