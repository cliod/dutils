package com.wobangkj.api;

import com.wobangkj.cache.Cacheables;
import com.wobangkj.cache.MapCacheImpl;
import com.wobangkj.utils.KeyUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 权限认证
 *
 * @author cliod
 * @since 9/21/20 5:00 PM
 */
@Slf4j
@Component
public class Authenticate {

	private static Cacheables CACHE;
	private static Jwt jwt;

	/**
	 * 授权
	 *
	 * @return token
	 */
	public static String authorize(String ip, Integer auth, Long id) {
		init();
		String sign = jwt.sign(new SignObj(ip, auth, id), 24, TimeUnit.HOURS);
		String token = genToken(ip, id.toString());
		CACHE.set(token, sign, 24, TimeUnit.HOURS);
		return token;
	}

	/**
	 * 认证
	 *
	 * @return 签名对象
	 */
	public static SignObj authenticate(String token) {
		init();
		String sign = (String) CACHE.obtain(token);
		if (StringUtils.isEmpty(sign)) {
			return null;
		}
		try {
			return jwt.unsign(sign, SignObj.class);
		} catch (Exception e) {
			throw new IllegalArgumentException("由于程序重启, token已经失效, 请重新登录", e);
		}
	}

	private static String genToken(String ip, String id) {
		return KeyUtils.md5(String.format("%s,%s,%s", LocalDate.now().toString(), ip, id));
	}

	private static void init() {
		if (Objects.isNull(CACHE)) {
			CACHE = new MapCacheImpl();
		}
		if (Objects.isNull(jwt)) {
			try {
				jwt = Jwt.init();
			} catch (Exception e) {
				throw new IllegalArgumentException("用户无法登录");
			}
		}
	}

	/**
	 * 签名缓存对象
	 */
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class SignObj {
		private String ip;
		/**
		 * 权限, 0管理员, 2人力资源, 1企业, 3用户
		 */
		private Integer auth;
		private Long id;
	}
}
