package com.spos.lab2.locks;

import java.util.concurrent.locks.Lock;

public enum LockType {
    BAKERY("Bakery algorithm"),
    IMPROVED_BAKERY("Improved bakery algorithm"),
    DEKKERS("Dekker's algorithm"),
    SPIN_LOCK("SpinLock");

    private final String name;

    LockType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Lock getLock(int threadLimit) {
        switch (this) {
            case BAKERY:
                return new BakeryLock(threadLimit);
            case DEKKERS:
                if (threadLimit != 2)
                    throw new IllegalArgumentException("Dekker's algorithm only works with 2 threads!");
                return new DekkersLock();
            case IMPROVED_BAKERY:
                return new ImprovedBakeryLock(threadLimit);
            case SPIN_LOCK:
                return new SpinLock();
            default:
                throw new IllegalStateException("Illegal LockType");
        }
    }
}
