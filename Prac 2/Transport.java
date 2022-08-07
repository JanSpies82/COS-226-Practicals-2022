public class Transport extends Thread {
	Venue destination;
	volatile Filter f;
	volatile Bakery b;

	public Transport(Venue dest, Filter filter, Bakery bakery) {
		destination = dest;
		f = filter;
		b = bakery;
	}

	@Override
	public void run() {
		for (int t = 0; t < 1; t++) {
			System.out.println("BUS (" + Thread.currentThread().getName() + ") is waiting to drop-off: Load " + t);
			f.lock();
			// b.lock();
			try {
				System.out.println("BUS (" + Thread.currentThread().getName() + ") is dropping-off: Load " + t);
				destination.dropOff();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				System.out.println("BUS (" + Thread.currentThread().getName() + ") has left: Load " + t);
				f.unlock();
				// b.unlock();
			}
		}
	}
}
