package com.wobangkj.api;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * jwt加密
 *
 * @author cliod
 * @since 2019/11/9
 * package : com.wobangkj.util
 */
public class SimpleJwt extends Jwt implements Signable {

	protected static SimpleJwt INSTANCE = new SimpleJwt();
	/**
	 * 是否初始化
	 */
	protected boolean isInitialize;

	protected SimpleJwt() {
		super();
		isInitialize = false;
	}

	@SneakyThrows
	public static @NotNull SimpleJwt getInstance() {
		SimpleJwt jwt = INSTANCE;
		jwt.initialize();
		return jwt;
	}

	@Deprecated
	public static @NotNull SimpleJwt init() throws NoSuchAlgorithmException {
		SimpleJwt jwt = INSTANCE;
		jwt.initialize(KeyGenerator.getInstance(MAC_NAME));
		return jwt;
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
	protected void initialize() {
		SecretKey secretKey = keyGenerator.generateKey();
		algorithm = Algorithm.HMAC256(secretKey.getEncoded());
		/*
		 * 校验器 用于生成 JWTVerifier 校验器
		 */
		verifier = JWT.require(algorithm).build();
		this.isInitialize = true;
	}
}
