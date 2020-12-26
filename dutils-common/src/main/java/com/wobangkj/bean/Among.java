package com.wobangkj.bean;

import com.wobangkj.api.Dimension;

/**
 * 一维收缩
 *
 * @author cliod
 * @see Number 属于(二维)数字
 * @since 2020-04-07
 */
public abstract class Among<T extends Number> implements Dimension {

    protected T floor;
    protected T ceiling;

    /**
     * 获取维度
     *
     * @return 维度
     */
    @Override
    public int getDimension() {
        return 2;
    }

    public T getFloor() {
        return floor;
    }

    public T getCeiling() {
        return ceiling;
    }
}
