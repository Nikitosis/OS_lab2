package com.spos.lab2.locks;

import java.util.concurrent.locks.Lock;

public interface FixnumLock extends Lock {
    Long getId();
    Long register();
    Long unregister();
}
