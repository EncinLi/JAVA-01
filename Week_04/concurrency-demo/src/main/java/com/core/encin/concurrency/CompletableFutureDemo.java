package com.core.encin.concurrency;

import java.util.concurrent.CompletableFuture;

/**
 * @author Encin.Li
 * @create 2021-02-06
 */
public class CompletableFutureDemo extends ValueGet implements FiboSum {
    public CompletableFutureDemo() {
        System.out.println("CompletableFutureDemo Begin");
        final Integer result = CompletableFuture.supplyAsync(() -> sum()).join();
        setValue(result);
    }
}
