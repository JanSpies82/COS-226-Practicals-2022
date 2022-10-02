@SuppressWarnings({ "unchecked", "rawtypes", "generics" })
public class Gallery {
    CoarseList c;

    public Gallery() {
        c = new CoarseList();
    }

    public void enter(int person, long time) {
        c.add(Thread.currentThread(), person, time);
    }

    public void exit() {
        c.remove(Thread.currentThread());
    }
}
