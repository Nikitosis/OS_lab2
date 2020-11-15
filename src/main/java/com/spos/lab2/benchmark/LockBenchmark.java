package com.spos.lab2.benchmark;

import com.spos.lab2.locks.FixnumLock;
import com.spos.lab2.locks.LockType;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;

@Slf4j
public class LockBenchmark {
    private int counter;
    private final int maxSteps;
    private final int threadCount;
    private final int runs;
    private final Map<LockType, long[]> benchmarkTimes;
    private final boolean printDetail;
    private boolean launched = false;

    public LockBenchmark(int threadCount, int maxSteps, int runs) {
        this(threadCount, maxSteps, runs, false);
    }
    
    public LockBenchmark(int threadCount, int maxSteps, boolean printDetail) {
        this(threadCount, maxSteps, 1, printDetail);
    }
    
    public LockBenchmark(int threadCount, int maxSteps, int runs, boolean printDetail) {
        this.maxSteps = maxSteps;
        this.runs = runs;
        this.threadCount = threadCount;
        this.printDetail = printDetail;
        benchmarkTimes = new HashMap<>();
        for (LockType lockType : LockType.values())
            benchmarkTimes.put(lockType, new long[runs]);
    }
    
    public void run() {
        if (launched)
            throw new IllegalStateException("Benchmark has already been launched.");
        
        launched = true;
        
        for (int runNumber = 0; runNumber < runs; runNumber++)
            runSteps(runNumber);

        log.info("========================================");
        log.info("Results for test: {} steps, {} threads", maxSteps, threadCount);
        log.info("========================================");
        for (LockType lockType : LockType.values()) {
            long nanosAvg = 0, nanosMax = 0;

            long[] nanosTimes = benchmarkTimes.get(lockType);
            for (long nanoTime : nanosTimes) {
                nanosAvg += nanoTime;
                if (nanoTime > nanosMax)
                    nanosMax = nanoTime;
            }
            nanosAvg /= nanosTimes.length;
            log.info("avg. {} ms, max. {} ms - {}", nanosAvg / 1_000_000, nanosMax / 1_000_000, lockType.getName());
        }
    }
    
    private void runSteps(int runNumber) {
        for (LockType lockType : LockType.values()) {
            if (printDetail)
                log.info("Testing {} with {} threads, doing {} steps...", lockType.getName(), threadCount, maxSteps);

            counter = 0;

            LockBenchmarkRunnable[] runnables = new LockBenchmarkRunnable[threadCount];
            Thread[] threads = new Thread[threadCount];
            
            Lock lock;
            try {
                lock = lockType.getLock(threadCount);
            } catch (IllegalArgumentException e) {
                if (printDetail)
                    log.warn("Cannot test {} with {} threads.", lockType.getName(), threadCount);
                continue;
            }

            long nanoTimeStart = System.nanoTime();
            for (int i = 0; i < threadCount; i++) {
                runnables[i] = new LockBenchmarkRunnable(this, lock);
                threads[i] = new Thread(runnables[i]);
                if (lock instanceof FixnumLock) {
                    int id = ((FixnumLock) lock).register(threads[i].getId());
                    log.debug("Registered thread {} as {}", threads[i].getId(), id);
                }
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
            long nanoTime = nanoTimeEnd - nanoTimeStart;
            if (printDetail)
                log.info("Reached {}, time elapsed: {} ms", counter, nanoTime / 1_000_000);

            if (printDetail) {
                for (int i = 0; i < threadCount; i++)
                    log.info("Thread {} did {} steps.", i, runnables[i].getOwnSteps());
            }

            benchmarkTimes.get(lockType)[runNumber] = nanoTime;
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
