package com.wobangkj.utils;

import com.esotericsoftware.reflectasm.ConstructorAccess;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 对象属性拷贝
 *
 * @author cliod
 * @version 1.0
 * @since 2021-01-15 09:57:59
 */
public class CopierUtils {
	private static final Map<String, BeanCopier> BEAN_COPIER_MAP = new ConcurrentHashMap<>();
	private static final Map<String, ConstructorAccess<?>> CONSTRUCTOR_ACCESS_CACHE = new ConcurrentHashMap<>();
	private static final int MAX_CACHE_SIZE = 512;

	private CopierUtils() {
	}

	/**
	 * 对象属性拷贝
	 *
	 * @param source 来源
	 * @param target 目标
	 */
	public static void copyProperties(Object source, Object target) {
		BeanCopier copier = getBeanCopier(source.getClass(), target.getClass());
		copier.copy(source, target, null);
	}

	/**
	 * 根据类型获取拷贝对象
	 *
	 * @param source      元数据
	 * @param targetClass 目标类型
	 * @param <T>         泛型
	 * @return 目标对象
	 */
	public static <T> T copyProperties(T source, Class<T> targetClass) {
		if (Objects.isNull(source) || Objects.isNull(targetClass)) {
			return null;
		}
		T target = newInstance(targetClass);
		copyProperties(source, target);
		return target;
	}

	/**
	 * 根据类型获取拷贝对象列表
	 *
	 * @param sourceList  元数据
	 * @param targetClass 目标类型
	 * @param <T>         泛型
	 * @return 目标对象列表
	 */
	public static <T> List<T> copyProperties(List<?> sourceList, Class<T> targetClass) {
		if (CollectionUtils.isEmpty(sourceList)) {
			return Collections.emptyList();
		}

		List<T> resultList = new ArrayList<>(sourceList.size());
		for (Object source : sourceList) {
			T target = newInstance(targetClass);
			copyProperties(source, target);
			resultList.add(target);
		}
		return resultList;
	}

	/**
	 * 获取新实例
	 *
	 * @param type 类型对象
	 * @param <T>  类型
	 * @return 实例对象
	 */
	public static <T> T newInstance(Class<T> type) {
		T target;
		ConstructorAccess<T> constructorAccess = getConstructorAccess(type);
		try {
			target = constructorAccess.newInstance();
		} catch (RuntimeException e) {
			try {
				target = type.newInstance();
			} catch (InstantiationException | IllegalAccessException e1) {
				throw new RuntimeException(String.format("Create new instance of %s failed: %s", type, e.getMessage()));
			}
		}
		return target;
	}

	/**
	 * 获取结构体访问器
	 *
	 * @param targetClass 目标对象
	 * @param <T>         泛型
	 * @return 访问器
	 */
	private static <T> ConstructorAccess<T> getConstructorAccess(Class<T> targetClass) {
		@SuppressWarnings("unchecked")
		ConstructorAccess<T> constructorAccess = (ConstructorAccess<T>) CONSTRUCTOR_ACCESS_CACHE.get(targetClass.getName());
		if (constructorAccess != null) {
			return constructorAccess;
		}
		constructorAccess = ConstructorAccess.get(targetClass);
		if (CONSTRUCTOR_ACCESS_CACHE.size() > MAX_CACHE_SIZE) {
			CONSTRUCTOR_ACCESS_CACHE.clear();
		}
		CONSTRUCTOR_ACCESS_CACHE.put(targetClass.getName(), constructorAccess);
//		throw new RuntimeException(String.format("Create new instance of %s failed: %s", targetClass, e.getMessage()));
		return constructorAccess;
	}

	/**
	 * 获取拷贝器
	 *
	 * @param sourceClazz 元对象
	 * @param targetClazz 目标对象
	 * @return 拷贝器
	 */
	private static BeanCopier getBeanCopier(Class<?> sourceClazz, Class<?> targetClazz) {
		String key = generatorKey(sourceClazz, targetClazz);
		BeanCopier copier;
		if (BEAN_COPIER_MAP.containsKey(key)) {
			copier = BEAN_COPIER_MAP.get(key);
		} else {
			copier = BeanCopier.create(sourceClazz, targetClazz, false);
			BEAN_COPIER_MAP.put(key, copier);
		}
		return copier;
	}

	private static String generatorKey(Class<?> sourceClazz, Class<?> targetClazz) {
		return sourceClazz.getName() + "_" + targetClazz.getName();
	}
}
