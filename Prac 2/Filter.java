import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import java.util.HashMap;

// Name: Janco Spies
// Student Number: u21434159

public class Filter implements Lock {
	private HashMap<Integer, String> victim;
	private Integer[] level;
	private int n;

	public void lockInterruptibly() throws InterruptedException {
		throw new UnsupportedOperationException();
	}

	public boolean tryLock() {
		throw new UnsupportedOperationException();
		// return false;
	}

	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		throw new UnsupportedOperationException();
		// return false;
	}

	public Condition newCondition() {
		throw new UnsupportedOperationException();
		// return null;
	}

	public Filter(int size) {
		victim = new HashMap<Integer, String>();
		level = new Integer[size];
		n = size;

		for (int i = 0; i < size; i++) {
			victim.put(i, "");
			level[i] = 0;
		}
	}

	@Override
	public void lock() {
		Integer id = Integer.parseInt(String.valueOf(Thread.currentThread().getName().charAt(7))) % n;
		int L = 1;
		for (L = 1; L <= n - 1; L++) {
			level[id] = L;
			victim.put(L, Thread.currentThread().getName());
			while (higherExists(L)
					&& victim.get(L) == Thread.currentThread().getName()) {
			}
		}

	}

	@Override
	public void unlock() {
		Integer id = Integer.parseInt(String.valueOf(Thread.currentThread().getName().charAt(7))) % n;
		level[id] = 0;
	}

	private Boolean higherExists(int curr) {
		Integer id = Integer.parseInt(String.valueOf(Thread.currentThread().getName().charAt(7))) % n;
		for (int t = 0; t < n; t++) {
			if (t != id && level[t] >= curr) {
				return true;
			}
		}
		return false;
	}
}
