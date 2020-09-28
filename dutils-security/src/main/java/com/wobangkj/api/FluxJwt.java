package com.wobangkj.api;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Verification;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * jwt加密
 *
 * @author cliod
 * @since 2019/11/9
 * package : com.wobangkj.util
 */
public abstract class FluxJwt extends Jwt implements Signable {
	/**
	 * 加密，传入一个对象和有效期/毫秒
	 *
	 * @param obj      对象
	 * @param duration 时长/秒
	 * @return 签名字符串
	 */
	@Override
	public String sign(Object obj, long duration) {
		JwtBuilder.Builder builder = JwtBuilder.init();
		long now = System.currentTimeMillis();
		long accumulate = now + duration;
		return builder.withIssuedAt(new Date(now))
				.withExpiresAt(new Date(accumulate))
				.withIssuer(ISSUER)
				.withClaim(PAYLOAD, obj).sign(algorithm);
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
		JwtBuilder.Builder builder = JwtBuilder.init();
		long now = System.currentTimeMillis();
		return builder.withIssuedAt(new Date(now))
				.withExpiresAt(date)
				.withIssuer(ISSUER)
				.withClaim(PAYLOAD, obj).sign(algorithm);
	}

	@Override
	protected void initialize() {
		SecretKey secretKey = keyGenerator.generateKey();
		algorithm = Algorithm.HMAC256(secretKey.getEncoded());
		/*
		 * 校验器 用于生成 JWTVerifier 校验器
		 */
		Verification verification = JWT.require(algorithm);
		verifier = verification.build();
	}
}
