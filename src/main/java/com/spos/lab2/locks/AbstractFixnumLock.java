package com.spos.lab2.locks;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public abstract class AbstractFixnumLock implements FixnumLock {

    private final Object monitor = new Object();

    private final Integer threadLimit;
    private final HashMap<Long, Integer> registeredThreadIds = new HashMap<>();

    public AbstractFixnumLock(Integer threadLimit) {
        this.threadLimit = threadLimit;
    }

    public Integer getThreadLimit() {
        return threadLimit;
    }

    protected HashMap<Long, Integer> getRegisteredThreadIds() {
        return registeredThreadIds;
    }

    @Override
    public Integer getId() {
        return registeredThreadIds.get(Thread.currentThread().getId());
    }

    @Override
    public Integer register(Long threadId) {
        synchronized (monitor) {
            log.debug("Registering thread with threadId={}", threadId);

            if(registeredThreadIds.containsKey(threadId)) {
                log.debug("Thread is already registered with id={}", registeredThreadIds.get(threadId));
                return registeredThreadIds.get(threadId);
            }

            if(registeredThreadIds.size() >= threadLimit) {
                log.debug("Thread limit is exceeded");
                //TODO: wait???
                return -1;
            }

            int registeredId = registeredThreadIds.size();
            registeredThreadIds.put(threadId, registeredId);

            return registeredId;
        }
    }

    @Override
    public Integer register() {
        return register(Thread.currentThread().getId());
    }

    @Override
    public boolean unregister(Long threadId) {
        synchronized (monitor) {
            log.debug("Unregistering thread with threadId={}", threadId);

            if(!registeredThreadIds.containsKey(threadId)) {
                log.debug("Thread with id={} wasn't registered", threadId);
                return false;
            }

            registeredThreadIds.remove(threadId);
            return true;
        }
    }

    @Override
    public boolean unregister() {
        return unregister(Thread.currentThread().getId());
    }

    public void reset() {
        synchronized (monitor) {
            registeredThreadIds.clear();
        }
    }
}
