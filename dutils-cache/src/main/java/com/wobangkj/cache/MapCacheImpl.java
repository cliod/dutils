package com.wobangkj.cache;

import com.wobangkj.ThreadExecutor;
import org.jetbrains.annotations.NotNull;
import org.springframework.cache.support.SimpleValueWrapper;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

/**
 * map cache
 *
 * @author cliod
 * @since 6/22/20 10:16 AM
 */
public class MapCacheImpl implements Cacheables {

	protected final static Map<Object, Timing> TIMING = new WeakHashMap<>();
	private final static Map<Object, Object> CACHE = new WeakHashMap<>();
	private transient boolean shutdown = true;

	public boolean isShutdown() {
		return shutdown;
	}

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
		return "map_cache";
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
	public @NotNull ValueWrapper get(@NotNull Object key) {
		return new SimpleValueWrapper(CACHE.get(key));
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
	 * 定时任务
	 */
	protected void timing() {
		this.shutdown = false;
		ThreadExecutor.timing.scheduleWithFixedDelay(this::autoDelete, 1, 5, TimeUnit.MINUTES);
	}

	/**
	 * 关掉自动任务
	 */
	protected void shutdown() {
		ThreadExecutor.timing.shutdown();
	}

	/**
	 * 自动清除
	 */
	protected void autoDelete() {
		LocalDateTime now = LocalDateTime.now();
		for (Map.Entry<Object, Timing> entry : TIMING.entrySet()) {
			if (entry.getValue().getDeadline().isBefore(now)) {
				CACHE.remove(entry.getKey());
				TIMING.remove(entry.getKey());
			}
		}
		if (TIMING.isEmpty()) {
			this.shutdown();
			shutdown = true;
		}
	}
}
