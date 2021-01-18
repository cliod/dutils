package com.wobangkj.api;

import com.wobangkj.domain.Pageable;

/**
 * 通用controller接口
 *
 * @author cliod
 * @version 1.0
 * @since 2021-01-18 10:48:24
 */
public interface IController<T> {

	/**
	 * 通过查询单条数据
	 *
	 * @param t 参数
	 * @return 单条数据
	 */
	Object get(T t);

	/**
	 * 查询多条数据
	 *
	 * @param t        参数
	 * @param pageable 分页、排序，模糊查询
	 * @return 列表
	 */
	Object list(T t, Pageable pageable);

	/**
	 * 添加一条数据
	 *
	 * @param t 数据
	 * @return 结果
	 */
	Object add(T t);

	/**
	 * 编辑一条数据
	 *
	 * @param t 数据
	 * @return 结果
	 */
	Object edit(T t);

	/**
	 * 删除一条数据
	 *
	 * @param id id
	 * @return 结果
	 */
	Object del(Long id);
}
