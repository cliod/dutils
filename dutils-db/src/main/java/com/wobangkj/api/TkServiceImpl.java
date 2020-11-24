package com.wobangkj.api;

/**
 * 默认实现
 *
 * @author cliod
 * @since 9/4/20 11:07 AM
 */
public class TkServiceImpl<D extends IMapper<T>, T> extends ServiceImpl<T> implements IService<T> {

	protected final D dao;

	public TkServiceImpl(D dao) {
		this.dao = dao;
	}

	@Override
	public D getDao() {
		return this.dao;
	}

	@Override
	public T insert(T t) {
		this.dao.insertSelective(t);
		return t;
	}

	@Override
	public boolean deleteById(Long id) {
		return this.dao.deleteById(id) > 0;
	}

}
