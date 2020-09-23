package com.wobangkj.utils;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

/**
 * 列表转化为Map,通过id和对象映射
 *
 * @author cliod
 * @see CovUtils List -> Map 转换工具类
 * @since 2019/7/26
 */
@Deprecated
public class List2MapUtils {

	@Deprecated
	public static <T> @NotNull Map<String, T> convert(@NotNull List<T> list) throws NoSuchFieldException, IllegalAccessException {
		return convert(list, "id");
	}

	@Deprecated
	public static <T> @NotNull Map<String, T> convert(@NotNull List<T> list, @NotNull String key) throws NoSuchFieldException, IllegalAccessException {
		return CovUtils.convert(list, key);
	}

	@Deprecated
	public static <T> @NotNull Map<String, Object> convert(@NotNull List<T> list, @NotNull String keyName, String valueName) throws NoSuchFieldException, IllegalAccessException {
		return CovUtils.convert(list, keyName, valueName);
	}

	@Deprecated
	public static @NotNull Map<String, Long> statistics(@NotNull List<?> list, String key) throws NoSuchFieldException, IllegalAccessException {
		return CovUtils.statistics(list, key);
	}
}
