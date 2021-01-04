package com.wobangkj.api;

import lombok.SneakyThrows;
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

	protected static SimpleJwt INSTANCE = new SimpleJwt();
	/**
	 * 是否初始化
	 */
	protected boolean isInitialize;

	@SneakyThrows
	protected SimpleJwt() {
		super();
		isInitialize = true;
	}

	public SimpleJwt(KeyGenerator generator) throws NoSuchAlgorithmException {
		super(generator);
		isInitialize = true;
	}

	public static @NotNull SimpleJwt getInstance() {
		return INSTANCE;
	}

	@Deprecated
	public static @NotNull SimpleJwt init() {
		return INSTANCE;
	}

	/**
	 * 加密，传入一个对象和有效期/毫秒
	 *
	 * @param obj      对象
	 * @param duration 时长/秒
	 * @return 签名字符串
	 */
	@Override
	public String sign(Object obj, long duration) {
		if (!isInitialize) {
			throw new RuntimeException("未初始化");
		}
		return super.sign(obj, duration);
	}

	/**
	 * 加密，传入一个对象和有效期/毫秒
	 *
	 * @param obj  对象
	 * @param date 时间/秒
	 * @return 签名字符串
	 */
	@Override
	public String sign(Object obj, Date date) {
		if (!isInitialize) {
			throw new RuntimeException("未初始化");
		}
		return super.sign(obj, date);
	}

	/**
	 * 解密
	 *
	 * @param jwt   jwt密匙
	 * @param clazz 类
	 * @param <T>   类型
	 * @return 结果对象
	 */
	@Override
	public <T> T unsign(String jwt, Class<T> clazz) {
		if (!isInitialize) {
			throw new RuntimeException("未初始化");
		}
		return super.unsign(jwt, clazz);
	}

	@Override
	protected void initialize() throws NoSuchAlgorithmException {
		this.isInitialize = true;
		super.initialize();
	}
}
