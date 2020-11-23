package com.spos.lab2.locks;

import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.Condition;

public class DekkersLock extends AbstractFixnumLock {
    private final AtomicLong firstThreadId = new AtomicLong(-1);
    private final Set<Long> wantsToEnter = ConcurrentHashMap.newKeySet();
    private final AtomicBoolean turn = new AtomicBoolean(false);
    //private final AtomicInteger counter = new AtomicInteger(0);

    public DekkersLock() {
        super(2);
    }

    @Override
    public void lock() {
        long thisId = getId();

        firstThreadId.compareAndSet(-1, thisId);

        wantsToEnter.add(thisId);

        while(wantsToEnter.size() > 1) {
            boolean requiredTurn = firstThreadId.get() == thisId;

            if (turn.get() != requiredTurn) {
                wantsToEnter.remove(thisId);

                while (turn.get() != requiredTurn) {
                    //busy wait
                }
                wantsToEnter.add(thisId);
            }
        }
    }

    @Override
    public void unlock() {
        turn.set(!turn.get());
        long thisId = getId();
        //System.out.println(thisId + " is unlocked.");
        wantsToEnter.remove(thisId);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        throw new UnsupportedOperationException("Unsupported");
    }

    @Override
    public boolean tryLock() {
        throw new UnsupportedOperationException("Unsupported");

    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        throw new UnsupportedOperationException("Unsupported");

    }

    @Override
    public Condition newCondition() {
        throw new UnsupportedOperationException("Unsupported");
    }
}
