package com.wobangkj.utils;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 列表转化为Map,通过id和对象映射
 *
 * @author cliod
 * @since 2019/7/26
 * package : com.wobangkj.util
 */
public class ListUtils {

    public static <T> @NotNull Map<String, T> convert(@NotNull Collection<T> list, @NotNull String key) throws NoSuchFieldException, IllegalAccessException {
        return new HashMap<String, T>(list.size() * 4 / 3 + 1) {{
            Object obj;
            for (T t : list) {
                obj = BeanUtils.getFieldValue(t, key);
                if (Objects.isNull(obj)) {
                    continue;
                }
                put(obj.toString(), t);
            }
        }};
    }

    public static <T> @NotNull Map<String, T> convert(@NotNull Collection<T> list) throws NoSuchFieldException, IllegalAccessException {
        return convert(list, "id");
    }

    public static <T> @NotNull Map<String, Object> convert(@NotNull Collection<T> list, @NotNull String keyName, String valueName) throws NoSuchFieldException, IllegalAccessException {
        return new HashMap<String, Object>() {{
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

    public static @NotNull Map<String, Long> statistics(@NotNull Collection<?> list, String key) throws NoSuchFieldException,
            IllegalAccessException {
        return new HashMap<String, Long>() {{
            Object obj;
            Long temp;
            for (Object t : list) {
                obj = BeanUtils.getFieldValue(t, key);
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
