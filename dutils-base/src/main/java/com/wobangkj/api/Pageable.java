package com.wobangkj.api;

/**
 * 可分页的
 *
 * @author cliod
 * @since 9/11/20 4:22 PM
 */
public interface Pageable {

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
}
