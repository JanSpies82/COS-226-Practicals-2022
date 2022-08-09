import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import java.util.HashMap;

// Name: Janco Spies
// Student Number: u21434159

public class Bakery implements Lock {
	int n;
	volatile private HashMap<Long, Integer> index;
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
		index = new HashMap<Long, Integer>();
		for (int i = 0; i < size; i++) {
			flag[i] = false;
			label[i] = 0;
		}
	}

	@Override
	public void lock() {
		addIndex(Thread.currentThread().getId());
		Integer max = label[0];
		for (Integer i : label) {
			if (i > max)
				max = i;
		}
		label[index.get(Thread.currentThread().getId())] = max + 1;
		flag[index.get(Thread.currentThread().getId())] = true;
		while (lowerExists(Thread.currentThread().getId())) {
		}
		System.out.println("Unblocking with " + label[index.get(Thread.currentThread().getId())]);
	}

	@Override
	public void unlock() {
		flag[index.get(Thread.currentThread().getId())] = false;
	}

	private void addIndex(Long id) {
		if (!index.containsKey(id)) {
			index.put(id, index.size());
		}
	}

	// private Integer getMax(Integer[] arr) {
	// Integer max = null;
	// for (Integer i : arr) {
	// if (max == null || i > max) {
	// max = i;
	// }
	// }
	// return max;
	// }

	private Boolean lowerExists(Long id) {
		for (int t = 0; t < n; t++) {
			if (flag[t] && (label[t] < label[index.get(id)]
					|| (label[t] == label[index.get(id)] && getId(t) < id))) {
				return true;
			}
		}
		return false;
	}

	private Long getId(int ind) {
		HashMap<Long, Integer> temp = index;
		for (Long l : temp.keySet()) {
			if (temp.get(l) == ind) {
				return l;
			}
		}
		return 0L;
	}

}
