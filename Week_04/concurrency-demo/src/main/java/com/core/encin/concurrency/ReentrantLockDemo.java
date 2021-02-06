package com.core.encin.concurrency;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Encin.Li
 * @create 2021-02-06
 */
public class ReentrantLockDemo implements FiboSum {
    private int value = 0;

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    public ReentrantLockDemo() {
        System.out.println("ReentrantLockDemo Begin");
        new Thread(() -> setValue(sum())).start();
    }

    public int getValue() {
        try {
            lock.lock();
        } finally {
            lock.unlock();
        }
        return value;
    }

    public void setValue(final int value) {
        try {
            lock.lock();
            this.value = value;
        } finally {
            lock.unlock();
        }
    }
}
