package com.core.encin.concurrency;

import java.util.concurrent.locks.LockSupport;

/**
 * @author Encin.Li
 * @create 2021-02-06
 */
public class LockSupportDemo extends ValueGet implements FiboSum {

    public LockSupportDemo() {
        System.out.println("LockSupportDemo Begin");
        final Thread thread = Thread.currentThread();
        new Thread(() -> {
            setValue(sum());
            LockSupport.unpark(thread);
        }).start();
    }

    @Override
    public int getValue() {
        LockSupport.park();
        return super.getValue();
    }

}
