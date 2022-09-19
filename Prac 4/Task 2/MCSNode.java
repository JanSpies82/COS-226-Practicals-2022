public class MCSNode {
    boolean locked = false;
    MCSNode next = null;
    MCSNode prev = null;
    String name;
    String status = "WAITING";

    MCSNode(int i) {
        name = Thread.currentThread().getName() + ":Person " + i;
    }
    MCSNode() {
        name = "AVAILABLE";
    }
}
