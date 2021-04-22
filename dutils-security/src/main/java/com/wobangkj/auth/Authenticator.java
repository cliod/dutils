package com.wobangkj.auth;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.wobangkj.api.EnumTextMsg;
import com.wobangkj.api.Serializer;
import com.wobangkj.api.Signable;
import com.wobangkj.cache.Cacheable;
import com.wobangkj.exception.SecretException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.Objects;

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
	protected Cacheable cache;

	public Authenticator(@NotNull Cacheable cache, @NotNull Signable signer) {
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
		return this.authorize(author, author.getExpireAt());
	}

	/**
	 * 授权
	 *
	 * @param serializer 授权对象
	 * @param expireAt   过期时间
	 * @return token令牌
	 */
	public @NotNull String authorize(@NotNull Serializer serializer, Date expireAt) {
		Instant instant;
		if (expireAt == null) {
			instant = Instant.now().plus(24, ChronoUnit.HOURS);
		} else {
			instant = expireAt.toInstant();
		}
		return this.authorize(serializer, instant);
	}

	/**
	 * 授权
	 *
	 * @param serializer 授权对象
	 * @param expireAt   过期时间
	 * @return token令牌
	 */
	public @NotNull String authorize(@NotNull Serializer serializer, TemporalAccessor expireAt) {
		if (Objects.isNull(expireAt)) {
			expireAt = Instant.now().plus(24, ChronoUnit.HOURS);
		}
		String sign = this.signer.sign(serializer, expireAt);
		String token = createToken(serializer);
		this.cache.set(token, sign, expireAt);
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
		String sign = (String) this.cache.take(token);
		if (StringUtils.isEmpty(sign)) {
			return null;
		}
		try {
			return this.signer.unsign(sign, type);
		} catch (JWTDecodeException e) {
			throw new SecretException((EnumTextMsg) () -> "The secret key cannot be decrypted", e);
		} catch (TokenExpiredException e) {
			throw new SecretException((EnumTextMsg) e::getMessage, e);
		} catch (Exception e) {
			throw new SecretException((EnumTextMsg) () -> "Unknown exception", e);
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
