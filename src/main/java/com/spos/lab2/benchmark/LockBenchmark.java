package com.spos.lab2.benchmark;

import com.spos.lab2.locks.FixnumLock;
import com.spos.lab2.locks.LockType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LockBenchmark {
    private int counter;
    private int maxSteps;
    
    public void run() {
        for (LockType lockType : LockType.values()) {
            log.info("Testing {}...", lockType.getName());

            maxSteps = 1000;
            counter = 0;
            
            int threadCount = 2;
            LockBenchmarkRunnable[] runnables = new LockBenchmarkRunnable[threadCount];
            Thread[] threads = new Thread[threadCount];

            FixnumLock lock = lockType.getLock(threadCount);

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
            log.info("Time elapsed: {} ns", nanoTimeEnd - nanoTimeStart);

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
