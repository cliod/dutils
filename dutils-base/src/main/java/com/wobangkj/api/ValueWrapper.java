package com.wobangkj.api;

/**
 * value包装器
 *
 * @author cliod
 * @since 7/9/20 3:55 PM
 */
public interface ValueWrapper<T> extends SessionSerializable {
	/**
	 * 静态方法
	 *
	 * @param obj 对象
	 * @return 包装对象
	 */
	static ValueWrapper<Object> of(Object obj) {
		return new SimpleValueWrapper(obj);
	}

	/**
	 * 获取对象
	 *
	 * @return 对象
	 */
	T value();

	/**
	 * 默认实现
	 */
	class SimpleValueWrapper implements ValueWrapper<Object> {
		private final Object value;

		public SimpleValueWrapper(Object value) {
			this.value = value;
		}

		@Override
		public Object value() {
			return value;
		}
	}
}
