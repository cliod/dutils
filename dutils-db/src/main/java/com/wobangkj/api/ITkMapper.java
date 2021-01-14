package com.wobangkj.api;

import org.apache.ibatis.session.RowBounds;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 通用Mapper, 兼容旧方法
 *
 * @author cliod
 * @since 9/4/20 10:56 AM
 */
public interface ITkMapper<T> extends IDao<T>, Mapper<T> {

	/**
	 * 通过ID查询单条数据
	 *
	 * @param id 主键
	 * @return 实例对象
	 */
	@Override
	default T queryById(Long id) {
		return this.selectByPrimaryKey(id);
	}

	/**
	 * 通过实体作为筛选条件查询
	 *
	 * @param t 实例对象
	 * @return 对象列表
	 */
	@Override
	default T queryOne(T t) {
		return this.selectOne(t);
	}

	/**
	 * 通过实体作为筛选条件查询
	 *
	 * @param t 实例对象
	 * @return 对象列表
	 */
	@Override
	default List<T> queryAll(T t) {
		return this.select(t);
	}

	/**
	 * 通过实体作为筛选条件查询
	 *
	 * @param t      实例对象
	 * @param limit  分页
	 * @param offset 分页
	 * @return 对象列表
	 */
	@Override
	default List<T> queryAllLimit(T t, int offset, int limit) {
		return this.selectByRowBounds(t, new RowBounds(offset, limit));
	}

	/**
	 * 修改数据
	 *
	 * @param t 实例对象
	 * @return 影响行数
	 */
	@Override
	default int update(T t) {
		return this.updateByPrimaryKeySelective(t);
	}

	/**
	 * 通过主键删除数据
	 *
	 * @param id 主键
	 * @return 影响行数
	 */
	default int deleteById(Long id) {
		return this.deleteByPrimaryKey(id);
	}

	/**
	 * 查找个数
	 *
	 * @param t 对象
	 * @return 行数
	 */
	@Override
	default long count(T t) {
		return this.selectCount(t);
	}
}
