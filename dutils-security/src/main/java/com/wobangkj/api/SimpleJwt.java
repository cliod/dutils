package com.wobangkj.api;

import com.wobangkj.exception.SecretException;
import org.jetbrains.annotations.NotNull;

import javax.crypto.KeyGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * jwt加密
 *
 * @author cliod
 * @since 2019/11/9
 */
public class SimpleJwt extends Jwt implements Signable {

	protected static SimpleJwt INSTANCE;

	static {
		try {
			INSTANCE = new SimpleJwt();
		} catch (NoSuchAlgorithmException e) {
			throw new SecretException((EnumTextMsg) () -> "初始化失败", e);
		}
	}

	protected SimpleJwt() throws NoSuchAlgorithmException {
		super();
	}

	public SimpleJwt(KeyGenerator generator) throws NoSuchAlgorithmException {
		super(generator);
	}

	public static @NotNull SimpleJwt getInstance() {
		return INSTANCE;
	}

	@Deprecated
	public static @NotNull SimpleJwt init() {
		return INSTANCE;
	}

	/**
	 * 是否允许自动初始化
	 *
	 * @return 是否允许自动初始化
	 */
	@Override
	protected boolean enableInitialize() {
		return true;
	}
}
