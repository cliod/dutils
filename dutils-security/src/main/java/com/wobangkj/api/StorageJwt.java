package com.wobangkj.api;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import javax.crypto.SecretKey;
import java.util.Objects;

/**
 * jwt加密
 *
 * @author cliod
 * @since 2019/11/9
 * package : com.wobangkj.util
 */
public abstract class StorageJwt extends Jwt implements Signable {

	protected StorageJwt() {
		super();
	}

	@Override
	public void initialize() {
		byte[] body = this.getSecret();
		if (Objects.isNull(body) || body.length == 0) {
			SecretKey secretKey = keyGenerator.generateKey();
			body = secretKey.getEncoded();
			this.setSecret(body);
		}
		algorithm = Algorithm.HMAC256(body);
		/*
		 * 校验器 用于生成 JWTVerifier 校验器
		 */
		verifier = JWT.require(algorithm).build();
	}


	/**
	 * 获取存储的密匙
	 *
	 * @return 密匙
	 */
	protected abstract byte[] getSecret();

	/**
	 * 存储密匙
	 *
	 * @param data 密匙数据
	 */
	protected abstract void setSecret(byte[] data);
}
