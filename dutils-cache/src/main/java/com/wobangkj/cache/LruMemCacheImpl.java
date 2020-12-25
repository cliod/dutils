package com.wobangkj.cache;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * lru memory cache
 *
 * @author cliod
 * @since 6/22/20 10:16 AM
 */
public class LruMemCacheImpl extends MemCacheImpl implements Cacheables {
	@Override
	protected void init(int initialCapacity) {
		this.cache = new LinkedHashMap<Object, Object>(initialCapacity) {
			/**
			 * 满足一定条件自动删除最末尾数据
			 *
			 * @param eldest 节点
			 * @return 是否满足
			 */
			@Override
			protected boolean removeEldestEntry(Map.Entry<Object, Object> eldest) {
				return this.size() >= initialCapacity;
			}
		};
	}

	@Override
	public @NotNull String getName() {
		return "lru_" + super.getName();
	}

	@Override
	public @NotNull Object getNativeCache() {
		return this.cache;
	}
}
