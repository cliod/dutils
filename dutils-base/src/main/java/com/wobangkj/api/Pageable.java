package com.wobangkj.api;

import java.util.HashMap;
import java.util.Map;

/**
 * 可分页的
 *
 * @author cliod
 * @since 9/11/20 4:22 PM
 */
public interface Pageable extends Deserializer {

	/**
	 * 获取页码
	 *
	 * @return 页码
	 */
	Integer getPage();

	/**
	 * 获取大小
	 *
	 * @return 大小
	 */
	Integer getSize();

	default Integer getOffset() {
		return this.getSize() * (this.getPage() - 1);
	}

	default Integer getLimit() {
		return this.getSize();
	}

	/**
	 * 转为Map(映射)对象
	 *
	 * @return 映射
	 */
	@Override
	default Map<String, Object> toMap() {
		Map<String, Object> map = new HashMap<>();
		map.put("page", getPage());
		map.put("size", getSize());
		return map;
	}
}
