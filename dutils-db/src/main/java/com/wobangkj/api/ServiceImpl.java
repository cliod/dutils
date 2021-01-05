package com.wobangkj.api;

import com.wobangkj.bean.Pageable;
import com.wobangkj.bean.Pager;

/**
 * 抽象实现
 *
 * @author cliod
 * @since 11/24/20 5:04 PM
 */
public abstract class ServiceImpl<T> implements IService<T> {

	/**
	 * 获取Dao
	 *
	 * @return 通用dao
	 */
	public abstract IDao<T> getDao();

	/**
	 * 通过ID查询单条数据
	 *
	 * @param id 主键
	 * @return 实例对象
	 */
	@Override
	public T queryById(Long id) {
		return this.getDao().queryById(id);
	}

	/**
	 * 通过参数查询单条数据
	 *
	 * @param t 参数
	 * @return 实例对象
	 */
	@Override
	public T queryOne(T t) {
		return this.getDao().queryOne(t);
	}

	/**
	 * 查询
	 *
	 * @param t        条件
	 * @param pageable 分页
	 * @return 列表
	 */
	@Override
	public Pager<T> queryAll(T t, Pageable pageable) {
		return getDao().queryAllPage(t, pageable);
	}

	/**
	 * 修改数据
	 *
	 * @param t 实例对象
	 * @return 实例对象
	 */
	@Override
	public T update(T t) {
		this.getDao().update(t);
		return this.queryOne(t);
	}

	/**
	 * 新增数据
	 *
	 * @param t 实例对象
	 * @return 实例对象
	 */
	@Override
	public abstract T insert(T t);

	/**
	 * 通过主键删除数据
	 *
	 * @param id 主键
	 * @return 是否成功
	 */
	@Override
	public abstract boolean deleteById(Long id);

	/**
	 * 查找行数
	 *
	 * @param t 实例对象
	 * @return 实例行数
	 */
	@Override
	public long count(T t) {
		return this.getDao().count(t);
	}
}
