package com.spos.lab2.benchmark;

import com.spos.lab2.locks.FixnumLock;
import com.spos.lab2.locks.LockType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LockBenchmarkRunnable implements Runnable {
    private final LockBenchmark benchmark;
    private final FixnumLock lock;
    private int ownSteps = 0;
    
    public LockBenchmarkRunnable(LockBenchmark benchmark, FixnumLock lock) {
        this.benchmark = benchmark;
        this.lock = lock;
    }
    
    @Override
    public void run() {
        boolean shouldExit = false;
        do {
            lock.lock();

            log.debug("Counter: {}, own step: {}", benchmark.getCounter(), ownSteps);
            if (benchmark.getCounter() >= benchmark.getMaxSteps()) {
                shouldExit = true;
            } else {
                benchmark.incrementCounter();
                ownSteps++;
            }

            lock.unlock();
        } while(!shouldExit);
    }

    public int getOwnSteps() {
        return ownSteps;
    }
}
