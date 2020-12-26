package com.wobangkj.plugins.desensitization.enums;

import com.wobangkj.plugins.desensitization.DesensitizeStrategy;
import com.wobangkj.plugins.desensitization.Desensitizer;

/**
 * 脱敏策略
 *
 * @author cliod
 * @since 9/17/20 10:16 AM
 */
public enum BaseDesensitizeStrategy implements DesensitizeStrategy {
	/**
	 * Nuno sensitive strategy.
	 */
	NUNO(s -> s),
	/**
	 * Username sensitive type.
	 */
	USERNAME(s -> s.replaceAll("(\\S)\\S(\\S*)", "$1*$2")),
	/**
	 * Id card sensitive type.
	 */
	ID_CARD(s -> s.replaceAll("(\\d{4})\\d{10}(\\w{4})", "$1****$2")),
	/**
	 * Phone sensitive type.
	 */
	PHONE(s -> s.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2")),
	/**
	 * Address sensitive type.
	 */
	ADDRESS(s -> s.replaceAll("(\\S{8})\\S{4}(\\S*)\\S{4}", "$1****$2****")),
	/**
	 * Password sensitive type.
	 */
	PASSWORD(s -> "******"),
	;

	private final Desensitizer desensitizer;

	BaseDesensitizeStrategy(Desensitizer desensitizer) {
		this.desensitizer = desensitizer;
	}


	/**
	 * 获取脱敏器
	 *
	 * @return 脱敏器对象
	 */
	@Override
	public Desensitizer getDesensitizer() {
		return this.desensitizer;
	}
}
