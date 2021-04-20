package com.wobangkj.api;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.wobangkj.exception.SecretException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/**
 * jwt加密
 *
 * @author cliod
 * @since 2019/11/9
 */
public abstract class StorageJwt extends Jwt implements Signable {

	protected StorageJwt() throws NoSuchAlgorithmException {
		super();
	}

	public StorageJwt(KeyGenerator generator) throws NoSuchAlgorithmException {
		super(generator);
	}

	/**
	 * 初始化
	 *
	 * @param generator 秘钥生成器
	 * @throws NoSuchAlgorithmException 没有这样的算法异常
	 */
	@Override
	protected void initialize(KeyGenerator generator) throws NoSuchAlgorithmException {
		if (Objects.nonNull(generator)) {
			this.keyGenerator = generator;
		}
		if (Objects.isNull(this.keyGenerator)) {
			this.keyGenerator = KeyGenerator.getInstance(MAC_NAME);
		}
		SecretKey secretKey = keyGenerator.generateKey();
		this.setSecret(secretKey.getEncoded());
		this.initialize(secretKey.getEncoded());
	}

	@Override
	protected void initialize() throws NoSuchAlgorithmException {
		byte[] body = this.getSecret();
		if (Objects.isNull(body) || body.length == 0) {
			this.initialize(this.keyGenerator);
			return;
		}
		this.initialize(body);
	}

	private void initialize(byte[] body) {
		algorithm = Algorithm.HMAC256(body);
		/*
		 * 校验器 用于生成 JWTVerifier 校验器
		 */
		verifier = JWT.require(algorithm).build();
		this.isInitialize = true;
	}

	/**
	 * 是否允许自动初始化
	 *
	 * @return 是否允许自动初始化
	 */
	@Override
	protected boolean enableInitialize() {
		return false;
	}

	/**
	 * 获取存储的密匙
	 *
	 * @return 密匙
	 */
	protected abstract byte[] getSecret() throws SecretException;

	/**
	 * 存储密匙
	 *
	 * @param data 密匙数据
	 */
	protected abstract void setSecret(byte[] data) throws SecretException;
}
