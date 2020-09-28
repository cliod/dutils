package com.wobangkj.api;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.wobangkj.utils.BeanUtils;
import com.wobangkj.utils.JsonUtils;
import lombok.SneakyThrows;

import javax.crypto.KeyGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * jwt加密
 *
 * @author cliod
 * @since 2019/11/9
 * package : com.wobangkj.util
 */
public abstract class Jwt implements Signable {

	/**
	 * 发布者 后面一块去校验
	 */
	protected static final String ISSUER = "_user";
	/**
	 * 有效载荷
	 */
	protected static final String PAYLOAD = "payload";
	/**
	 * 加密算法 可以抽象到环境变量中配置
	 */
	protected static final String MAC_NAME = "HMacSHA256";

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

	@SneakyThrows
	protected Jwt() {
		keyGenerator = KeyGenerator.getInstance(MAC_NAME);
	}

	/**
	 * 加密，传入一个对象和有效期/毫秒
	 *
	 * @param obj      对象
	 * @param duration 时长/秒
	 * @return 签名字符串
	 */
	@Override
	public String sign(final Object obj, long duration) {
		JWTCreator.Builder builder = JWT.create();
		long now = System.currentTimeMillis();
		long accumulate = now + duration;
		builder.withExpiresAt(new Date(accumulate));
		return build(builder, obj, now);
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
		JWTCreator.Builder builder = JWT.create();
		long now = System.currentTimeMillis();
		builder.withExpiresAt(date);
		return build(builder, obj, now);
	}

	protected String build(JWTCreator.Builder builder, Object obj, long now) {
		builder.withIssuedAt(new Date(now));
		builder.withIssuer(ISSUER);
		if (BeanUtils.isBaseType(obj)) {
			if (obj instanceof Integer || obj instanceof Short || obj instanceof Byte) {
				// int
				builder.withClaim(PAYLOAD, ((Number) obj).intValue());
			} else if (obj instanceof Double || obj instanceof Float) {
				// double
				builder.withClaim(PAYLOAD, ((Number) obj).doubleValue());
			} else if (obj instanceof Long) {
				// long
				builder.withClaim(PAYLOAD, ((Number) obj).longValue());
			} else if (obj instanceof Boolean) {
				// bool
				builder.withClaim(PAYLOAD, (Boolean) obj);
			} else {
				// char
				builder.withClaim(PAYLOAD, obj.toString());
			}
		} else if (obj.getClass().isInstance("")) {
			// string
			builder.withClaim(PAYLOAD, obj.toString());
		} else if (obj instanceof Date) {
			// date
			builder.withClaim(PAYLOAD, (Date) obj);
		} else {
			// object
			builder.withClaim(PAYLOAD, JsonUtils.toJson(obj));
		}
		return builder.sign(algorithm);
	}

	/**
	 * 解密，传入一个加密后的token字符串和解密后的类型
	 *
	 * @param jwt 签名字符串
	 * @return 实例对象
	 */
	@Override
	public Claim unsign(String jwt) {
		final DecodedJWT claims = verifier.verify(jwt);
		Date date = claims.getExpiresAt();
		long exp = date.getTime();
		long now = System.currentTimeMillis();
		if (exp < now) {
			return null;
		}
		return claims.getClaim(PAYLOAD);
	}

	protected void initialize(KeyGenerator generator) throws NoSuchAlgorithmException {
		if (!BeanUtils.isNull(generator)) {
			this.keyGenerator = generator;
		}
		if (null == this.keyGenerator) {
			this.keyGenerator = KeyGenerator.getInstance(MAC_NAME);
		}
		this.initialize();
	}

	/**
	 * 初始化
	 */
	protected abstract void initialize();
}
