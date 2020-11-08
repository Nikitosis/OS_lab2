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
            while (buffer.getCount() == 0) {
                //Thread.yield(); //uncomment this to cause deadlock
                try {
                    synchronized (this) {
                        System.out.println("Consumer: start sleeping");
                        wait();
                        System.out.println("Consumer: wake up");
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException("Please don't interrupt the consumer thread.", e);
                }
            }
            int item = buffer.get();
            System.out.println("Consumed item " + item);
            
            if (buffer.getCount() == buffer.getMaxSize() - 1) {
                synchronized (producer) {
                    System.out.println("Consumer: notify producer");
                    producer.notify();
                }
            }
        }
    }
}
