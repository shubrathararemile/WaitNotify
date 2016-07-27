import java.util.ArrayList;
import java.util.List;

public class AddRemove implements Runnable {
	Object obj = new Object();
	List<Integer> list = new ArrayList<Integer>();

	public AddRemove(List<Integer> list) {
		this.list = list;
	}

	@Override
	public void run() {

		System.out.println("running thread = " + Thread.currentThread().getName());
		String currentThread = Thread.currentThread().getName();

		if (currentThread.equals("Thread1")) {

			add();

		}

		else if (currentThread.equals("Thread2")) {

			remove();
		}
	}

	public synchronized void add() {
		synchronized (this) {
			list.add(100);
			System.out.println("add() " + list);

			this.notifyAll();
		}
	}

	public synchronized void remove() {
		if (list.isEmpty()) {
			synchronized (this) {
				try {
					System.out.println("waiting...");
					this.wait();
				}

				catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		System.out.println("removed");
		System.out.println(list.remove(0));

	}

	public static void main(String[] args) {
		List<Integer> list = new ArrayList<Integer>();
		Runnable runnable = new AddRemove(list);

		Thread thread1 = new Thread(runnable);
		Thread thread2 = new Thread(runnable);

		thread1.setName("Thread1");
		thread2.setName("Thread2");

		thread1.start();
		thread2.start();
	}

}
