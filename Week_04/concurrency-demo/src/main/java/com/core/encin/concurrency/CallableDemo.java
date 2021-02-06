package com.core.encin.concurrency;

import java.util.concurrent.Callable;

/**
 * 不太符合理想的一个线程实现
 *
 * @author Encin.Li
 * @create 2021-02-06
 */
@Deprecated
public class CallableDemo extends ValueGet implements FiboSum {

    public CallableDemo() {
        try {
            System.out.println("CallableDemo Begin");
            setValue((Integer) new Sum().call());
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    class Sum implements Callable {
        @Override
        public Object call() throws Exception {
            return sum();
        }
    }
}
