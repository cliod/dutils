package com.wobangkj.utils;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/**
 * 列表转化为Map,通过id和对象映射
 *
 * @author cliod
 * @since 2020/07/26
 */
public class CovUtils {
	/**
	 * 因子
	 */
	private static final Function<Integer, Integer> FACTOR = size -> size * 4 / 3 + 1;

	/**
	 * obj列表根据指定项进行map转化
	 *
	 * @param list 列表对象
	 * @param <K>  类型
	 * @param <V>  类型
	 * @return map对象
	 * @throws NoSuchFieldException   未知field异常
	 * @throws IllegalAccessException 非法访问异常
	 */
	public static <K, V> @NotNull Map<K, V> convert(@NotNull Collection<V> list) throws NoSuchFieldException, IllegalAccessException {
		return convert(list, "id");
	}

	/**
	 * obj列表根据指定项进行map转化
	 *
	 * @param list    列表对象
	 * @param keyName map的key在obj的名称
	 * @param <K>     类型
	 * @param <V>     类型
	 * @return map对象
	 * @throws NoSuchFieldException   未知field异常
	 * @throws IllegalAccessException 非法访问异常
	 */
	@SuppressWarnings("unchecked")
	public static <K, V> @NotNull Map<K, V> convert(@NotNull Collection<V> list, @NotNull String keyName) throws NoSuchFieldException, IllegalAccessException {
		return new HashMap<K, V>(FACTOR.apply(list.size())) {{
			K key;
			for (V obj : list) {
				key = (K) RefUtils.getFieldValue(obj, keyName);
				if (Objects.nonNull(key)) {
					put(key, obj);
				}
			}
		}};
	}

	/**
	 * obj列表根据指定项进行map转化
	 *
	 * @param list      列表对象
	 * @param keyName   map的key在obj的名称
	 * @param valueName map的value在obj的名称
	 * @return map对象
	 * @throws NoSuchFieldException   未知field异常
	 * @throws IllegalAccessException 非法访问异常
	 */
	@SuppressWarnings("unchecked")
	public static <K, T, V> @NotNull Map<K, V> convert(@NotNull Collection<T> list, @NotNull String keyName, String valueName) throws NoSuchFieldException, IllegalAccessException {
		return new HashMap<K, V>(FACTOR.apply(list.size())) {{
			K obj;
			for (Object t : list) {
				obj = (K) RefUtils.getFieldValue(t, keyName);
				if (Objects.nonNull(obj)) {put(obj, (V) RefUtils.getFieldValue(t, valueName));}
			}
		}};
	}

	/**
	 * 针对列表的对象某一个field的值进行统计
	 *
	 * @param list    列表对象
	 * @param keyName 统计的field名称
	 * @return 数量map
	 * @throws NoSuchFieldException   未知field异常
	 * @throws IllegalAccessException 非法访问异常
	 */
	@SuppressWarnings("unchecked")
	public static <K, T> @NotNull Map<K, Long> statistics(@NotNull Collection<T> list, String keyName) throws NoSuchFieldException, IllegalAccessException {
		return new HashMap<K, Long>(FACTOR.apply(list.size())) {{
			K obj;
			Long temp;
			for (Object t : list) {
				obj = (K) RefUtils.getFieldValue(t, keyName);
				if (Objects.isNull(obj)) {
					continue;
				}
				temp = get(obj);
				if (Objects.isNull(temp)) {
					put(obj, 1L);
				} else {
					temp += 1L;
					put(obj, temp);
				}
			}
		}};
	}
}
