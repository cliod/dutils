package com.wobangkj.utils;

import com.wobangkj.api.EnumTextMsg;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.time.temporal.Temporal;
import java.util.*;
import java.util.function.Supplier;

import static java.lang.reflect.Modifier.*;
import static java.util.Objects.isNull;

/**
 * Bean工具类
 *
 * @author cliod
 * @since 19-7-18
 */
public class BeanUtils {
	private BeanUtils() {
	}
	/**
	 * 验证对象是否为空
	 *
	 * @param obj 对象
	 * @param e   为空时抛出的异常
	 * @param <T> 对象类型
	 * @see com.wobangkj.api.asserts.Assert#isNull(Object, String)
	 */
	@Deprecated
	public static <T> void verifyNonNull(T obj, @NotNull Supplier<String> e) {
		if (isNull(obj) || isEmpty(obj)) {
			throw new NullPointerException(e.get());
		}
	}

	/**
	 * 请求对象是否为空,空抛出异常
	 *
	 * @param obj 对象
	 * @param e   异常
	 * @param <T> 对象类型
	 * @return 对象
	 * @see Objects#requireNonNull(Object, Supplier)
	 */
	@Deprecated
	public static <T> T requireNonNull(T obj, @NotNull Supplier<String> e) {
		if (isNull(obj)) {
			throw new NullPointerException(e.get());
		}
		return obj;
	}

	/**
	 * 请求对象是否为空,空抛出异常
	 *
	 * @param obj 对象
	 * @param <T> 对象类型
	 * @return 对象
	 * @see Objects#requireNonNull(Object)
	 */
	@Deprecated
	public static <T> T requireNonNull(T obj) {
		return requireNonNull(obj, (EnumTextMsg) () -> "未知异常");
	}

	/**
	 * 对象是否为空
	 *
	 * @param obj 对象
	 * @param <T> 对象类型
	 * @return 是否为空
	 * @see Objects#isNull(Object)
	 */
	@Deprecated
	public static <T> boolean isNull(T obj) {
		return Objects.isNull(obj);
	}

	/**
	 * 判断对象是否相等(对象需要重写 equals() 方法)
	 *
	 * @param var0 对象1
	 * @param var1 对象2
	 * @return 是否相等
	 * @see Objects#equals(Object, Object)
	 */
	@Deprecated
	public static boolean equals(Object var0, Object var1) {
		return Objects.equals(var0, var1);
	}

