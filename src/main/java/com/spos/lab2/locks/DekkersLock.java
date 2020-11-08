package com.spos.lab2.locks;

import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Condition;

public class DekkersLock extends AbstractFixnumLock {

    private Set<Long> wantsToEnter = ConcurrentHashMap.newKeySet();
    private AtomicBoolean turn = new AtomicBoolean(false);
    private AtomicInteger counter = new AtomicInteger(0);

    public DekkersLock() {
        super(2);
    }

    @Override
    public void lock() {
        int curCounter = counter.addAndGet(1);
        //System.out.println("Cur counter: " + curCounter + " turn " + turn);

        wantsToEnter.add(getId());

        while(wantsToEnter.size() > 1) {
            if(curCounter % 2 == 0 && turn.get() == false) {
                break;
            }
            if(curCounter % 2 == 1 && turn.get() == true) {
                break;
            }
            boolean turnPrev = turn.get();
            while(turn.get() == turnPrev) {
                //busy wait
            }
        }
    }

    @Override
    public void unlock() {
        turn.set(!turn.get());
        wantsToEnter.remove(getId());
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
