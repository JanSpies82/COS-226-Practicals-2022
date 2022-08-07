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
		// throw new UnsupportedOperationException();
	}

	public boolean tryLock() {
		// throw new UnsupportedOperationException();
		return false;
	}

	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		// throw new UnsupportedOperationException();
		return false;
	}

	public Condition newCondition() {
		// throw new UnsupportedOperationException();
		return null;
	}

	public Filter(int size) {
		victim = new HashMap<Integer, String>();
		index = new HashMap<String, Integer>();
		level = new Integer[size];
		n = size;

		for (int i = 0; i < size; i++) {
			victim.put(i, "");
			level[i] = 0;
		}
	}

	@Override
	public void lock() {
		addIndex(Thread.currentThread().getName());
		int L = 1;
		for (L = 1; L <= n - 1; L++) {
			level[index.get(Thread.currentThread().getName())] = L;
			victim.put(L, Thread.currentThread().getName());
			// testPrint();
			while (higherExists(L, Thread.currentThread().getName())
					&& victim.get(L) == Thread.currentThread().getName()) {
			}
		}
		// System.out.println("unblocking " + Thread.currentThread().getName() + " at level "
		// 		+ level[index.get(Thread.currentThread().getName())] +
		// 		" where victim is " + victim.get(L) + " and higher exists "
		// 		+ higherExists(L, Thread.currentThread().getName()));

	}

	private void testPrint() {
		System.out.println("Thread: " + Thread.currentThread().getName());
		System.out.println("victims: " + victim.toString());
		System.out.println("index: " + index.toString());
		System.out.println("level: " + level.toString());
	}

	@Override
	public void unlock() {
		level[index.get(Thread.currentThread().getName())] = 0;
	}

	private Boolean higherExists(int curr, String name) {
		// for (String s : index.keySet()) {
		// // System.out.println("Index " + s + ": " + index.get(s));
		// if (s != name && level[index.get(s)] > curr) {
		// return true;
		// }
		// }
		// return false;

		for (int t = 0; t < n; t++) {
			if (t != index.get(name) && level[t] >= curr) {
				return true;
			}
		}
		return false;
	}

	private void addIndex(String name) {
		if (!index.containsKey(name)) {
			index.put(name, index.size());
		}
	}
}
