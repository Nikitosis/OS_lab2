package com.spos.lab2.locks;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.locks.Condition;

@Slf4j
public class ImprovedBakeryLock extends AbstractFixnumLock{

    AtomicIntegerArray ticket; // ticket for threads in line, n - number of threads
    AtomicIntegerArray entering; // 1 when thread entering in line

    private int maxThreadsId = 50;

    public ImprovedBakeryLock(Integer threadLimit) {
        super(threadLimit);
        this.ticket = new AtomicIntegerArray(maxThreadsId);
        this.entering = new AtomicIntegerArray(maxThreadsId);
    }

    public void lock(int threadId) {
        entering.set(threadId, 1);
        int max = 0;
        for (int i = 0; i < 50; i++) {
            int current = ticket.get(i);
            if (current > max) {
                max = current;
            }
        }
        ticket.set(threadId, 1 + max);
        System.out.println("Number of ticket " + (1 + max) + "  " + threadId);
        entering.set(threadId, 0);
        for (int i = 0; i < ticket.length(); ++i) {
            if (i != threadId) {
                while (entering.get(i) == 1) {
                    Thread.yield();
                }
                while (ticket.get(i) != 0 && ( ticket.get(threadId) > ticket.get(i)  ||
                        (ticket.get(threadId) == ticket.get(i) && threadId > i))) {
                    Thread.yield();
                }
            }
        }
    }

    public void unlock(int threadId) {
        ticket.set(threadId, 0);
    }

    @Override
    public Long register() {
        System.out.println("Start");
            Long threadId = Thread.currentThread().getId();
            log.info("Registering thread with threadId={}", threadId);

            if(getRegisteredThreadIds().contains(threadId)) {
                log.info("Thread is already registered with id={}", threadId);
                return threadId;
            }

            if(getRegisteredThreadIds().size() >= getThreadLimit()) {
                log.info("Thread limit is exceeded");
                //TODO: wait???
                return -1L;
            }

            getRegisteredThreadIds().add(threadId);
        try {
            Thread.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Finish");
        return threadId;
    }

    public void bakeryAlgorithmRun() {
        Long threatId = getId();
        lock(Math.toIntExact(threatId));
        register();
        unlock(Math.toIntExact(threatId));
    }

    @Override
    public void lock() {

    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {

    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
