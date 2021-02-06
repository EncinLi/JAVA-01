package com.core.encin.concurrency;

import java.util.concurrent.Semaphore;

/**
 * @author Encin.Li
 * @create 2021-02-06
 */
public class SemaphoreDemo extends ValueGet implements FiboSum {
    private final Semaphore semaphore = new Semaphore(1);

    public SemaphoreDemo() {
        //semaphore 首先抢占锁资源，这样getValue没有获得锁，只有计算结果后才release
        System.out.println("SemaphoreDemo Begin");
        semaphore.acquireUninterruptibly();
        new Thread(() -> {
            setValue(sum());
            semaphore.release();
        }).start();
    }

    @Override
    public int getValue() {
        try {
            semaphore.acquireUninterruptibly();
            return super.getValue();
        } finally {
            semaphore.release();
        }
    }

}
