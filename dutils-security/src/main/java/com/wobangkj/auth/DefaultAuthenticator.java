package com.wobangkj.auth;

import com.wobangkj.api.Jwt;
import com.wobangkj.cache.Cacheables;
import com.wobangkj.utils.KeyUtils;
import org.jetbrains.annotations.NotNull;

/**
 * 默认授权
 *
 * @author cliod
 * @version 1.0
 * @since 2021-04-14 10:27:19
 */
public class DefaultAuthenticator extends Authenticator {

	public DefaultAuthenticator(@NotNull Cacheables cache, @NotNull Jwt jwt) {
		super(cache, jwt);
	}

	/**
	 * 生成令牌
	 *
	 * @param author 授权者
	 * @return token令牌
	 */
	@Override
	protected String createToken(Author author) {
		return KeyUtils.md5Hex(String.format("%d;%d", System.currentTimeMillis(), author.hashCode()));
	}
}
