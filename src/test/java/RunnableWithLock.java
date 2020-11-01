import com.spos.lab2.locks.AbstractFixnumLock;
import com.spos.lab2.locks.DekkersLock;

import java.util.concurrent.Callable;

public class RunnableWithLock implements Callable<Integer> {
    private AbstractFixnumLock lock;


    public RunnableWithLock(AbstractFixnumLock lock) {
        System.out.println("Creating new thread...");
        this.lock = lock;
    }

    @Override
    public Integer call() throws Exception {
        Integer counter = 0;
        for (int i = 0; i < 1000; i++) {
            System.out.println("Locked thread: " + Thread.currentThread().getName());
            lock.lock();

            counter++;

            lock.unlock();
            System.out.println("Unlocked thread: " + Thread.currentThread().getName());
        }

        return counter;
    }
}