	/**
	 * 判断对象以及内容是否为空
	 * <li>只要有一个不为空即不为空</li>
	 *
	 * @param obj 对象
	 * @return 是否为空
	 */
	@SneakyThrows
	public static boolean isEmpty(Object obj) {
		if (isNull(obj)) {
			//为空
			return true;
		}
		if (isBaseType(obj)) {
			//基本类型--> not empty
			return false;
		}
		if (obj instanceof Number) {
			//不为null的Number--> not empty
			return false;
		}
		if (obj instanceof Enum) {
			//不为null的枚举--> not empty
			return false;
		}
		if (obj instanceof Date || obj instanceof Temporal) {
			//时间不为空
			return false;
		}
		Class<?> clazz = obj.getClass();
		if (clazz.isArray()) {
			//数组,看长度
			return Array.getLength(obj) == 0;
		}
		if (obj instanceof CharSequence) {
			//字符串,看长度
			return ((CharSequence) obj).length() == 0;
		}
		if (obj instanceof Collection<?>) {
			//集合,看长度
			return ((Collection<?>) obj).isEmpty();
		}
		if (obj instanceof Map<?, ?>) {
			//图,看长度
			return ((Map<?, ?>) obj).isEmpty();
		}
		//POJO
		Map<String, Object> fields = RefUtils.getFieldValues(obj, STATIC, FINAL, PUBLIC);
		for (Object value : fields.values()) {
			if (!isEmpty(value)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断Map是否为空
	 *
	 * @param map map
	 * @return 是否为空
	 */
	public static boolean isEmpty(Map<?, ?> map) {
		return isNull(map) || map.isEmpty();
	}

	/**
	 * 判断Collection是否为空
	 *
	 * @param collection collection
	 * @return 是否为空
	 */
	public static boolean isEmpty(Collection<?> collection) {
		return isNull(collection) || collection.isEmpty();
	}

	/**
	 * 判断数值是否为空
	 *
	 * @param number 数值
	 * @return 是否为空
	 */
	public static boolean isEmpty(Number number) {
		return isNull(number);
	}

	/**
	 * 判断字符串是否为空
	 *
	 * @param charSequence 字符串
	 * @return 是否为空
	 */
	public static boolean isEmpty(CharSequence charSequence) {
		return isNull(charSequence) || charSequence.length() == 0;
	}

	/**
	 * 判断obj是否为基本类型
	 *
	 * @param obj 对象
	 * @return 结果
	 */
	public static boolean isBaseType(@NotNull Object obj) {
		Class<?> clazz = obj.getClass();
		return Integer.class.equals(clazz) || Byte.class.equals(clazz)
				|| Long.class.equals(clazz) || Double.class.equals(clazz)
				|| Float.class.equals(clazz) || Character.class.equals(clazz)
				|| Short.class.equals(clazz) || Boolean.class.equals(clazz);
	}

	/**
	 * 判断是否为基本类型的默认值
	 *
	 * @param obj 对象
	 * @return 结果
	 */
	public static boolean isBaseDefaultValue(@NotNull Object obj) {
		Class<?> clazz = obj.getClass();
		if (clazz.equals(Integer.class)) {
			return (int) obj == 0;
		} else if (clazz.equals(Byte.class)) {
			return (byte) obj == 0;
		} else if (clazz.equals(Long.class)) {
			return (long) obj == 0L;
		} else if (clazz.equals(Double.class)) {
			return (double) obj == 0.0d;
		} else if (clazz.equals(Float.class)) {
			return (float) obj == 0.0f;
		} else if (clazz.equals(Character.class)) {
			return (char) obj == '\u0000';
		} else if (clazz.equals(Short.class)) {
			return (short) obj == 0;
		} else if (clazz.equals(Boolean.class)) {
			return !((boolean) obj);
		}
		return false;
	}

	/**
	 * 获取新实例
	 *
	 * @param clazz 类型对象
	 * @param <T>   类型
	 * @return 实例对象
	 * @throws ReflectiveOperationException 反射异常
	 * @see RefUtils#newInstance(Class) 反射工具类
	 */
	public static <T> @NotNull T newInstance(@NotNull Class<T> clazz) throws ReflectiveOperationException {
		return RefUtils.newInstance(clazz);
	}

	/**
	 * 扩展字段
	 *
	 * @param obj    对象
	 * @param extObj 扩展字段
	 * @return map对象
	 * @throws IllegalAccessException 字段非法访问异常
	 * @see RefUtils#getFieldValues(Object) 反射工具类
	 */
	public static @NotNull Map<String, Object> extend(Object obj, Map<String, Object> extObj) throws IllegalAccessException {
		extObj.putAll(RefUtils.getFieldValues(obj));
		return extObj;
	}

	/**
	 * 将对象转化为map
	 *
	 * @param obj 对象
	 * @return map结果
	 * @see RefUtils#getFieldValues(Object) 反射工具类
	 */
	public static @NotNull Map<String, Object> convert(Object obj) throws IllegalAccessException {
		return RefUtils.getFieldValues(obj);
	}

	/**
	 * 将map转化为对象
	 *
	 * @param map   map
	 * @param clazz 对象类型
	 * @param <T>   类型
	 * @return 结果对象
	 */
	public static <T> @NotNull T convert(@NotNull Map<String, Object> map, @NotNull Class<T> clazz) throws ReflectiveOperationException {
		T t = newInstance(clazz);
		RefUtils.setFieldValues(t, map);
		return t;
	}


	/**
	 * 获取对象字段值
	 *
	 * @param obj 对象
	 * @param key 字段
	 * @return 值
	 * @see RefUtils#getFieldValue(Object, String)
	 */
	@Deprecated
	public static Object getFieldValue(@NotNull Object obj, String key) throws NoSuchFieldException, IllegalAccessException {
		return RefUtils.getFieldValue(obj, key);
	}

	/**
	 * 获取对象字段值
	 *
	 * @param obj 对象
	 * @return 值
	 * @see RefUtils#getFieldValues(Object)
	 */
	@Deprecated
	public static @NotNull Map<String, Object> getFieldValues(@NotNull Object obj) throws IllegalAccessException {
		return RefUtils.getFieldValues(obj);
	}

	/**
	 * 获取对象字段值
	 *
	 * @param obj         对象
	 * @param excludeMods 排除不获取指定修饰符的字段
	 * @return 值
	 * @see RefUtils#getFieldValues(Object, Integer...)
	 */
	@Deprecated
	public static @NotNull Map<String, Object> getFieldValues(@NotNull Object obj, Integer... excludeMods) throws IllegalAccessException {
		return RefUtils.getFieldValues(obj, excludeMods);
	}

	/**
	 * 获取对象字段值
	 *
	 * @param obj               对象
	 * @param excludeFieldNames 排除不获取指定名称的字段
	 * @return 值
	 * @see RefUtils#getFieldValues(Object, String...)
	 */
	@Deprecated
	public static @NotNull Map<String, Object> getFieldValues(@NotNull Object obj, String... excludeFieldNames) throws IllegalAccessException {
		return RefUtils.getFieldValues(obj, excludeFieldNames);
	}

	/**
	 * 给对象的属性值赋值
	 * 注: 暂无反射删除方法
	 *
	 * @param key   字段名
	 * @param value 字段值
	 * @param obj   操作对象
	 * @see RefUtils#setFieldValue(Object, String, Object)
	 */
	@Deprecated
	public static void setFieldValue(@NotNull Object obj, String key, Object value) throws NoSuchFieldException, IllegalAccessException {
		RefUtils.setFieldValue(obj, key, value);
	}

	/**
	 * 给对象的属性值赋值
	 * 注: 暂无反射删除方法
	 *
	 * @param values 字段值
	 * @param obj    操作对象
	 * @see RefUtils#setFieldValues(Object, Map)
	 */
	@Deprecated
	public static void setFieldValues(@NotNull Object obj, Map<String, Object> values) throws IllegalAccessException {
		RefUtils.setFieldValues(obj, values);
	}

	/**
	 * 获取字段名称
	 *
	 * @param obj 对象
	 * @return 结果
	 * @see RefUtils#getFieldNames(Object)
	 */
	@Deprecated
	public static @NotNull String[] getFieldNames(@NotNull Object obj) {
		return RefUtils.getFieldNames(obj);
	}

	/**
	 * 获取字段名称
	 *
	 * @param obj 对象
	 * @return 结果
	 * @see RefUtils#getFieldNames(Class)
	 */
	@Deprecated
	public static @NotNull String[] getFieldNames(@NotNull Class<?> obj) {
		return RefUtils.getFieldNames(obj);
	}

	/**
	 * 获取对象字段名
	 *
	 * @param obj 对象
	 * @param var 分隔符
	 * @return 字符串
	 * @see RefUtils#getFieldNameStr(Class, CharSequence)
	 */
	@Deprecated
	public static @NotNull String getFieldNames(@NotNull Class<?> obj, CharSequence var) {
		return RefUtils.getFieldNameStr(obj, var);
	}

	/**
	 * 获取对象字段名
	 *
	 * @param obj 对象
	 * @param var 分隔符
	 * @return 字符串
	 * @see RefUtils#getFieldNameStr(Object, CharSequence)
	 */
	@Deprecated
	public static @NotNull String getFieldNames(@NotNull Object obj, CharSequence var) {
		return RefUtils.getFieldNameStr(obj, var);
	}

	/**
	 * 获取对象字段名
	 *
	 * @param obj 对象
	 * @return 字符串
	 * @see RefUtils#getFieldNameAndType(Object)
	 */
	@Deprecated
	public static @NotNull Map<String, Class<?>> getFieldNameAndType(@NotNull Object obj) {
		return RefUtils.getFieldNameAndType(obj);
	}

	@Deprecated
	public static @NotNull List<Map<String, Object>> extend(List<Object> objs, List<Map<String, Object>> extObjs) throws IllegalAccessException {
		List<Map<String, Object>> result = new ArrayList<>();
		if (isNull(objs) || objs.isEmpty()) {
			return result;
		}
		int size = objs.size();
		Object obj;
		Map<String, Object> extObj;
		for (int i = 0; i < size; i++) {
			obj = objs.get(i);
			extObj = extObjs.get(i);
			result.add(extend(obj, extObj));
		}
		return result;
	}

	@Deprecated
	public static @NotNull List<Map<String, Object>> extend(Map<Object, Map<String, Object>> maps) throws IllegalAccessException {
		List<Map<String, Object>> result = new ArrayList<>();
		if (isNull(maps) || maps.keySet().isEmpty()) {
			return result;
		}
		for (Map.Entry<Object, Map<String, Object>> entry : maps.entrySet()) {
			result.add(extend(entry.getKey(), entry.getValue()));
		}
		return result;
	}
}
