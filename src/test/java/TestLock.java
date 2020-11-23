import com.spos.lab2.locks.DekkersLock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TestLock {
    @Test
    void DekkersLockTest() throws InterruptedException, ExecutionException {
        Integer counter = 0;
        DekkersLock dekkersLock = new DekkersLock();
        RunnableWithLock runnableWithLock = new RunnableWithLock(dekkersLock, counter);
        RunnableWithLockMinus runnableWithLockMinus = new RunnableWithLockMinus(dekkersLock, counter);
        Thread t1 = new Thread(runnableWithLock);
        Thread t2 = new Thread(runnableWithLockMinus);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Counter: " + counter);
        Assertions.assertEquals(0, counter);
    }
}
