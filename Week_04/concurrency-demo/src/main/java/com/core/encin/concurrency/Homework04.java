package com.core.encin.concurrency;

/**
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
 * 写出你的方法，越多越好，提交到github。
 * 一个简单的代码参考：
 */
public class Homework04 {

    public static void main(final String[] args) throws Exception {

        int result = 0;
        final long start = System.currentTimeMillis();

        //1
        //        final ThreadDemo threadDemo = new ThreadDemo();
        //        result = threadDemo.getResult();

        //2
        //        final ThreadJoinDemo threadJoinDemo = new ThreadJoinDemo();
        //        result = threadJoinDemo.getValue();

        //3
        //        final SynchronizedDemo synchronizedDemo = new SynchronizedDemo();
        //        result = synchronizedDemo.getValue();

        //4
        //        final ThreadSleepDemo threadSleepDemo = new ThreadSleepDemo();
        //        result = threadSleepDemo.getValue();

        //5
        //        final ExecutorServiceDemo executorServiceDemo = new ExecutorServiceDemo();
        //        result = executorServiceDemo.getValue();

        //6
        //还有问题
        //        final ReentrantLockDemo reentrantLockDemo = new ReentrantLockDemo();
        //        result = reentrantLockDemo.getValue();

        //7
        //还有问题
        //        final ReentrantReadWriteLockDemo reentrantRWLockDemo = new ReentrantReadWriteLockDemo();
        //        result = reentrantRWLockDemo.getValue();

        //8
        //        final ReentrantLockCondDemo lockCondDemo = new ReentrantLockCondDemo();
        //        result = lockCondDemo.getValue();

        //9
        //        final LockSupportDemo lockSupportDemo = new LockSupportDemo();
        //        result = lockSupportDemo.getValue();

        //10
        //        final SemaphoreDemo semaphoreDemo = new SemaphoreDemo();
        //        result = semaphoreDemo.getValue();

        //11
        //        final CountDownLatchDemo countDownLatchDemo = new CountDownLatchDemo();
        //        result = countDownLatchDemo.getValue();

        //12
        //        final CyclicBarrierDemo cyclicBarrierDemo = new CyclicBarrierDemo();
        //        result = cyclicBarrierDemo.getValue();

        // 13
        //        final CallableDemo callableDemo = new CallableDemo();
        //        result = callableDemo.getValue();

        // 14
        //        final FutureTask1Demo futureTask1Demo = new FutureTask1Demo();
        //        result = futureTask1Demo.getValue();

        // 15
        //        final FutureTask2Demo futureTask2Demo = new FutureTask2Demo();
        //        result = futureTask2Demo.getValue();

        // 16
        final CompletableFutureDemo completableFutureDemo = new CompletableFutureDemo();
        result = completableFutureDemo.getValue();

        //        final int result = sum(); //这是得到的返回值
        // 确保  拿到result 并输出
        System.out.println("异步计算结果为：" + result);
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");

        // 然后退出main线程
    }

    private static int sum() {
        return fibo(36);
    }

    private static int fibo(final int a) {
        if (a < 2) {
            return 1;
        }
        return fibo(a - 1) + fibo(a - 2);
    }
}
