package com.wobangkj.utils;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.function.Function;

/**
 * 反射工具类
 *
 * @author cliod
 * @since 8/21/20 3:15 PM
 */
public class RefUtils {

	private static Function<String, String> covert = (s) -> s;

	private RefUtils() {
	}

	public static Function<String, String> getCovert() {
		return covert;
	}

	public static void setCovert(Function<String, String> covert) {
		RefUtils.covert = covert;
	}

	/**
	 * 获取对象字段值
	 *
	 * @param obj       对象
	 * @param fieldName 字段
	 * @return 值
	 */
	public static Object getFieldValue(@NotNull Object obj, String fieldName) throws NoSuchFieldException, IllegalAccessException {
		Field field = obj.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		return field.get(obj);
	}

	public static <V> V getFieldValue(@NotNull Object obj, String fieldName, Class<V> type) throws IllegalAccessException, NoSuchFieldException {
		Field field = obj.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		if (!field.getType().equals(type)) {
			return null;
		} else {
			@SuppressWarnings("unchecked")
			V v = (V) field.get(obj);
			return v;
		}
	}

	/**
	 * 获取对象字段值
	 *
	 * @param obj 对象
	 * @return 值
	 */
	public static @NotNull Map<String, Object> getFieldValues(@NotNull Object obj) throws IllegalAccessException {
		return getFieldValues(obj, Modifier.STATIC, Modifier.FINAL);
	}

	/**
	 * 获取对象字段值
	 *
	 * @param obj         对象
	 * @param excludeMods 排除不获取指定修饰符的字段
	 * @return 值
	 */
	public static @NotNull Map<String, Object> getFieldValues(@NotNull Object obj, Integer... excludeMods) throws IllegalAccessException {
		return getFieldValues(obj, Arrays.asList(excludeMods), new ArrayList<>());
	}

	/**
	 * 获取对象字段值
	 *
	 * @param obj               对象
	 * @param excludeFieldNames 排除不获取指定名称的字段
	 * @return 值
	 */
	public static @NotNull Map<String, Object> getFieldValues(@NotNull Object obj, String... excludeFieldNames) throws IllegalAccessException {
		return getFieldValues(obj, Arrays.asList(excludeFieldNames));
	}

	/**
	 * 获取对象字段值
	 *
	 * @param obj               对象
	 * @param excludeFieldNames 排除不获取指定名称的字段
	 * @return 值
	 */
	public static @NotNull Map<String, Object> getFieldValues(@NotNull Object obj, List<String> excludeFieldNames) throws IllegalAccessException {
		return getFieldValues(obj, Collections.singletonList(Modifier.STATIC), excludeFieldNames);
	}

	/**
	 * 获取对象字段值
	 *
	 * @param obj               对象
	 * @param excludeFieldNames 排除不获取指定名称的字段
	 * @return 值
	 */
	public static @NotNull Map<String, Object> getFieldValues(@NotNull Object obj, List<Integer> excludeMods, List<String> excludeFieldNames) throws IllegalAccessException {
		Map<String, Object> map = new HashMap<>();
		Field[] fields = obj.getClass().getDeclaredFields();
		boolean mods = excludeMods.isEmpty();
		boolean names = excludeFieldNames.isEmpty();
		boolean exclude;
		for (Field field : fields) {
			exclude = !mods && excludeMods.contains(field.getModifiers());
			exclude = exclude || !names
					&& (excludeFieldNames.contains(field.getName()) || excludeFieldNames.contains(covert.apply(field.getName())));
			if (exclude) {
				continue;
			}
			field.setAccessible(true);
			map.put(covert.apply(field.getName()), field.get(obj));
		}
		return map;
	}

	/**
	 * 给对象的属性值赋值
	 * 注: 暂无反射删除方法
	 *
	 * @param key   字段名
	 * @param value 字段值
	 * @param obj   操作对象
	 *              //     * @return 是否成功赋值
	 */
	public static void setFieldValue(@NotNull Object obj, String key, Object value) throws NoSuchFieldException, IllegalAccessException {
		Field field = obj.getClass().getDeclaredField(key);
		//设置对象的访问权限，保证对private的属性的访问
		field.setAccessible(true);
		field.set(obj, value);
	}

