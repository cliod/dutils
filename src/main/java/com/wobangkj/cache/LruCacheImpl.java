package com.wobangkj.cache;

import com.wobangkj.api.ValueWrapper;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * lru算法实现缓存
 *
 * @author cliod
 * @since 7/7/20 4:43 PM
 */
public class LruCacheImpl extends MapCacheImpl {
    private final static Map<Object, Object> CACHE = new LruCache();

    @Override
    public void set(Object key, Object value, Timing timing) {
        CACHE.put(key, value);
        TIMING.put(key, timing);
        if (isShutdown()) {
            this.timing();
        }
    }

    @Override
    public @NotNull String getName() {
        return "lru_cache";
    }

    @Override
    public @NotNull Object getNativeCache() {
        return CACHE;
    }

    @Override
    public void clear() {
        CACHE.clear();
    }

    @Override
    public ValueWrapper get(@NotNull Object key) {
        return new ValueWrapper.SimpleValueWrapper(CACHE.get(key));
    }

    @Override
    public Object obtain(Object key) {
        return CACHE.get(key);
    }

    @Override
    public void del(Object key) {
        CACHE.remove(key);
    }

    /**
     * 缓存
     */
    public static final class LruCache extends LinkedHashMap<Object, Object> {
        private static final long serialVersionUID = 1L;
        private final int maxSize;

        public LruCache(int maxSize) {
            this(maxSize, 16, 0.75f, false);
        }

        public LruCache(int maxSize, int initialCapacity, float loadFactor, boolean accessOrder) {
            super(initialCapacity, loadFactor, accessOrder);
            this.maxSize = maxSize;
        }

        public LruCache() {
            this(128);
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry<Object, Object> eldest) {
            return this.size() > this.maxSize;
        }
    }
}
