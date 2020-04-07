package com.wobangkj.bean;

import com.wobangkj.api.TwoDimension;

/**
 * 一维收缩
 *
 * @author cliod
 * @see java.lang.Number
 * @see java.io.Serializable
 * @since 2020-04-07
 */
public abstract class Among<T extends Number> extends Number implements TwoDimension<T, T> {

    protected T floor;
    protected T ceiling;

    @Override
    public T getX() {
        return floor;
    }

    @Override
    public T getY() {
        return ceiling;
    }

    @Override
    public int intValue() {
        return ceiling.intValue() ^ floor.intValue();
    }

    @Override
    public long longValue() {
        return ceiling.longValue() ^ floor.longValue();
    }

    @Override
    public float floatValue() {
        return ceiling.longValue() ^ floor.longValue();
    }

    @Override
    public double doubleValue() {
        return ceiling.longValue() ^ floor.longValue();
    }
}
