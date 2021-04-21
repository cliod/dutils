package com.wobangkj.cache;

import java.util.Date;

/**
 * 可存储的
 *
 * @author cliod
 * @version 1.0
 * @since 2021-04-21 13:20:59
 */
public interface Storable {

	Object take(Object key);

	void save(Object key, Object value);
}
