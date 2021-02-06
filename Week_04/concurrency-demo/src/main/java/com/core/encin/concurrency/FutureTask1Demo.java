package com.core.encin.concurrency;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author Encin.Li
 * @create 2021-02-06
 */
public class FutureTask1Demo extends ValueGet implements FiboSum {

    public FutureTask1Demo() throws ExecutionException, InterruptedException {
        System.out.println("FutureTask1Demo Begin");
        final FutureTask futureTask = new FutureTask(() -> sum());
        new Thread(futureTask).start();
        setValue((Integer) futureTask.get());
    }

}
