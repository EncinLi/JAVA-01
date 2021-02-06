package com.core.encin.concurrency;

/**
 * @author Encin.Li
 * @create 2021-02-06
 */
public class ThreadSleepDemo extends ValueGet implements FiboSum {

    public ThreadSleepDemo() throws InterruptedException {
        System.out.println("ThreadSleepDemo Begin");
        new Thread(() -> setValue(sum())).start();
        //test find sleep not need synchronzied
        Thread.sleep(1000);
    }

}
