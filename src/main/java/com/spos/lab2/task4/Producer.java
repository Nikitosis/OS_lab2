package com.spos.lab2.task4;

public class Producer implements Runnable {
    private Consumer consumer = null;
    private final SimpleBuffer<Integer> buffer;
    
    private int nextItem = 0;

    public Producer(SimpleBuffer<Integer> buffer) {
        this.buffer = buffer;
    }

    public void setConsumer(Consumer consumer) {
        if (this.consumer != null)
            throw new IllegalStateException("Already connected to consumer.");

        this.consumer = consumer;
    }

    @Override
    public void run() {
        if (consumer == null)
            throw new IllegalStateException("Must be connected to consumer.");

        while (true) {
            int newItem = nextItem;
            nextItem = (nextItem + 1) % 1_000_000_000;

            if (buffer.getCount() == buffer.getMaxSize()) {
                try {
                    synchronized (this) {
                        wait();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException("Please don't interrupt the producer thread.", e);
                }
            }

            System.out.println("Inserting item " + newItem);
            buffer.put(newItem);
            
            if (buffer.getCount() == 1) {
                synchronized (consumer) {
                    consumer.notify();
                }
            }
        }
    }
}
