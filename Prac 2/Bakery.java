import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import java.util.HashMap;

// Name: Janco Spies
// Student Number: u21434159

public class Bakery implements Lock {
	int n;
	volatile private HashMap<String, Integer> index;
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
		index = new HashMap<String, Integer>();
		for (int i = 0; i < size; i++) {
			flag[i] = false;
			label[i] = 0;
		}
	}

	@Override
	public void lock() {
		addIndex(Thread.currentThread().getName());
		flag[index.get(Thread.currentThread().getName())] = true;
		label[index.get(Thread.currentThread().getName())] = getMax(label) + 1;
		while (lowerExists(index.get(Thread.currentThread().getName()), Thread.currentThread().getName())) {
		}
		System.out.println("unblocking " + Thread.currentThread().getName() + " with label " + label[index.get(Thread.currentThread().getName())]);
	}

	@Override
	public void unlock() {
		flag[index.get(Thread.currentThread().getName())] = false;
	}

	private void addIndex(String name) {
		if (!index.containsKey(name)) {
			index.put(name, index.size());
		}
	}

	private Integer getMax(Integer[] arr) {
		Integer max = null;
		for (Integer i : arr) {
			if (max == null || i > max) {
				max = i;
			}
		}
		return max;
	}

	private Boolean lowerExists(int curr, String name) {
		for (int t = 0; t < n; t++) {
			if (t < index.get(name) && label[t] < curr && flag[t]) {
				return true;
			}
		}
		return false;
	}

	// private Boolean existsFlag(Boolean[] arr){
	// 	for (Boolean b : arr) {
	// 		if (b) {
	// 			return true;
	// 		}
	// 	}
	// 	return false;
	// }

}
