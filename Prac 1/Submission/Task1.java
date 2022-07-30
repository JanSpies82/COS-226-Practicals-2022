class Task1 {
    public static void main(String[] args) {
        Scrumboard1 sb = new Scrumboard1();
        myThread1 t1 = new myThread1(sb, "0");
        myThread1 t2 = new myThread1(sb, "1");
        t1.start();
        t2.start();

    }
}

class Scrumboard1 {
    private String[] Todo;
    private String[] Completed;
    private int Tcount;
    private int Ccount;

    Scrumboard1() {
        Todo = new String[10];
        Completed = new String[10];
        int t = 0;
        for (char i = 'A'; i <= 'J'; i++) {
            Todo[t++] = String.valueOf(i);
        }
        Tcount = 0;
        Ccount = 0;
    }

    public String getNextTodo() {
        String out = Todo[Tcount];
        Todo[Tcount++] = null;
        return out;
    }

    public void addCompleted(String s) {
        Completed[Ccount++] = s;
    }

    public void print() {
        System.out.println("Tcount: " + Tcount);
        System.out.println("Ccount: " + Ccount);
        System.out.println("Todo: ");
        for (int i = 0; i < 10; i++) {
            System.out.println(i + ": " + Todo[i]);
        }
        System.out.println("Completed: ");
        for (int i = 0; i < 10; i++) {
            System.out.println(i + ": " + Completed[i]);
        }
    }
}

class myThread1 extends Thread {
    private Scrumboard1 s;
    private String threadName;

    myThread1(Scrumboard1 s, String name) {
        threadName = name;
        this.s = s;
    }

    public void run() {
        for (int i = 0; i < 5; i++) {
            String next = s.getNextTodo();
            System.out.println("Thread-" + threadName + " Task: " + next);
            s.addCompleted(next);
            next = "";
        }
    }
}