package com.spos.lab2.locks;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

@Slf4j
public class BakeryLock extends AbstractFixnumLock {

    int[] numbers;

    private int currentCounterValue = 0;
    private boolean checkLastNumber = false;


    public BakeryLock(Integer threadLimit) {
        super(threadLimit);
        this.numbers = new int[threadLimit];
    }

    private void lock(int threadId) {
        int max = 0;
        for (int i = 0; i < numbers.length; i++) {
            int current = numbers[i];
            if (current > max) {
                max = current;
            }
        }
        numbers[threadId] = 1 + max;
        //System.out.println("Number of ticket " + (1 + max) + "  " + threadId);
        for (int i = 0; i < numbers.length; i++) {
            while (numbers[i] != 0 && ( numbers[threadId] > numbers[i])) {}
        }
    }

    private void unlock(int threadId) {
        numbers[threadId] = 0;
    }

    @Override
    public Integer register() {
        if (checkLastNumber) {
            currentCounterValue--;
        }
        else {
            currentCounterValue++;
        }
        System.out.println("Counter - " + currentCounterValue);
        checkLastNumber = !checkLastNumber;

        return super.register();
    }

    @Override
    public void lock() {
        lock(getId());
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
        unlock(getId());
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
