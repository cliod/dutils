package com.wobangkj.api;

import com.wobangkj.bean.Pager;
import com.wobangkj.domain.Columns;
import com.wobangkj.domain.Condition;

/**
 * 抽象实现
 *
 * @author cliod
 * @since 11/24/20 5:04 PM
 */
public abstract class ServiceImpl<T> implements IService<T> {

	protected int maxSize = 4;
	/**
	 * 用户缓存Entity对应的数据库字段
	 */
	protected Columns<T> fieldCache;

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
	 * 通过ID查询单条数据
	 *
	 * @param id 主键
	 * @return 实例对象
	 */
	@Override
	public T queryById(Object id) {
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
	 * @param t         条件
	 * @param condition 分页
	 * @return 列表
	 */
	@Override
	public Pager<T> queryAll(T t, Condition condition) {
		return getDao().queryAllPage(t, condition);
	}

	/**
	 * 修改数据
	 *
	 * @param t 实例对象
	 * @return 实例对象
	 */
	@Override
	public T update(T t) {
		if (this.getDao().update(t) > 0) {
			return this.queryOne(t);
		}
		return t;
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
	 * 通过主键删除数据
	 *
	 * @param id 主键
	 * @return 是否成功
	 */
	@Override
	public abstract boolean deleteById(Object id);

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
