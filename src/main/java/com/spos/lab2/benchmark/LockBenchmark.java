package com.spos.lab2.benchmark;

import com.spos.lab2.locks.FixnumLock;
import com.spos.lab2.locks.LockType;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Lock;

@Slf4j
public class LockBenchmark {
    private int counter;
    private int maxSteps;
    
    public void run(int threadCount, int maxSteps) {
        for (LockType lockType : LockType.values()) {
            log.info("Testing {} with {} threads, doing {} steps...", lockType.getName(), threadCount, maxSteps);
            
            this.maxSteps = maxSteps;
            counter = 0;

            LockBenchmarkRunnable[] runnables = new LockBenchmarkRunnable[threadCount];
            Thread[] threads = new Thread[threadCount];

            Lock lock;
            try {
                lock = lockType.getLock(threadCount);
            } catch (IllegalArgumentException e) {
                log.warn("Cannot test {} with {} threads.", lockType.getName(), threadCount);
                continue;
            }

            long nanoTimeStart = System.nanoTime();
            for (int i = 0; i < threadCount; i++) {
                runnables[i] = new LockBenchmarkRunnable(this, lock);
                threads[i] = new Thread(runnables[i]);
            }

            for (int i = 0; i < threadCount; i++)
                threads[i].start();

            try {
                for (int i = 0; i < threadCount; i++)
                    threads[i].join();
            } catch (InterruptedException e) {
                throw new RuntimeException("Please don't interrupt the benchmark.", e);
            }

            long nanoTimeEnd = System.nanoTime();
            log.info("Reached {}, time elapsed: {} ns", counter, nanoTimeEnd - nanoTimeStart);

            for (int i = 0; i < threadCount; i++)
                log.info("Thread {} did {} steps.", i, runnables[i].getOwnSteps());
        }
    }
    
    void incrementCounter() {
        counter++;
    }
    
    public int getCounter() {
        return counter;
    }

    public int getMaxSteps() {
        return maxSteps;
    }
}
