package com.wobangkj.api;

import java.util.List;

/**
 * 默认实现
 *
 * @author cliod
 * @since 9/4/20 11:07 AM
 */
public abstract class JpaServiceImpl<D extends IRepository<T>, T> implements IService<T> {

	protected final D dao;

	public JpaServiceImpl(D dao) {
		this.dao = dao;
	}

	@Override
	public T queryById(Long id) {
		return this.dao.queryById(id);
	}

	@Override
	public T queryOne(T t) {
		return this.dao.queryOne(t);
	}

	@Override
	public List<T> queryAll(T t, int offset, int limit) {
		return this.dao.queryAllByConditionLimit(t, offset, limit);
	}

	@Override
	public T insert(T t) {
		this.dao.insert(t);
		return t;
	}

	@Override
	public T update(T t) {
		this.dao.update(t);
		return this.queryOne(t);
	}

	@Override
	public boolean deleteById(Long id) {
		this.dao.deleteById(id);
		return true;
	}

	@Override
	public long count(T t) {
		return this.dao.count(t);
	}
}
