import java.util.concurrent.locks.Lock;

public class Venue {
	// TODO is this supposed to be here?
	Lock l;

	public void dropOff() throws InterruptedException {
		long time = (long) Math.floor(Math.random() * (1000 + 1 - 200 + 1) + 200);
		Thread.currentThread().sleep(time);
		System.out.println("Drop off - " + time);
	}
}
