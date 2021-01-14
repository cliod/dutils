package com.wobangkj.api;

/**
 * 默认实现
 *
 * @author cliod
 * @since 9/4/20 11:07 AM
 */
@Deprecated
public class JpaServiceImpl<D extends IRepository<T>, T> extends com.wobangkj.impl.JpaServiceImpl<D, T> {
	public JpaServiceImpl(D dao) {
		super(dao);
	}

	protected JpaServiceImpl() {
	}
}
