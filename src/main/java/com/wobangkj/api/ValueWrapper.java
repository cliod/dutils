package com.wobangkj.api;

/**
 * value包装器
 *
 * @author cliod
 * @since 7/9/20 3:55 PM
 */
public interface ValueWrapper<T> extends Session {

    T value();

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
