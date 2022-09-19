public class MCSNode {
    boolean locked = false;
    MCSNode next = null;
    MCSNode prev = null;
    String name = "Node " + Integer.parseInt(String.valueOf(Thread.currentThread().getName().charAt(7)));
}
