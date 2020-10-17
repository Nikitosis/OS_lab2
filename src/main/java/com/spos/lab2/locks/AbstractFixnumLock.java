package com.spos.lab2.locks;

import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;

@Slf4j
public abstract class AbstractFixnumLock implements FixnumLock {

    private final Object monitor = new Object();
    private Set<Long> registeredThreadIds = new HashSet<>();

    private Integer threadLimit;

    public AbstractFixnumLock(Integer threadLimit) {
        this.threadLimit = threadLimit;
    }

    @Override
    public Long getId() {
        return Thread.currentThread().getId();
    }

    @Override
    public Long register() {
        synchronized (monitor) {
            Long threadId = Thread.currentThread().getId();
            log.info("Registering thread with threadId={}", threadId);

            if(registeredThreadIds.contains(threadId)) {
                log.info("Thread is already registered with id={}", threadId);
                return threadId;
            }

            if(registeredThreadIds.size() >= threadLimit) {
                log.info("Thread limit is exceeded");
                //TODO: wait???
                return -1L;
            }

            registeredThreadIds.add(threadId);

            return threadId;
        }
    }

    @Override
    public Long unregister() {
        synchronized (monitor) {
            Long threadId = Thread.currentThread().getId();
            log.info("Unregistering thread with threadId={}", threadId);

            if(!registeredThreadIds.contains(threadId)) {
                log.info("Thread with id={} wasn't registered", threadId);
                return -1L;
            }

            registeredThreadIds.remove(threadId);
            return threadId;
        }
    }

    public void reset() {
        synchronized (monitor) {
            registeredThreadIds.clear();
        }
    }
}
