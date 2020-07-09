package com.wobangkj.api;

/**
 * value包装器
 *
 * @author cliod
 * @since 7/9/20 3:55 PM
 */
public interface ValueWrapper {

    Object get();

    class SimpleValueWrapper implements ValueWrapper {
        private final Object value;

        public SimpleValueWrapper(Object value) {
            this.value = value;
        }

        @Override
        public Object get() {
            return value;
        }
    }
}
