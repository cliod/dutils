package com.wobangkj.auth;

import com.wobangkj.api.Serializer;
import com.wobangkj.api.Signable;
import com.wobangkj.api.SimpleJwt;
import com.wobangkj.cache.Cacheables;
import com.wobangkj.cache.LruMemCacheImpl;
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

	protected static final DefaultAuthenticator INSTANCE = new DefaultAuthenticator();

	public DefaultAuthenticator(@NotNull Cacheables cache, @NotNull Signable signable) {
		super(cache, signable);
	}

	protected DefaultAuthenticator() {
		this(new LruMemCacheImpl(), SimpleJwt.getInstance());
	}

	/**
	 * 生成令牌
	 *
	 * @param serializer 授权对象
	 * @return token令牌
	 */
	@Override
	protected String createToken(Serializer serializer) {
		return KeyUtils.md5Hex(String.format("%d;%d;%s", System.currentTimeMillis(), serializer.hashCode(), serializer.toJson()));
	}
}
