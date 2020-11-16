package com.spos.lab2.task4;

public class ProducerConsumerMain {
    public static final SimulationType SIMULATION_TYPE = SimulationType.I_WANT_LOST_ITEMS;
    
    public static void main(String[] args) {
        SimpleBuffer<Integer> simpleItemBuffer = new SimpleBuffer<>(10);

        Producer producer = new Producer(simpleItemBuffer);
        Consumer consumer = new Consumer(simpleItemBuffer);

        producer.setConsumer(consumer);
        consumer.setProducer(producer);

        Thread producerThread = new Thread(producer);
        Thread consumerThread = new Thread(consumer);

        producerThread.start();
        consumerThread.start();
    }
}
