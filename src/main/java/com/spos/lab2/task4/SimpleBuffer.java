package com.spos.lab2.task4;

import java.util.LinkedList;
import java.util.Queue;

public class SimpleBuffer<T> {
    private final LinkedList<T> queue = new LinkedList<>();
    private final int maxSize;
    private int currentSize = 0;

    public SimpleBuffer(int size) {
        this.maxSize = size;
    }

    public void put(T element) {
        if (currentSize >= maxSize)
            throw new IndexOutOfBoundsException("Attempt to push element into full buffer!");

        queue.addLast(element);
        currentSize++;
    }
    
    public T get() {
        if (currentSize == 0)
            throw new IndexOutOfBoundsException("Attempt to pop element from empty buffer!");

        currentSize--;
        return queue.removeFirst();
    }
    
    public int getCount() {
        return currentSize;
    }
    
    public int getMaxSize() {
        return maxSize;
    }
}
