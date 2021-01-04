package com.wobangkj.auth;

import com.wobangkj.api.EnumTextMsg;
import com.wobangkj.api.Jwt;
import com.wobangkj.api.SimpleJwt;
import com.wobangkj.cache.Cacheables;
import com.wobangkj.cache.MemCacheImpl;
import com.wobangkj.exception.SecretException;
import com.wobangkj.utils.KeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 权限认证
 *
 * @author cliod
 * @since 9/21/20 5:00 PM
 */
@Slf4j
public abstract class Authenticate {

	private static Cacheables cache;
	private static Jwt jwt;

	/**
	 * 授权
	 *
	 * @param ip   用户ip
	 * @param role 授权角色
	 * @param id   用户唯一id
	 * @return token令牌
	 */
	public static @NotNull String authorize(String ip, Integer role, Long id) {
		init();
		return authorize(Author.builder().ip(ip).role(role).id(id).build());
	}

	/**
	 * 授权
	 *
	 * @param author 授权者
	 * @return token令牌
	 */
	public static @NotNull String authorize(Author author) {
		init();
		String sign = jwt.sign(author, 24, TimeUnit.HOURS);
		String token = createToken(author);
		cache.set(token, sign, 24, TimeUnit.HOURS);
		return token;
	}

	/**
	 * 认证
	 *
	 * @param token 令牌
	 * @return 签名对象
	 */
	public static @Nullable Author authenticate(String token) {
		init();
		String sign = (String) cache.obtain(token);
		if (StringUtils.isEmpty(sign)) {
			return null;
		}
		try {
			return jwt.unsign(sign, Author.class);
		} catch (Exception e) {
			throw new SecretException((EnumTextMsg) () -> "由于程序重启, token已经失效, 请重新登录", e);
		}
	}

	/**
	 * 生成令牌
	 *
	 * @param author 授权者
	 * @return token令牌
	 */
	protected static String createToken(Author author) {
		return KeyUtils.md5Hex(String.format("%d;%d", System.currentTimeMillis(), author.hashCode()));
	}

	/**
	 * 初始化
	 */
	protected static void init() {
		if (Objects.isNull(cache)) {
			cache = new MemCacheImpl();
		}
		if (Objects.isNull(jwt)) {
			try {
				jwt = SimpleJwt.getInstance();
			} catch (Exception e) {
				throw new SecretException((EnumTextMsg) () -> "用户授权失败", e);
			}
		}
	}

}
