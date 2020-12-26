package com.wobangkj.api;

import com.wobangkj.bean.Maps;

/**
 * 点 顶级抽象
 *
 * @author @cliod
 * @since 4/8/20 9:17 AM
 */
public interface Coordinate<X, Y, Z> extends Dimension {

	/**
	 * 获取x的值
	 *
	 * @return x
	 */
	X getX();

	/**
	 * 获取y的值
	 *
	 * @return y
	 */
	Y getY();

	/**
	 * 获取z的值
	 *
	 * @return z
	 */
	Z getZ();

	/**
	 * 获取维度
	 *
	 * @return 维度
	 */
	@Override
	default int getDimension() {
		return 2;
	}

	/**
	 * 获取座标点
	 *
	 * @return 座标点
	 */
	default String getPoint() {
		return String.format("(%s,%s,%s)", getX(), getY(), getZ());
	}

	/**
	 * 转成对象
	 *
	 * @return obj
	 */
	@Override
	default Object toObject() {
		return Maps.of("x", (Object) getX()).add("y", getY()).add("z", getZ());
	}
}
