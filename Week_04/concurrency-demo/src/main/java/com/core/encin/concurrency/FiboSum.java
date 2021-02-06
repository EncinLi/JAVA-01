package com.core.encin.concurrency;

/**
 * @author Encin.Li
 * @create 2021-02-06
 */
public interface FiboSum {
    default int sum() {
        return fibo(36);
    }

    static int fibo(final int a) {
        if (a < 2) {
            return 1;
        }
        return fibo(a - 1) + fibo(a - 2);
    }
}
