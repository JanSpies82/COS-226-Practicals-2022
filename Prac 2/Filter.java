import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import java.util.HashMap;

// Name: Janco Spies
// Student Number: u21434159

public class Filter implements Lock {
	private HashMap<Integer, String> victim;
	private HashMap<String, Integer> index;
	private Integer[] level;
	private int n;

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

	public Filter(int size) {
		victim = new HashMap<Integer, String>();
		index = new HashMap<String, Integer>();
		level = new Integer[size+1];
		n = size+1;

		for (int i = 1; i <= size; i++) {
			victim.put(i, "");
			level[i] = 0;
		}
	}

	@Override
	public void lock() {
		addIndex(Thread.currentThread().getName());
		for (int L = 1; L <= n; L++) {
			level[index.get(Thread.currentThread().getName())] = L;
			victim.put(L, Thread.currentThread().getName());
			while (higherExists(L) && victim.get(L) == Thread.currentThread().getName()) {
			}

		}
	}

	@Override
	public void unlock() {
		level[index.get(Thread.currentThread().getName())] = 0;
	}

	private Boolean higherExists(int curr) {
		for (String s : index.keySet()) {
			System.out.println("Index " + s + ": " + index.get(s));
			if (level[index.get(s)] > curr) {
				return true;
			}
		}
		return false;
	}

	private void addIndex(String name) {
		if (!index.containsKey(name)) {
			index.put(name, index.size() + 1);
		}
	}
}
