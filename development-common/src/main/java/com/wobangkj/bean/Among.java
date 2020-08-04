package com.wobangkj.bean;

import com.wobangkj.api.Dimension;

/**
 * 一维收缩
 *
 * @author cliod
 * @see java.lang.Number
 * @see java.io.Serializable
 * @since 2020-04-07
 */
public abstract class Among<T extends Number> implements Dimension {

    protected T floor;
    protected T ceiling;

    public T getFloor() {
        return floor;
    }

    public T getCeiling() {
        return ceiling;
    }
}
