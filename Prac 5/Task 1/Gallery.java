public class Gallery {
    CoarseList c;
    
    public Gallery() {
        c = new CoarseList();
    }

    public void enter() {
        c.add(Thread.currentThread());
    }

    public void exit() {
        c.remove(Thread.currentThread());
    }
}
