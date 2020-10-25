package com.spos.lab2.locks;

public abstract class BakeryLock extends AbstractFixnumLock {

    private int currentCounterValue = 0;

    public BakeryLock(Integer threadLimit) {
        super(threadLimit);
    }

    public void lock() {

    }

    public void unlock() {

    }

    public void bakeryAlgorithmRun() {
        lock();
        register();
        unlock();
    }
}
