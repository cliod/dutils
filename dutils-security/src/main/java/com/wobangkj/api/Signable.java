package com.wobangkj.api;

import com.auth0.jwt.interfaces.Claim;
import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 可签名的
 *
 * @author cliod
 * @since 9/28/20 1:55 PM
 */
public interface Signable {

	/**
	 * 加密，传入一个对象和有效期/毫秒
	 *
	 * @param obj      对象
	 * @param duration 时长/秒
	 * @return 签名字符串
	 */
	String sign(Object obj, long duration);

	/**
	 * 加密，传入一个对象和有效期/毫秒
	 *
	 * @param obj  对象
	 * @param date 时间/秒
	 * @return 签名字符串
	 */
	String sign(Object obj, Date date);

	/**
	 * 加密，传入一个对象和有效期/指定单位
	 *
	 * @param obj      对象
	 * @param duration 时长
	 * @param unit     单位
	 * @return 签名字符串
	 */
	default String sign(Object obj, long duration, @NotNull TimeUnit unit) {
		return this.sign(obj, unit.toMillis(duration));
	}

	/**
	 * 解密，传入一个加密后的token字符串
	 *
	 * @param jwt 签名字符串
	 * @return 实例对象
	 */
	Claim unsign(String jwt);

	/**
	 * 解密，传入一个加密后的token字符串和解密后的类型
	 *
	 * @param jwt   签名字符串
	 * @param clazz 结果类型
	 * @return 实例对象
	 */
	default <T> T unsign(String jwt, Class<T> clazz) {
		Claim value = this.unsign(jwt);
		if (Objects.isNull(value)) {
			return null;
		}
		return value.as(clazz);
	}

	/**
	 * 解密，传入一个加密后的token字符串
	 *
	 * @param jwt 签名字符串
	 * @return map实例对象
	 */
	default Map<String, Object> unsignToMap(String jwt) {
		Claim value = this.unsign(jwt);
		if (Objects.isNull(value)) {
			return null;
		}
		return value.asMap();
	}
}
