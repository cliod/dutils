package com.wobangkj.api;

/**
 * 可分页的
 *
 * @author cliod
 * @since 9/11/20 4:22 PM
 */
public interface Pageable {
	/**
	 * 静态方法
	 *
	 * @param page 分页
	 * @param size 分页
	 * @return 分页对象
	 */
	static Pageable of(Integer page, Integer size) {
		return new Pageable() {
			@Override
			public Integer getPage() {
				return page;
			}

			@Override
			public Integer getSize() {
				return size;
			}
		};
	}

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
