package com.spos.lab2.task4;

public class Consumer implements Runnable {
    private Producer producer = null;
    private final SimpleBuffer<Integer> buffer;

    public Consumer(SimpleBuffer<Integer> buffer) {
        this.buffer = buffer;
    }
    
    public void setProducer(Producer producer) {
        if (this.producer != null)
            throw new IllegalStateException("Already connected to producer.");

        this.producer = producer;
    }

    @Override
    public void run() {
        if (producer == null)
            throw new IllegalStateException("Must be connected to producer.");

        while (true) {
            //wait
            if (buffer.getCount() == 0) {
                try {
                    synchronized (this) {
                        wait();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException("Please don't interrupt the consumer thread.", e);
                }
            }
            int item = buffer.get();
            System.out.println("Consumed item " + item);
            
            if (buffer.getCount() == buffer.getMaxSize() - 1) {
                synchronized (producer) {
                    producer.notify();
                }
            }
        }
    }
}
