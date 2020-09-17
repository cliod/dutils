package com.wobangkj.plugins.desensitization;

/**
 * 脱敏实现
 *
 * @author cliod
 * @since 9/17/20 10:29 AM
 */
public abstract class DesensitizeHandler<T> {
	/**
	 * 脱敏拦截
	 *
	 * @param invocation 拦截对象
	 * @return 脱敏结果
	 * @throws Throwable 异常
	 */
	public abstract Object intercept(T invocation) throws Throwable;

}
