package com.wobangkj.api;

import com.wobangkj.bean.Pageable;
import com.wobangkj.bean.Pager;

import java.util.List;

/**
 * DAO接口
 *
 * @author cliod
 * @since 9/16/20 9:22 AM
 */
public interface IDao<T> {

	/**
	 * 通过ID查询单条数据
	 *
	 * @param id 主键
	 * @return 实例对象
	 */
	T queryById(Long id);

	/**
	 * 通过实体作为筛选条件查询
	 *
	 * @param t 实例对象
	 * @return 对象列表
	 */
	T queryOne(T t);

	/**
	 * 通过实体作为筛选条件查询
	 *
	 * @param t 实例对象
	 * @return 对象列表
	 */
	List<T> queryAllByEntity(T t);

	/**
	 * 通过实体作为筛选条件查询
	 *
	 * @param t      实例对象
	 * @param limit  分页
	 * @param offset 分页
	 * @return 对象列表
	 */
	List<T> queryAllByConditionLimit(T t, int offset, int limit);

	/**
	 * 修改数据
	 *
	 * @param t 实例对象
	 * @return 影响行数
	 */
	int update(T t);

	/**
	 * 查找个数
	 *
	 * @param t 对象
	 * @return 行数
	 */
	long count(T t);

	/**
	 * 通过实体作为筛选条件查询
	 *
	 * @param t        实例对象
	 * @param pageable 分页
	 * @return 对象列表
	 */
	default Pager<T> queryAll(T t, Pageable pageable) {
		long count = this.count(t);
		if (count == 0) {
			return Pager.empty();
		}
		return Pager.of(count, pageable, this.queryAllByConditionLimit(t, pageable.getMybatisPage(), pageable.getLimit()));
	}
}
