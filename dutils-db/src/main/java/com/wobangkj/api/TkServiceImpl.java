package com.wobangkj.api;

/**
 * 默认实现
 *
 * @author cliod
 * @since 9/4/20 11:07 AM
 */
@Deprecated
public class TkServiceImpl<D extends IMapper<T>, T> extends com.wobangkj.impl.TkServiceImpl<D, T> {

	public TkServiceImpl(D dao) {
		super(dao);
	}

	protected TkServiceImpl() {
	}
}
