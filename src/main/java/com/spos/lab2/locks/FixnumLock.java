package com.spos.lab2.locks;

import java.util.concurrent.locks.Lock;

public interface FixnumLock extends Lock {
    Integer getId();
    Integer register();
    Integer register(Long threadId);
    boolean unregister();
    boolean unregister(Long threadId);
}
