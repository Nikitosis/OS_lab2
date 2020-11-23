package com.spos.lab2.locks;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class SpinLock implements Lock {
    private AtomicBoolean locked = new AtomicBoolean(false);
    
    public SpinLock() {

    }

    @Override
    public void lock() {
        while (!locked.compareAndSet(false, true)) {
            //busy wait
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean tryLock() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean tryLock(long l, TimeUnit timeUnit) throws InterruptedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void unlock() {
        locked.set(false);
    }

    @Override
    public Condition newCondition() {
        throw new UnsupportedOperationException();
    }
}
