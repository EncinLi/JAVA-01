package com.core.encin.concurrency;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * @author Encin.Li
 * @create 2021-02-06
 */
public class FutureTask2Demo extends ValueGet implements FiboSum {

    private final ExecutorService executor = Executors.newCachedThreadPool();

    public FutureTask2Demo() throws ExecutionException, InterruptedException {
        System.out.println("FutureTask2Demo Begin");
        try {
            final FutureTask futureTask = new FutureTask(() -> sum());
            executor.submit(futureTask);
            setValue((Integer) futureTask.get());
        } finally {
            executor.shutdown();
        }
    }

}
