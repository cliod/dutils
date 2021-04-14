package com.wobangkj.auth;

import com.wobangkj.api.EnumTextMsg;
import com.wobangkj.api.Jwt;
import com.wobangkj.cache.Cacheables;
import com.wobangkj.exception.SecretException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
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

	protected Jwt jwt;
	protected Cacheables cache;

	public Authenticator(@NotNull Cacheables cache, @NotNull Jwt jwt) {
		this.cache = cache;
		this.jwt = jwt;
	}

	/**
	 * 授权
	 *
	 * @param author 授权者
	 * @return token令牌
	 */
	public @NotNull String authorize(@NotNull Author author) {
		if (Objects.isNull(author.getDuration())) {
			author.setDuration(Duration.of(24, ChronoUnit.HOURS));
		}
		String sign = this.jwt.sign(author, author.getDuration());
		String token = createToken(author);
		this.cache.set(token, sign, author.getDuration());
		return token;
	}

	/**
	 * 认证
	 *
	 * @param token 令牌
	 * @return 签名对象
	 */
	public @Nullable Author authenticate(@NotNull String token) {
		String sign = (String) this.cache.obtain(token);
		if (StringUtils.isEmpty(sign)) {
			return null;
		}
		try {
			return this.jwt.unsign(sign, Author.class);
		} catch (Exception e) {
			throw new SecretException((EnumTextMsg) () -> "由于秘钥失效或丢失，token已经失效，请重新登录", e);
		}
	}

	/**
	 * 生成令牌
	 *
	 * @param author 授权者
	 * @return token令牌
	 */
	protected abstract String createToken(Author author);
}
