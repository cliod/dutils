package com.wobangkj.api;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.impl.NullClaim;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.jetbrains.annotations.NotNull;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Objects;

/**
 * jwt加密
 *
 * @author cliod
 * @since 2019/11/9
 */
public abstract class Jwt implements Signable {

	/**
	 * 加密算法 可以抽象到环境变量中配置
	 */
	protected static final String MAC_NAME = "HMacSHA256";
	/**
	 * 发布者 后面一块去校验
	 */
	protected static String ISSUER = "_user";
	/**
	 * 有效载荷
	 */
	protected static String PAYLOAD = "payload";
	/**
	 * 校验类
	 */
	protected JWTVerifier verifier;
	/**
	 * 加密算法
	 */
	protected Algorithm algorithm;
	/**
	 * 秘钥生成器
	 */
	protected KeyGenerator keyGenerator;

	protected Jwt() throws NoSuchAlgorithmException {
		this.initialize();
	}

	public Jwt(KeyGenerator keyGenerator) throws NoSuchAlgorithmException {
		this.initialize(keyGenerator);
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
		JwtBuilder.Builder builder = JwtBuilder.init();
		long now = System.currentTimeMillis();
		long accumulate = now + duration;
		return build(builder.withExpiresAt(new Date(accumulate)), obj, now);
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
		return build(builder.withExpiresAt(date), obj, now);
	}

	/**
	 * 解密，传入一个加密后的token字符串和解密后的类型
	 *
	 * @param jwt 签名字符串
	 * @return 实例对象
	 */
	@Override
	public @NotNull Claim unsign(String jwt) {
		final DecodedJWT claims = verifier.verify(jwt);
		Date date = claims.getExpiresAt();
		long exp = date.getTime();
		long now = System.currentTimeMillis();
		if (exp < now) {
			return new NullClaim();
		}
		return claims.getClaim(PAYLOAD);
	}

	protected String build(Buildable builder, Object obj, long now) {
		return builder.withIssuedAt(new Date(now))
				.withIssuer(ISSUER)
				.withClaim(PAYLOAD, obj).sign(algorithm);
	}

	/**
	 * 初始化
	 *
	 * @param generator 秘钥生成器
	 * @throws NoSuchAlgorithmException 没有这样的算法异常
	 */
	protected void initialize(KeyGenerator generator) throws NoSuchAlgorithmException {
		if (Objects.nonNull(generator)) {
			this.keyGenerator = generator;
		}
		if (Objects.isNull(this.keyGenerator)) {
			this.keyGenerator = KeyGenerator.getInstance(MAC_NAME);
		}
		SecretKey secretKey = keyGenerator.generateKey();
		algorithm = Algorithm.HMAC256(secretKey.getEncoded());
		/*
		 * 校验器 用于生成 JWTVerifier 校验器
		 */
		verifier = JWT.require(algorithm).build();
	}

	/**
	 * 初始化
	 */
	protected void initialize() throws NoSuchAlgorithmException {
		initialize(KeyGenerator.getInstance(MAC_NAME));
	}
}
