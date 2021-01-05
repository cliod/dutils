package com.wobangkj.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * plus映射
 *
 * @author cliod
 * @version 1.0
 * @since 2021-01-05 10:10:35
 */
public interface IPlusMapper<T> extends IDao<T>, BaseMapper<T> {
	/**
	 * 通过ID查询单条数据
	 *
	 * @param id 主键
	 * @return 实例对象
	 */
	@Override
	default T queryById(Long id) {
		return this.selectById(id);
	}

	/**
	 * 通过实体作为筛选条件查询
	 *
	 * @param t 实例对象
	 * @return 对象列表
	 */
	@Override
	default T queryOne(T t) {
		return this.selectOne(new QueryWrapper<>(t));
	}

	/**
	 * 通过实体作为筛选条件查询
	 *
	 * @param t 实例对象
	 * @return 对象列表
	 */
	@Override
	default List<T> queryAll(T t) {
		return this.selectList(new QueryWrapper<>(t));
	}

	/**
	 * 通过实体作为筛选条件查询
	 *
	 * @param t      实例对象
	 * @param offset 分页
	 * @param limit  分页
	 * @return 对象列表
	 */
	@Override
	default List<T> queryAllLimit(T t, int offset, int limit) {
		Page<T> page = this.selectPage(new Page<>(offset, limit), new QueryWrapper<>(t));
		return page.getRecords();
	}

	/**
	 * 修改数据
	 *
	 * @param t 实例对象
	 * @return 影响行数
	 */
	@Override
	default int update(T t) {
		return this.updateById(t);
	}

	/**
	 * 查找个数
	 *
	 * @param t 对象
	 * @return 行数
	 */
	@Override
	default long count(T t) {
		return this.selectCount(new QueryWrapper<>(t));
	}
}
