import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

// Name: Janco Spies
// Student Number: u21434159

public class Bakery implements Lock {
	int n;
	volatile private Boolean[] flag;
	volatile private Integer[] label;

	public void lockInterruptibly() throws InterruptedException {
		throw new UnsupportedOperationException();
	}

	public boolean tryLock() {
		throw new UnsupportedOperationException();
	}

	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		throw new UnsupportedOperationException();
	}

	public Condition newCondition() {
		throw new UnsupportedOperationException();
	}

	public Bakery(int size) {
		n = size;
		flag = new Boolean[size];
		label = new Integer[size];
		for (int i = 0; i < size; i++) {
			flag[i] = false;
			label[i] = 0;
		}
	}

	@Override
	public void lock() {
		Integer id = Integer.parseInt(String.valueOf(Thread.currentThread().getName().charAt(7))) % n;
		Integer max = label[0];
		for (Integer i : label) {
			if (i > max)
				max = i;
		}
		label[id] = max
				+ 1;
		flag[id] = true;
		while (lowerExists()) {
		}
	}

	@Override
	public void unlock() {
		Integer id = Integer.parseInt(String.valueOf(Thread.currentThread().getName().charAt(7))) % n;
		flag[id] = false;
	}

	private Boolean lowerExists() {
		Integer id = Integer.parseInt(String.valueOf(Thread.currentThread().getName().charAt(7))) % n;
		for (int t = 0; t < n; t++) {
			if (flag[t] && (label[t] < label[id]
					|| (label[t] == label[id] && t < id))) {
				return true;
			}
		}
		return false;
	}

}