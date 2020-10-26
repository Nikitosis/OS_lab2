import com.spos.lab2.locks.DekkersLock;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TestLock {
    @Test
    void DekkersLockTest() throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        DekkersLock dekkersLock = new DekkersLock();
        Future<Integer> f1 = executorService.submit(new RunnableWithLock(dekkersLock));
        Future<Integer> f2 = executorService.submit(new RunnableWithLock(dekkersLock));

        System.out.println("F1: "+f1.get());
        System.out.println("F2: "+f2.get());
    }
}
