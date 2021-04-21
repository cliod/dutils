package com.wobangkj.auth;

import com.wobangkj.api.Serializer;
import com.wobangkj.api.Signable;
import com.wobangkj.api.SimpleJwt;
import com.wobangkj.cache.Cacheables;
import com.wobangkj.cache.LruMemCacheImpl;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;
import java.util.Objects;

/**
 * 权限认证
 *
 * @author cliod
 * @since 9/21/20 5:00 PM
 */
@Slf4j
public abstract class Authenticate {

	protected static Authenticator authenticator;

	public static @NotNull Authenticator getAuthenticator() {
		init();
		return Authenticate.authenticator;
	}

	public static void setAuthenticator(Cacheables cacheables) {
		Authenticate.setAuthenticator(cacheables, SimpleJwt.getInstance());
	}

	public static void setAuthenticator(Authenticator authenticator) {
		Authenticate.authenticator = authenticator;
	}

	public static void setAuthenticator(Cacheables cacheables, Signable jwt) {
		Authenticate.setAuthenticator(new DefaultAuthenticator(cacheables, jwt));
	}

	/**
	 * 授权
	 *
	 * @param ip   用户ip
	 * @param role 授权角色
	 * @param id   用户唯一id
	 * @return token令牌
	 */
	public static @NotNull String authorize(String ip, Object role, Object id) {
		return authorize(Author.builder().key(ip).role(role).id(id).build());
	}

	/**
	 * 授权
	 *
	 * @param ip   用户ip
	 * @param role 授权角色
	 * @param id   用户唯一id
	 * @param data 额外存储的数据
	 * @return token令牌
	 */
	public static @NotNull String authorize(String ip, Object role, Object id, Object data) {
		return authorize(Author.builder().key(ip).role(role).id(id).data(data).build());
	}

	/**
	 * 授权
	 *
	 * @param author 授权者
	 * @return token令牌
	 */
	public static @NotNull String authorize(Author author) {
		return authorize(author, author.getExpireAt());
	}

	/**
	 * 授权
	 *
	 * @param serializer 授权对象
	 * @param expireAt   过期时间
	 * @return token令牌
	 */
	public static @NotNull String authorize(Serializer serializer, Date expireAt) {
		return getAuthenticator().authorize(serializer, expireAt);
	}

	/**
	 * 认证
	 *
	 * @param token 令牌
	 * @return 签名对象
	 */
	public static @Nullable Author authenticate(String token) {
		return authenticate(token, Author.class);
	}

	/**
	 * 认证
	 *
	 * @param token 令牌
	 * @return 签名对象
	 */
	public static @Nullable <T extends Serializer> T authenticate(String token, Class<T> type) {
		return getAuthenticator().authenticate(token, type);
	}

	/**
	 * 初始化
	 */
	protected static void init() {
		if (Objects.isNull(Authenticate.authenticator)) {
			Authenticate.setAuthenticator(new LruMemCacheImpl());
		}
	}
}
