import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        System.out.print("Please select either Task1 or Task2: ");
        Scanner input = new Scanner(System.in);
        String task = input.nextLine();
        if (task.equals("1")) {
            Task1();
        } else if (task.equals("2")) {
            Task2();
        } else {
            System.out.println("Invalid input");
        }
        input.close();
    }

    public static void Task1() {
        System.out.println("Hello world1");
    }

    public static void Task2() {
        System.out.println("Hello world2");
    }
}