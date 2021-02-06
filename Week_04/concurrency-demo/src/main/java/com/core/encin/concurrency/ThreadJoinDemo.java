package com.core.encin.concurrency;

/**
 * @author Encin.Li
 * @create 2021-02-06
 */
public class ThreadJoinDemo implements FiboSum {
    private volatile int value = 0;

    public ThreadJoinDemo() throws InterruptedException {
        System.out.println("ThreadJoinDemo Begin");
        final Thread thread = new Thread(() -> setValue(sum()));
        thread.start();
        thread.join();
    }

    public int getValue() {
        return value;
    }

    public void setValue(final int value) {
        this.value = value;
    }

}
