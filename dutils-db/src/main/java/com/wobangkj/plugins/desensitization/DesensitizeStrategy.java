package com.wobangkj.plugins.desensitization;

/**
 * 获取脱敏策略
 *
 * @author cliod
 * @since 9/17/20 9:42 AM
 */
public interface DesensitizeStrategy {
	/**
	 * 获取脱敏器
	 *
	 * @return 脱敏器对象
	 */
	Desensitizer getDesensitizer();
}
