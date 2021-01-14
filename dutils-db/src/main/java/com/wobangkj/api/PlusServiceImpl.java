package com.wobangkj.api;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 默认实现
 *
 * @author cliod
 * @since 11/24/20 1:44 PM
 */
@Deprecated
public class PlusServiceImpl<M extends BaseMapper<T>, T> extends com.wobangkj.impl.PlusServiceImpl<M, T> {
	public PlusServiceImpl(IService<T> service) {
		super(service);
	}

	protected PlusServiceImpl() {
	}
}
