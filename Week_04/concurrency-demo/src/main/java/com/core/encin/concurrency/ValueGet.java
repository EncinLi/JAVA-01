package com.core.encin.concurrency;

/**
 * @author Encin.Li
 * @create 2021-02-06
 */
public class ValueGet {
    private int value = 0;

    public int getValue() {
        return value;
    }

    public void setValue(final int value) {
        this.value = value;
    }
}
