package com.wobangkj.utils;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
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

	@Deprecated
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
	public static @NotNull Object getFieldValue(@NotNull Object obj, String fieldName) throws NoSuchFieldException, IllegalAccessException {
		Field field = obj.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		return field.get(obj);
	}

	/**
	 * 获取对象字段值
	 *
	 * @param obj       对象
	 * @param fieldName 字段
	 * @return 值
	 */
	public static @NotNull <V> V getFieldValue(@NotNull Object obj, String fieldName, Class<V> type) throws IllegalAccessException, NoSuchFieldException {
		Field field = obj.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		if (!field.getType().isAssignableFrom(type)) {
			throw new IllegalAccessException("类型不匹配");
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
	 * @param annotations 注解
	 * @return 值
	 */
	@SafeVarargs
	public static @NotNull Map<String, Object> getFieldValues(@NotNull Object obj, Class<? extends Annotation>... annotations) throws IllegalAccessException {
		return getFieldValues(obj, Arrays.asList(Modifier.STATIC, Modifier.FINAL), Collections.emptyList(), Arrays.asList(annotations));
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
		return getFieldValues(obj, excludeMods, excludeFieldNames, Collections.emptyList());
	}

	/**
	 * 获取对象字段值
	 *
	 * @param obj               对象
	 * @param excludeFieldNames 排除不获取指定名称的字段
	 * @return 值
	 */
	public static @NotNull Map<String, Object> getFieldValues(@NotNull Object obj, List<Integer> excludeMods, List<String> excludeFieldNames,
	                                                          List<Class<? extends Annotation>> excludeAnnotations) throws IllegalAccessException {
		Field[] fields = obj.getClass().getDeclaredFields();
		Map<String, Object> map = new HashMap<>(fields.length);
		boolean mods = excludeMods.isEmpty();
		boolean ann = excludeAnnotations.isEmpty();
		boolean names = excludeFieldNames.isEmpty();
		boolean exclude = false;
		for (Field field : fields) {
			if (!ann) {
				exclude = containsAnnotation(field, excludeAnnotations);
			}
			if (!mods) {
				exclude = containsExcludeMods(excludeMods, field.getModifiers());
			}
			if (!names) {
				exclude = exclude || (excludeFieldNames.contains(field.getName()) || excludeFieldNames.contains(covert.apply(field.getName())));
			}
			if (!exclude) {
				field.setAccessible(true);
				map.put(covert.apply(field.getName()), field.get(obj));
			}
		}
		return map;
	}

	private static boolean containsExcludeMods(Collection<Integer> excludeMods, int modifiers) {
		for (Integer mod : excludeMods) {
			if ((mod & modifiers) != 0) {
				return true;
			}
		}
		return false;
	}

	private static boolean containsAnnotation(Field field, Collection<Class<? extends Annotation>> annotations) {
		for (Class<? extends Annotation> annotation : annotations) {
			if (field.isAnnotationPresent(annotation)) {
				return true;
			}
		}
		return false;
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
	public static @NotNull String[] getFieldNames(@NotNull Object obj) {
		return getFieldNames(obj.getClass(), Modifier.STATIC, Modifier.FINAL).toArray(new String[0]);
	}

	/**
	 * 获取字段名称
	 *
	 * @param obj 对象类
	 * @return 结果
	 */
	public static @NotNull String[] getFieldNames(@NotNull Class<?> obj) {
		return getFieldNames(obj, Modifier.STATIC, Modifier.FINAL).toArray(new String[0]);
	}

	/**
	 * 获取字段名称
	 *
	 * @param obj         对象
	 * @param excludeMods 忽略指定的修饰符
	 * @return 结果
	 */
	public static @NotNull List<String> getFieldNames(@NotNull Class<?> obj, Integer... excludeMods) {
		return getFieldNames(obj, Arrays.asList(excludeMods));
	}

	/**
	 * 获取字段名称
	 *
	 * @param obj               对象
	 * @param excludeFieldNames 忽略指定字段
	 * @return 结果
	 */
	public static @NotNull List<String> getFieldNames(@NotNull Class<?> obj, String... excludeFieldNames) {
		return getFieldNames(obj, Arrays.asList(excludeFieldNames));
	}

	/**
	 * 获取字段名称
	 *
	 * @param obj               对象
	 * @param excludeFieldNames 忽略指定字段
	 * @return 结果
	 */
	@SafeVarargs
	public static @NotNull List<String> getFieldNames(@NotNull Class<?> obj, Class<? extends Annotation>... excludeFieldNames) {
		return getFieldNames(obj, Collections.emptyList(), Collections.emptyList(), Arrays.asList(excludeFieldNames));
	}

	/**
	 * 获取字段名称
	 *
	 * @param obj         对象
	 * @param excludeMods 忽略指定的修饰符
	 * @return 结果
	 */
	public static @NotNull List<String> getFieldNames(@NotNull Class<?> obj, Collection<Integer> excludeMods) {
		return getFieldNames(obj, excludeMods, Collections.emptyList(), Collections.emptyList());
	}

	/**
	 * 获取字段名称
	 *
	 * @param obj               对象
	 * @param excludeFieldNames 忽略指定字段
	 * @return 结果
	 */
	public static @NotNull List<String> getFieldNames(@NotNull Class<?> obj, List<String> excludeFieldNames) {
		return getFieldNames(obj, Collections.emptyList(), excludeFieldNames, Collections.emptyList());
	}

	/**
	 * 获取字段名称
	 *
	 * @param obj               对象
	 * @param excludeMods       忽略指定的修饰符
	 * @param excludeFieldNames 忽略指定字段
	 * @return 结果
	 */
	public static @NotNull List<String> getFieldNames(@NotNull Class<?> obj, Collection<Integer> excludeMods, Collection<String> excludeFieldNames,
	                                                  Collection<Class<? extends Annotation>> excludeAnnotations) {
		Field[] fields = obj.getDeclaredFields();
		List<String> result = new ArrayList<>(fields.length);
		boolean ann = excludeAnnotations.isEmpty();
		boolean names = excludeFieldNames.isEmpty();
		boolean mods = excludeMods.isEmpty();
		boolean exclude = false;
		for (Field field : fields) {
			if (!names) {
				exclude = exclude || (excludeFieldNames.contains(field.getName()) || excludeFieldNames.contains(covert.apply(field.getName())));
			}
			if (!mods) {
				exclude = containsExcludeMods(excludeMods, field.getModifiers());
			}
			if (!ann) {
				exclude = containsAnnotation(field, excludeAnnotations);
			}
			if (!exclude) {
				result.add(covert.apply(field.getName()));
			}
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
	public static @NotNull String getFieldNameStr(@NotNull Object obj, CharSequence var) {
		return String.join(var, getFieldNames(obj));
	}

	/**
	 * 获取对象字段名
	 *
	 * @param obj 对象
	 * @param var 分隔符
	 * @return 字符串
	 */
	public static @NotNull String getFieldNameStr(@NotNull Class<?> obj, CharSequence var) {
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
	public static @NotNull String getFieldNameStr(@NotNull Class<?> obj, CharSequence var, Integer... excludeMods) {
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
	public static @NotNull String getFieldNameStr(@NotNull Class<?> obj, CharSequence var, Collection<Integer> excludeMods) {
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
	public static @NotNull String getFieldNameStr(@NotNull Class<?> obj, CharSequence var, String... excludeFieldNames) {
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
	public static @NotNull String getFieldNameStr(@NotNull Class<?> obj, CharSequence var, List<String> excludeFieldNames) {
		return String.join(var, getFieldNames(obj, excludeFieldNames));
	}

	/**
	 * 获取字段名和类型
	 *
	 * @param obj 对象
	 * @return 结果
	 */
	public static @NotNull Map<String, Class<?>> getFieldNameAndType(@NotNull Object obj) {
		return getFieldNameAndType(obj.getClass(), Modifier.STATIC, Modifier.FINAL);
	}

	/**
	 * 获取字段名和类型
	 *
	 * @param obj 对象
	 * @return 结果
	 */
	public static @NotNull Map<String, Class<?>> getFieldNameAndType(@NotNull Class<?> obj) {
		return getFieldNameAndType(obj, Modifier.STATIC, Modifier.FINAL);
	}

	/**
	 * 获取字段名和类型
	 *
	 * @param obj         对象
	 * @param excludeMods 忽略指定的修饰符
	 * @return 结果
	 */
	public static @NotNull Map<String, Class<?>> getFieldNameAndType(@NotNull Class<?> obj, Integer... excludeMods) {
		return getFieldNameAndType(obj, Arrays.asList(excludeMods));
	}

	/**
	 * 获取字段名和类型
	 *
	 * @param obj         对象
	 * @param excludeMods 忽略指定的修饰符
	 * @return 结果
	 */
	public static @NotNull Map<String, Class<?>> getFieldNameAndType(@NotNull Class<?> obj, Collection<Integer> excludeMods) {
		Field[] fields = obj.getDeclaredFields();
		Map<String, Class<?>> map = new HashMap<>(fields.length);
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
	public static @NotNull Map<String, Class<?>> getFieldNameAndType(@NotNull Class<?> obj, String... excludeFieldNames) {
		return getFieldNameAndType(obj, Arrays.asList(excludeFieldNames));
	}

	/**
	 * 获取字段名和类型
	 *
	 * @param obj               对象
	 * @param excludeFieldNames 忽略指定字段
	 * @return 结果
	 */
	public static @NotNull Map<String, Class<?>> getFieldNameAndType(@NotNull Class<?> obj, List<String> excludeFieldNames) {
		Field[] fields = obj.getDeclaredFields();
		Map<String, Class<?>> map = new HashMap<>(fields.length);
		boolean names = excludeFieldNames.isEmpty();
		boolean exclude = false;
		for (Field field : fields) {
			if (!names) {
				exclude = excludeFieldNames.contains(field.getName()) || excludeFieldNames.contains(covert.apply(field.getName()));
			}
			if (!exclude) {
				map.put(covert.apply(field.getName()), field.getType());
			}
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
	public static @NotNull <T> T newInstance(@NotNull Class<T> clazz) throws ReflectiveOperationException {
		return clazz.newInstance();
	}

	/**
	 * 构建新对象
	 *
	 * @param className 对象名称
	 * @param <T>       类型
	 * @return 结果对象
	 */
	public static @NotNull <T> T newInstance(@NotNull String className) throws ReflectiveOperationException {
		@SuppressWarnings("unchecked")
		Class<T> clazz = (Class<T>) Class.forName(className);
		return newInstance(clazz);
	}

}
