package com.spos.lab2.locks;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MonitorLock implements Lock {
    private int currentThreadCount = 0;
    private final Object lock = new Object();
    
    public MonitorLock() {
    }

    @Override
    public void lock() {
        synchronized (lock) {
            currentThreadCount++;
            if (currentThreadCount > 1) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException("Interrupting is not supported by MonitorLock", e);
                }
            }
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        synchronized (lock) {
            currentThreadCount++;
            if (currentThreadCount > 1) {
                lock.wait();
            }
        }
    }

    @Override
    public boolean tryLock() {
        synchronized (lock) {
            if (currentThreadCount == 0) {
                currentThreadCount++;
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public boolean tryLock(long l, TimeUnit timeUnit) throws InterruptedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void unlock() {
        synchronized (lock) {
            currentThreadCount--;
            lock.notify();
        }
    }

    @Override
    public Condition newCondition() {
        throw new UnsupportedOperationException();
    }
}
