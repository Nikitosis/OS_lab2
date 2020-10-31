package com.spos.lab2.locks;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

@Slf4j
public class BakeryLock extends AbstractFixnumLock {

    int[] numbers;
    private int maxThreadsId = 50;

    private int currentCounterValue = 0;
    private boolean checkLastNumber = false;


    public BakeryLock(Integer threadLimit) {
        super(threadLimit);
        this.numbers = new int[maxThreadsId];
    }

    public void lock(int threadId) {
        int max = 0;
        for (int i = 0; i < numbers.length; i++) {
            int current = numbers[i];
            if (current > max) {
                max = current;
            }
        }
        numbers[threadId] = 1 + max;
        System.out.println("Number of ticket " + (1 + max) + "  " + threadId);
        for (int i = 0; i < numbers.length; i++) {
            while (numbers[i] != 0 && ( numbers[threadId] > numbers[i])) {}
        }
    }

    public void unlock(int threadId) {
        numbers[threadId] = 0;
    }

    public Long register(Long threadId) {
        if (checkLastNumber) {
            currentCounterValue--;
        }
        else {
            currentCounterValue++;
        }
        System.out.println("Counter - " + currentCounterValue);
        checkLastNumber = !checkLastNumber;

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
        return threadId;
    }

    public void bakeryAlgorithmRun() {
        Long threatId = getId();

        lock(Math.toIntExact(threatId));
        register(threatId);
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
