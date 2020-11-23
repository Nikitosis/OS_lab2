import com.spos.lab2.locks.AbstractFixnumLock;
import com.spos.lab2.locks.DekkersLock;

import java.util.concurrent.Callable;

public class RunnableWithLock implements Runnable {
    private AbstractFixnumLock lock;
    private Integer counter;


    public RunnableWithLock(AbstractFixnumLock lock, Integer counter) {
        System.out.println("Creating new thread...");
        this.counter = counter;
        this.lock = lock;
    }

    @Override
    public void run() {
        lock.register();
        for (int i = 0; i < 1000; i++) {
            System.out.println("Locked thread: " + Thread.currentThread().getName());
            lock.lock();

            counter++;

            lock.unlock();
            System.out.println("Unlocked thread: " + Thread.currentThread().getName());
        }
    }

    public Integer getCounter() {
        return counter;
    }
}
