public class Main {
    public static void main(String[] args) {
        for (int h = 0; h < 3; h++) {
            long startTime = System.currentTimeMillis();
            //////////////////////////////////////
            long stopTime = System.currentTimeMillis();
            long elapsedTime = stopTime - startTime;
            System.out.println(elapsedTime + " ms");
        }

    }
}
