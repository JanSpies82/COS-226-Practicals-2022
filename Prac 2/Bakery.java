import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import java.util.HashMap;

// Name: Janco Spies
// Student Number: u21434159

public class Bakery implements Lock {
	int n;
	// volatile private HashMap<Long, Integer> index;
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
		// index = new HashMap<Long, Integer>();
		for (int i = 0; i < size; i++) {
			flag[i] = false;
			label[i] = 0;
		}
	}

	@Override
	public void lock() {
		// addIndex(Thread.currentThread().getId());
		Integer id = Integer.parseInt(String.valueOf(Thread.currentThread().getName().charAt(7))) % n;
		Integer max = label[0];
		for (Integer i : label) {
			if (i > max)
				max = i;
		}
		// if (index.get(Thread.currentThread().getId()) == null)
		// 	addIndex(Thread.currentThread().getId());
		label[id] = max
				+ 1;
		flag[id] = true;
		while (lowerExists()) {
		}
	}

	@Override
	public void unlock() {
		// if (index.get(Thread.currentThread().getId()) == null)
		// 	addIndex(Thread.currentThread().getId());
		Integer id = Integer.parseInt(String.valueOf(Thread.currentThread().getName().charAt(7))) % n;
		flag[id] = false;
	}

	// private void addIndex(Long id) {
	// 	if (!index.containsKey(id)) {
	// 		index.put(id, index.size());
	// 	}
	// }

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

	// private Long getId(int ind) {
	// 	HashMap<Long, Integer> temp = index;
	// 	for (Long l : temp.keySet()) {
	// 		if (temp.get(l) == ind) {
	// 			return l;
	// 		}
	// 	}
	// 	return 0L;
	// }

}