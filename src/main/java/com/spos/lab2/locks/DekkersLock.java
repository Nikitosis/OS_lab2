package com.spos.lab2.locks;

import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Condition;

public class DekkersLock extends AbstractFixnumLock {

    private Set<Long> wantsToEnter = new HashSet<>();
    private Boolean turn = false;

    public DekkersLock() {
        super(2);
    }

    @SneakyThrows
    @Override
    public void lock() {
        wantsToEnter.add(getId());

        while(wantsToEnter.size() > 1) {
            boolean turnPrev = turn;
            while(turn == turnPrev) {
                //busy wait
                Thread.sleep(10);
            }
        }
    }

    @Override
    public void unlock() {
        turn = !turn;
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