	/**
	 * 给对象的属性值赋值
	 * 注: 暂无反射删除方法
	 *
	 * @param values 字段值
	 * @param obj    操作对象
	 */
	public static void setFieldValues(@NotNull Object obj, Map<String, Object> values) throws IllegalAccessException {
		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field field : fields) {
			if (values.containsKey(field.getName())) {
				//设置对象的访问权限，保证对private的属性的访问
				field.setAccessible(true);
				field.set(obj, values.get(field.getName()));
			}
		}
	}

	/**
	 * 获取字段名称
	 *
	 * @param obj 对象
	 * @return 结果
	 */
	@NotNull
	public static String[] getFieldNames(@NotNull Object obj) {
		return getFieldNames(obj.getClass(), Modifier.STATIC, Modifier.FINAL).toArray(new String[0]);
	}

	/**
	 * 获取字段名称
	 *
	 * @param obj 对象类
	 * @return 结果
	 */
	@NotNull
	public static String[] getFieldNames(@NotNull Class<?> obj) {
		return getFieldNames(obj, Modifier.STATIC, Modifier.FINAL).toArray(new String[0]);
	}

	/**
	 * 获取字段名称
	 *
	 * @param obj         对象
	 * @param excludeMods 忽略指定的修饰符
	 * @return 结果
	 */
	@NotNull
	public static List<String> getFieldNames(@NotNull Class<?> obj, Integer... excludeMods) {
		return getFieldNames(obj, Arrays.asList(excludeMods));
	}

	/**
	 * 获取字段名称
	 *
	 * @param obj               对象
	 * @param excludeFieldNames 忽略指定字段
	 * @return 结果
	 */
	@NotNull
	public static List<String> getFieldNames(@NotNull Class<?> obj, String... excludeFieldNames) {
		return getFieldNames(obj, Arrays.asList(excludeFieldNames));
	}

	/**
	 * 获取字段名称
	 *
	 * @param obj         对象
	 * @param excludeMods 忽略指定的修饰符
	 * @return 结果
	 */
	@NotNull
	public static List<String> getFieldNames(@NotNull Class<?> obj, Collection<Integer> excludeMods) {
		Field[] fields = obj.getDeclaredFields();
		List<String> result = new ArrayList<>();
		for (Field field : fields) {
			if (excludeMods.contains(field.getModifiers())) {
				continue;
			}
			result.add(covert.apply(field.getName()));
		}
		return result;
	}

	/**
	 * 获取字段名称
	 *
	 * @param obj               对象
	 * @param excludeFieldNames 忽略指定字段
	 * @return 结果
	 */
	@NotNull
	public static List<String> getFieldNames(@NotNull Class<?> obj, List<String> excludeFieldNames) {
		Field[] fields = obj.getDeclaredFields();
		List<String> result = new ArrayList<>();
		for (Field field : fields) {
			if (excludeFieldNames.contains(field.getName()) || excludeFieldNames.contains(covert.apply(field.getName()))) {
				continue;
			}
			result.add(covert.apply(field.getName()));
		}
		return result;
	}

	/**
	 * 获取对象字段名
	 *
	 * @param obj 对象
	 * @param var 分隔符
	 * @return 字符串
	 */
	@NotNull
	public static String getFieldNameStr(@NotNull Object obj, CharSequence var) {
		return String.join(var, getFieldNames(obj));
	}

	/**
	 * 获取对象字段名
	 *
	 * @param obj 对象
	 * @param var 分隔符
	 * @return 字符串
	 */
	@NotNull
	public static String getFieldNameStr(@NotNull Class<?> obj, CharSequence var) {
		return String.join(var, getFieldNames(obj));
	}

	/**
	 * 获取对象字段名
	 *
	 * @param obj         对象
	 * @param var         分隔符
	 * @param excludeMods 忽略指定的修饰符
	 * @return 字符串
	 */
	@NotNull
	public static String getFieldNameStr(@NotNull Class<?> obj, CharSequence var, Integer... excludeMods) {
		return String.join(var, getFieldNames(obj, excludeMods));
	}

	/**
	 * 获取对象字段名
	 *
	 * @param obj         对象
	 * @param var         分隔符
	 * @param excludeMods 忽略指定的修饰符
	 * @return 字符串
	 */
	@NotNull
	public static String getFieldNameStr(@NotNull Class<?> obj, CharSequence var, Collection<Integer> excludeMods) {
		return String.join(var, getFieldNames(obj, excludeMods));
	}

	/**
	 * 获取对象字段名
	 *
	 * @param obj               对象
	 * @param var               分隔符
	 * @param excludeFieldNames 忽略指定字段
	 * @return 字符串
	 */
	@NotNull
	public static String getFieldNameStr(@NotNull Class<?> obj, CharSequence var, String... excludeFieldNames) {
		return String.join(var, getFieldNames(obj, excludeFieldNames));
	}

	/**
	 * 获取对象字段名
	 *
	 * @param obj               对象
	 * @param var               分隔符
	 * @param excludeFieldNames 忽略指定字段
	 * @return 字符串
	 */
	@NotNull
	public static String getFieldNameStr(@NotNull Class<?> obj, CharSequence var, List<String> excludeFieldNames) {
		return String.join(var, getFieldNames(obj, excludeFieldNames));
	}

	/**
	 * 获取字段名和类型
	 *
	 * @param obj 对象
	 * @return 结果
	 */
	@NotNull
	public static Map<String, Class<?>> getFieldNameAndType(@NotNull Object obj) {
		return getFieldNameAndType(obj.getClass(), Modifier.STATIC, Modifier.FINAL);
	}

	/**
	 * 获取字段名和类型
	 *
	 * @param obj 对象
	 * @return 结果
	 */
	@NotNull
	public static Map<String, Class<?>> getFieldNameAndType(@NotNull Class<?> obj) {
		return getFieldNameAndType(obj, Modifier.STATIC, Modifier.FINAL);
	}

	/**
	 * 获取字段名和类型
	 *
	 * @param obj         对象
	 * @param excludeMods 忽略指定的修饰符
	 * @return 结果
	 */
	@NotNull
	public static Map<String, Class<?>> getFieldNameAndType(@NotNull Class<?> obj, Integer... excludeMods) {
		return getFieldNameAndType(obj, Arrays.asList(excludeMods));
	}

	/**
	 * 获取字段名和类型
	 *
	 * @param obj         对象
	 * @param excludeMods 忽略指定的修饰符
	 * @return 结果
	 */
	@NotNull
	public static Map<String, Class<?>> getFieldNameAndType(@NotNull Class<?> obj, Collection<Integer> excludeMods) {
		Field[] fields = obj.getDeclaredFields();
		Map<String, Class<?>> map = new HashMap<>();
		for (Field field : fields) {
			if (excludeMods.contains(field.getModifiers())) {
				continue;
			}
			map.put(covert.apply(field.getName()), field.getType());
		}
		return map;
	}

	/**
	 * 获取字段名和类型
	 *
	 * @param obj               对象
	 * @param excludeFieldNames 忽略指定字段
	 * @return 结果
	 */
	@NotNull
	public static Map<String, Class<?>> getFieldNameAndType(@NotNull Class<?> obj, String... excludeFieldNames) {
		return getFieldNameAndType(obj, Arrays.asList(excludeFieldNames));
	}

	/**
	 * 获取字段名和类型
	 *
	 * @param obj               对象
	 * @param excludeFieldNames 忽略指定字段
	 * @return 结果
	 */
	@NotNull
	public static Map<String, Class<?>> getFieldNameAndType(@NotNull Class<?> obj, List<String> excludeFieldNames) {
		Field[] fields = obj.getDeclaredFields();
		Map<String, Class<?>> map = new HashMap<>();
		for (Field field : fields) {
			if (excludeFieldNames.contains(field.getName()) || excludeFieldNames.contains(covert.apply(field.getName()))) {
				continue;
			}
			map.put(covert.apply(field.getName()), field.getType());
		}
		return map;
	}

	/**
	 * 构建新对象
	 *
	 * @param clazz 对象类型
	 * @param <T>   类型
	 * @return 结果对象
	 */
	@NotNull
	public static <T> T newInstance(@NotNull Class<T> clazz) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
		return clazz.getConstructor().newInstance();
	}

	/**
	 * 构建新对象
	 *
	 * @param className 对象名称
	 * @param <T>       类型
	 * @return 结果对象
	 */
	@NotNull
	public static <T> T newInstance(@NotNull String className) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException {
		@SuppressWarnings("unchecked")
		Class<T> clazz = (Class<T>) Class.forName(className);
		return newInstance(clazz);
	}

}
