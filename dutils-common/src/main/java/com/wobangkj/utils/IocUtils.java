package com.wobangkj.utils;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * get bean utils
 *
 * @author cliod
 * @since 7/11/20 3:22 PM
 */
public abstract class IocUtils implements ApplicationContextAware {

	protected static ApplicationContext applicationContext;

	/**
	 * 获取applicationContext
	 *
	 * @return 上下文
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * 注入ioc
	 *
	 * @param applicationContext ioc容器
	 * @throws BeansException bean异常
	 */
	@Override
	public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
		IocUtils.applicationContext = applicationContext;
	}

	/**
	 * 通过name获取 Bean.
	 *
	 * @param name 名称
	 * @return bean
	 */
	public static @NotNull Object getBean(String name) {
		return getApplicationContext().getBean(name);
	}

	/**
	 * 通过class获取Bean.
	 *
	 * @param clazz 类
	 * @param <T>   类型
	 * @return bean
	 */
	public static <T> @NotNull T getBean(Class<T> clazz) {
		return getApplicationContext().getBean(clazz);
	}

	/**
	 * 通过name,以及Clazz返回指定的Bean
	 *
	 * @param name  名字
	 * @param clazz 类
	 * @param <T>   类型
	 * @return bean
	 */
	public static <T> @NotNull T getBean(String name, Class<T> clazz) {
		return getApplicationContext().getBean(name, clazz);
	}

}
