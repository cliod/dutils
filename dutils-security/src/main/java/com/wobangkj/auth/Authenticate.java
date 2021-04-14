package com.wobangkj.auth;

import com.wobangkj.api.SimpleJwt;
import com.wobangkj.cache.LruMemCacheImpl;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * 权限认证
 *
 * @author cliod
 * @since 9/21/20 5:00 PM
 */
@Slf4j
public abstract class Authenticate {

	@Getter
	@Setter
	protected static Authenticator authenticator;

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
	 * @param author 授权者
	 * @return token令牌
	 */
	public static @NotNull String authorize(Author author) {
		init();
		return authenticator.authorize(author);
	}

	/**
	 * 认证
	 *
	 * @param token 令牌
	 * @return 签名对象
	 */
	public static @Nullable Author authenticate(String token) {
		init();
		return authenticator.authenticate(token);
	}

	/**
	 * 初始化
	 */
	protected static void init() {
		if (Objects.isNull(authenticator)) {
			authenticator = new DefaultAuthenticator(new LruMemCacheImpl(), SimpleJwt.getInstance());
		}
	}
}
