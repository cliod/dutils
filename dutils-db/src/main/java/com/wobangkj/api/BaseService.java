package com.wobangkj.api;

/**
 * crud 默认实现
 *
 * @author cliod
 * @since 9/4/20 11:07 AM
 */
@Deprecated
public class BaseService<D extends IRepository<T>, T> extends JpaServiceImpl<D, T> {
	public BaseService(D dao) {
		super(dao);
	}
}
