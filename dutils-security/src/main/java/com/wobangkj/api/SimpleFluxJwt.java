package com.wobangkj.api;

import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

/**
 * jwt加密
 *
 * @author cliod
 * @since 2019/11/9
 * package : com.wobangkj.util
 */
public class SimpleFluxJwt extends FluxJwt implements Signable {

	protected static SimpleFluxJwt INSTANCE = new SimpleFluxJwt();
	/**
	 * 是否初始化
	 */
	protected boolean isInitialize;

	protected SimpleFluxJwt() {
		super();
		isInitialize = false;
	}

	@SneakyThrows
	public static @NotNull SimpleFluxJwt getInstance() {
		SimpleFluxJwt jwt = INSTANCE;
		jwt.initialize();
		return jwt;
	}

	@Override
	protected void initialize() {
		super.initialize();
		this.isInitialize = true;
	}
}
