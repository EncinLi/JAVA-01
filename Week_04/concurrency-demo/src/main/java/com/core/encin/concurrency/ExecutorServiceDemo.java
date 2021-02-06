package com.core.encin.concurrency;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author Encin.Li
 * @create 2021-02-06
 */
public class ExecutorServiceDemo extends ValueGet implements FiboSum {
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    //1 same as single thread
    ExecutorService fixedThreadPool = Executors.newFixedThreadPool(1);
    ExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(1);

    public ExecutorServiceDemo() {
        System.out.println("ExecutorServiceDemo Begin");
        final Future<Integer> future = executorService.submit(() -> sum());
        try {
            setValue(future.get());
        } catch (final InterruptedException e) {
            e.printStackTrace();
        } catch (final ExecutionException e) {
            e.printStackTrace();
        } finally {
            if (future.isDone()) {
                executorService.shutdown();
                // cachedThreadPool.shutdown();
                // fixedThreadPool.shutdown();
                // scheduledThreadPool.shutdown();
            }
        }
    }

}
