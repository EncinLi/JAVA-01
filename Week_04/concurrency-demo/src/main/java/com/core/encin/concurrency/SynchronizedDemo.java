package com.core.encin.concurrency;

/**
 * @author Encin.Li
 * @create 2021-02-06
 */
public class SynchronizedDemo implements FiboSum {

    private int value = 0;

    public SynchronizedDemo() throws InterruptedException {
        final Thread thread = new Thread(() -> setValue(sum()));
        thread.start();
        //sleep need before get, or use wait() instead of sleep()
        // Thread.sleep(1000);
    }

    public synchronized int getValue() throws InterruptedException {
        //        wait();
        Thread.sleep(1000);
        return value;
    }

    public synchronized void setValue(final int value) {
        this.value = value;
        notifyAll();
    }
}
