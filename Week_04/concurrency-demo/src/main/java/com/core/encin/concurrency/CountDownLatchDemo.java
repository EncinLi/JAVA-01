package com.core.encin.concurrency;

import java.util.concurrent.CountDownLatch;

/**
 * @author Encin.Li
 * @create 2021-02-06
 */
public class CountDownLatchDemo implements FiboSum {
    private int value = 0;
    private final CountDownLatch latch = new CountDownLatch(1);

    public CountDownLatchDemo() {
        System.out.println("CountDownLatchDemo Begin");
        new Thread(() -> setValue(sum())).start();
    }

    public int getValue() throws InterruptedException {
        latch.await();
        return value;
    }

    public void setValue(final int value) {
        this.value = value;
        latch.countDown();
    }
}
