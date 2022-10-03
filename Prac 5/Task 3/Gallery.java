@SuppressWarnings({ "unchecked", "rawtypes", "generics" })
public class Gallery {
    OptemisticList c;

    public Gallery() {
        c = new OptemisticList();
    }

    public void enter(int person, long time) {
        c.add(Thread.currentThread(), person, time);
    }

    public void exit() {
        c.remove(Thread.currentThread());
    }
}
