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
 * @see CovUtils
 * @since 2019/7/26
 * package : com.wobangkj.util
 */
@Deprecated
public class ListUtils {
	//因子
	private static final Function<Integer, Integer> factor = size -> size * 4 / 3 + 1;

	/**
	 * obj列表根据指定项进行map转化
	 *
	 * @param list 列表对象
	 * @param <T>  类型
	 * @return map对象
	 * @throws NoSuchFieldException   未知field异常
	 * @throws IllegalAccessException 非法访问异常
	 */
	public static <T> @NotNull Map<String, T> convert(@NotNull Collection<T> list) throws NoSuchFieldException, IllegalAccessException {
		return convert(list, "id");
	}

	/**
	 * obj列表根据指定项进行map转化
	 *
	 * @param list    列表对象
	 * @param keyName map的key在obj的名称
	 * @param <T>     类型
	 * @return map对象
	 * @throws NoSuchFieldException   未知field异常
	 * @throws IllegalAccessException 非法访问异常
	 */
	public static <T> @NotNull Map<String, T> convert(@NotNull Collection<T> list, @NotNull String keyName) throws NoSuchFieldException, IllegalAccessException {
		return new HashMap<String, T>(factor.apply(list.size())) {{
			Object obj;
			for (T t : list) {
				obj = BeanUtils.getFieldValue(t, keyName);
				if (Objects.isNull(obj)) {
					continue;
				}
				put(obj.toString(), t);
			}
		}};
	}

	/**
	 * obj列表根据指定项进行map转化
	 *
	 * @param list      列表对象
	 * @param keyName   map的key在obj的名称
	 * @param valueName map的value在obj的名称
	 * @param <T>       类型
	 * @return map对象
	 * @throws NoSuchFieldException   未知field异常
	 * @throws IllegalAccessException 非法访问异常
	 */
	public static <T> @NotNull Map<String, Object> convert(@NotNull Collection<T> list, @NotNull String keyName, String valueName) throws NoSuchFieldException, IllegalAccessException {
		return new HashMap<String, Object>(factor.apply(list.size())) {{
			Object obj;
			for (T t : list) {
				obj = BeanUtils.getFieldValue(t, keyName);
				if (Objects.isNull(obj)) {
					continue;
				}
				put(obj.toString(), BeanUtils.getFieldValue(t, valueName));
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
	public static @NotNull Map<String, Long> statistics(@NotNull Collection<?> list, String keyName) throws NoSuchFieldException, IllegalAccessException {
		return new HashMap<String, Long>(factor.apply(list.size())) {{
			Object obj;
			Long temp;
			for (Object t : list) {
				obj = BeanUtils.getFieldValue(t, keyName);
				if (Objects.isNull(obj)) {
					continue;
				}
				temp = get(obj.toString());
				if (Objects.isNull(temp)) {
					put(obj.toString(), 1L);
				} else {
					temp += 1L;
					put(obj.toString(), temp);
				}
			}
		}};
	}
}
