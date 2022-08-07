import java.util.concurrent.locks.Lock;

public class Venue {
	// TODO is this supposed to be here?
	Lock l;

	public void dropOff() throws InterruptedException {
		System.out.println("Drop off");
		Thread.currentThread().sleep((long) Math.floor(Math.random() * (1000 + 1 - 200 + 1) + 200));
	}
}
