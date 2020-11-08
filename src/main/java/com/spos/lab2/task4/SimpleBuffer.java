package com.spos.lab2.task4;

import java.util.LinkedList;
import java.util.Queue;

public class SimpleBuffer<T> {
    private final T[] buffer;
    private final int maxSize;
    private int currentSize = 0;
    private int nextPos = 0;
    private int startPos = 0;

    public SimpleBuffer(int size) {
        this.maxSize = size;
        this.buffer = (T[])new Object[size];
    }

    public void put(T element) {
        if (currentSize >= maxSize)
            throw new IndexOutOfBoundsException("Attempt to push element into full buffer!");

        buffer[nextPos] = element;
        //Increase element count
        if (ProducerConsumerMain.SIMULATION_TYPE != SimulationType.I_WANT_LOST_ITEMS) {
            currentSize++;
        } else {
            Thread.yield();
            currentSize++;
            Thread.yield();
        }
        nextPos = (nextPos + 1) % maxSize;
    }
    
    public T get() {
        if (currentSize == 0)
            throw new IndexOutOfBoundsException("Attempt to pop element from empty buffer!");

        T element = buffer[startPos];
        if (ProducerConsumerMain.SIMULATION_TYPE != SimulationType.I_WANT_LOST_ITEMS) {
            currentSize--;
        } else {
            int newSize = currentSize - 1;
            Thread.yield();
            currentSize = newSize;
            Thread.yield();
        }
        startPos = (startPos + 1) % maxSize;

        return element;
    }
    
    public int getCount() {
        return currentSize;
    }
    
    public int getMaxSize() {
        return maxSize;
    }
}
