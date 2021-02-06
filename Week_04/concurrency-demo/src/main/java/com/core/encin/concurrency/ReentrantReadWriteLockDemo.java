package com.core.encin.concurrency;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Encin.Li
 * @create 2021-02-06
 */
public class ReentrantReadWriteLockDemo implements FiboSum {
    private int value = 0;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public ReentrantReadWriteLockDemo() {
        System.out.println("ReentrantReadWriteLockDemo Begin");
        new Thread(() -> setValue(sum())).start();
    }

    public int getValue() {
        try {
            lock.readLock().lock();
            return value;
        } finally {
            lock.readLock().unlock();
        }
    }

    public void setValue(final int value) {
        try {
            lock.writeLock().lock();
            this.value = value;
        } finally {
            lock.writeLock().unlock();
        }
    }
}
