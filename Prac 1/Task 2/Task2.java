import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

class Task2 {
    public static void main(String[] args) {
        Scrumboard sb = new Scrumboard();
        myLock l = new myLock();
        myThread2 t1 = new myThread2(sb, "0", l);
        myThread2 t2 = new myThread2(sb, "1", l);
        t1.start();
        t2.start();

    }
}

class Scrumboard {
    private String[] Todo;
    private String[] Completed;
    private int Tcount;
    private int Ccount;

    Scrumboard() {
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

class myThread2 extends Thread {
    private Scrumboard s;
    private String threadName;
    private myLock l;

    myThread2(Scrumboard s, String name, myLock l) {
        threadName = name;
        this.s = s;
        this.l = l;
    }

    public void run() {
        for (int i = 0; i < 5; i++) {
            l.Lock(threadName);
            try {
                String next = s.getNextTodo();
                System.out.println("Thread-" + threadName + " Task: " + next);
                s.addCompleted(next);
                next = "";
            } finally {
                l.Unlock(threadName);
            }
        }
    }
}

class myLock implements Lock {
    private Boolean[] flag;
    private String victim;

    myLock() {
        flag = new Boolean[2];
        flag[0] = false;
        flag[1] = false;
        victim = "";
    }

    public void Lock(String id) {
        flag[Integer.parseInt(id)] = true;
        victim = id;
        while (flag[Integer.parseInt(id) == 1 ? 0 : 1] && victim == id) {
        }
        ;
    }

    public void Unlock(String id) {
        flag[Integer.parseInt(id)] = false;
    }

    @Override
    public void lock() {
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
    }

    @Override
    public Condition newCondition() {
        return null;
    }

}