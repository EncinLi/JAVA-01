package com.core.encin.concurrency;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author Encin.Li
 * @create 2021-02-06
 */
public class CyclicBarrierDemo extends ValueGet implements FiboSum {
    private final CyclicBarrier barrier = new CyclicBarrier(1, () -> setValue(sum()));
    // 在没添加runnable的时候如何实现呢？
    private final CyclicBarrier barrier1 = new CyclicBarrier(1);

    public CyclicBarrierDemo() throws BrokenBarrierException, InterruptedException {
        System.out.println("CyclicBarrierDemo Begin");
        barrier.await();
    }

}
