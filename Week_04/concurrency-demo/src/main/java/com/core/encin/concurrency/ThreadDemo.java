package com.core.encin.concurrency;

/**
 * @author Encin.Li
 * @create 2021-02-06
 */
public class ThreadDemo implements FiboSum {

    private volatile int value = 0;

    public ThreadDemo() {
        System.out.println("ThreadDemo Begin");
        new Thread(() -> setValue(sum())).start();
    }

    public void setValue(final int sum) {
        value = sum;
    }

    public int getResult() {
        while (true) {
            if (value != 0) {
                return value;
            }
        }
    }

}
