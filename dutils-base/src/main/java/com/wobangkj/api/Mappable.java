package com.wobangkj.api;

import java.util.Map;

/**
 * 可以转Map
 *
 * @author cliod
 * @version 1.0
 * @since 2021-01-20 15:48:25
 */
@FunctionalInterface
public interface Mappable<K, V> {
	/**
	 * 转为Map(映射)对象
	 *
	 * @return 映射
	 */
	Map<K, V> toMap();
}
