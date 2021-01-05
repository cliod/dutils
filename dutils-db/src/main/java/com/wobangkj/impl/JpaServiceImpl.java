package com.wobangkj.impl;

import com.wobangkj.api.IDao;
import com.wobangkj.api.IRepository;
import com.wobangkj.api.IService;
import com.wobangkj.api.ServiceImpl;

/**
 * 默认实现
 *
 * @author cliod
 * @since 9/4/20 11:07 AM
 */
public class JpaServiceImpl<D extends IRepository<T>, T> extends ServiceImpl<T> implements IService<T> {

	protected final D dao;

	public JpaServiceImpl(D dao) {
		this.dao = dao;
	}

	/**
	 * 获取Dao
	 *
	 * @return 通用dao
	 */
	@Override
	public IDao<T> getDao() {
		return this.dao;
	}

	@Override
	public T insert(T t) {
		this.dao.insert(t);
		return t;
	}

	@Override
	public boolean deleteById(Long id) {
		this.dao.deleteById(id);
		return true;
	}
}
