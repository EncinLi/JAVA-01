package com.core.encin.concurrency;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Encin.Li
 * @create 2021-02-06
 */
public class ReentrantLockCondDemo extends ValueGet implements FiboSum {

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    public ReentrantLockCondDemo() {
        System.out.println("ReentrantLockCondDemo Begin");
        new Thread(() -> setValue(sum())).start();
    }

    @Override
    public int getValue() {
        try {
            lock.lock();
            condition.await();
        } catch (final InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return super.getValue();
    }

    @Override
    public void setValue(final int value) {
        try {
            lock.lock();
            super.setValue(value);
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }
}
