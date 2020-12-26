package com.wobangkj.plugins.desensitization;

import com.wobangkj.plugins.desensitization.annotation.Sensitive;
import com.wobangkj.plugins.desensitization.enums.BaseDesensitizeStrategy;
import lombok.SneakyThrows;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Stream;

/**
 * 可以作为Mybatis插件
 *
 * @author cliod
 * @since 9/17/20 10:25 AM
 */
public class DesensitizePlugin extends DesensitizeHandler<Invocation> {
	/**
	 * 脱敏拦截
	 *
	 * @param invocation 拦截对象
	 * @return 脱敏结果
	 * @throws Throwable 异常
	 */
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		List<?> records = (List<?>) invocation.proceed();
		// 对结果集脱敏
		records.forEach(this::desensitize);
		return records;
	}

	/**
	 * 进行脱敏
	 *
	 * @param source 对象
	 */
	protected void desensitize(Object source) {
		// 拿到返回值类型
		Class<?> sourceClass = source.getClass();
		// 初始化返回值类型的 MetaObject
		MetaObject metaObject = SystemMetaObject.forObject(source);
		// 捕捉到属性上的标记注解 @Sensitive 并进行对应的脱敏处理
		Stream.of(sourceClass.getDeclaredFields())
				.filter(field -> field.isAnnotationPresent(Sensitive.class))
				.forEach(field -> doDesensitize(metaObject, field));
	}

	/**
	 * 真正执行
	 *
	 * @param metaObject 元素
	 * @param field      字段
	 */
	@SneakyThrows
	protected void doDesensitize(MetaObject metaObject, Field field) {
		// 拿到属性名
		String name = field.getName();
		// 获取属性值
		Object value = metaObject.getValue(name);
		// 只有字符串类型才能脱敏  而且不能为null
		if (String.class == metaObject.getGetterType(name) && value != null) {
			Sensitive annotation = field.getAnnotation(Sensitive.class);
			// 获取对应的脱敏策略 并进行脱敏
			DesensitizeStrategy type;
			Class<?> strategyType = annotation.special();
			if (strategyType.isAssignableFrom(BaseDesensitizeStrategy.class)) {
				type = annotation.strategy();
			} else {
				type = (DesensitizeStrategy) strategyType.newInstance();
			}
			Object o = type.getDesensitizer().apply((String) value);
			// 把脱敏后的值塞回去
			metaObject.setValue(name, o);
		}
	}
}
