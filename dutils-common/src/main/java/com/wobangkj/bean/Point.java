package com.wobangkj.bean;

import com.wobangkj.api.Coordinate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.math.BigDecimal;

/**
 * 座标点
 *
 * @author cliod
 * @since 6/23/20 10:50 AM
 */
public abstract class Point<X extends Number, Y extends Number> implements Coordinate<X, Y, Void> {

	private static final double EARTH_RADIUS = 637_1393.00D;
	private static final double RADIAN = Math.PI / 180.00D;
	private static final double HALF = 0.5D;

	public static <X extends Number, Y extends Number> @Unmodifiable @NotNull Number distance(@NotNull Point<X, Y> point1, @NotNull Point<X, Y> point2) {
		double lat1 = point1.getX().doubleValue();
		double lon1 = point1.getY().doubleValue();
		double lat2 = point2.getX().doubleValue();
		double lon2 = point2.getY().doubleValue();
		double x, y, a, b, distance;

		lat1 *= RADIAN;
		lat2 *= RADIAN;
		x = lat1 - lat2;
		y = lon1 - lon2;
		y *= RADIAN;
		a = Math.sin(x * HALF);
		b = Math.sin(y * HALF);
		distance = EARTH_RADIUS * Math.asin(Math.sqrt(a * a + Math.cos(lat1) * Math.cos(lat2) * b * b)) / HALF;
		return BigDecimal.valueOf(distance).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public static <X extends Number, Y extends Number> @NotNull Point<X, Y> of(X x, Y y) {
		return new SimplePoint<>(x, y);
	}

	@Override
	public X getX() {
		return this.getLng();
	}

	@Override
	public Y getY() {
		return this.getLat();
	}

	@Override
	public Void getZ() {
		throw new UnsupportedOperationException("不存在Z值");
	}

	/**
	 * 获取经纬度
	 *
	 * @return latitude(纬度)
	 */
	public abstract Y getLat();

	/**
	 * 获取经纬度
	 *
	 * @return longitude(经度)
	 */
	public abstract X getLng();

	/**
	 * 获取距离
	 *
	 * @param x 经度
	 * @param y 纬度
	 * @return 距离
	 */
	public Number distance(X x, Y y) {
		return distance(this, of(x, y));
	}

	/**
	 * 简单点对象
	 *
	 * @param <X> X类型
	 * @param <Y> Y类型
	 */
	public static final class SimplePoint<X extends Number, Y extends Number> extends Point<X, Y> {

		private final X x;
		private final Y y;

		public SimplePoint(X x, Y y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public Y getLat() {
			return y;
		}

		@Override
		public X getLng() {
			return x;
		}
	}
}
