package com.wobangkj.auth;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.wobangkj.api.EnumTextMsg;
import com.wobangkj.api.Serializer;
import com.wobangkj.api.Signable;
import com.wobangkj.cache.Cacheables;
import com.wobangkj.exception.SecretException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 权限认证
 *
 * @author cliod
 * @since 2021-04-14 10:17:19
 */
@Slf4j
@Getter
public abstract class Authenticator {

	protected Signable signer;
	protected Cacheables cache;

	public Authenticator(@NotNull Cacheables cache, @NotNull Signable signer) {
		this.cache = cache;
		this.signer = signer;
	}

	@Deprecated
	public @NotNull Signable getJwt() {
		return signer;
	}

	/**
	 * 授权
	 *
	 * @param author 授权者
	 * @return token令牌
	 */
	public @NotNull String authorize(@NotNull Authorized author) {
		if (Objects.isNull(author.getExpireAt())) {
			author.setExpireAt(Instant.now().plus(24, ChronoUnit.HOURS));
		}
		String sign = this.signer.sign(author, author.getExpireAt());
		String token = createToken(author);
		this.cache.set(token, sign, author.getExpireAt());
		return token;
	}

	/**
	 * 授权
	 *
	 * @param serializer 授权对象
	 * @param expireAt   过期时间
	 * @return token令牌
	 */
	public @NotNull String authorize(@NotNull Serializer serializer, Date expireAt) {
		String sign = this.signer.sign(serializer, expireAt);
		String token = createToken(serializer);
		this.cache.set(token, sign, expireAt);
		return token;
	}

	/**
	 * 授权
	 *
	 * @param serializer 授权对象
	 * @param expireAt   过期时间
	 * @return token令牌
	 */
	public @NotNull String authorize(@NotNull Serializer serializer, TemporalAccessor expireAt) {
		long milli = expireAt.getLong(ChronoField.MILLI_OF_SECOND);
		String sign = this.signer.sign(serializer, milli);
		String token = createToken(serializer);
		this.cache.set(token, sign, milli, TimeUnit.MILLISECONDS);
		return token;
	}

	/**
	 * 认证
	 *
	 * @param token 令牌
	 * @return 签名对象
	 */
	public @Nullable Serializer authenticate(@NotNull String token) {
		return this.authenticate(token, Author.class);
	}

	/**
	 * 认证
	 *
	 * @param token 令牌
	 * @param type  结果类型
	 * @param <T>   类型
	 * @return 签名对象
	 */
	public @Nullable <T extends Serializer> T authenticate(@NotNull String token, Class<T> type) {
		String sign = (String) this.cache.obtain(token);
		if (StringUtils.isEmpty(sign)) {
			return null;
		}
		try {
			return this.signer.unsign(sign, type);
		} catch (JWTDecodeException e) {
			throw new SecretException((EnumTextMsg) () -> "由于秘钥失效或丢失，token已经失效，请重新登录", e);
		} catch (Exception e) {
			throw new SecretException((EnumTextMsg) () -> "未知异常", e);
		}
	}

	/**
	 * 生成令牌
	 *
	 * @param author 授权者
	 * @return token令牌
	 */
	protected abstract String createToken(Serializer author);
}
