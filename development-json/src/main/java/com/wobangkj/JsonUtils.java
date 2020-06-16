package com.wobangkj;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * json 工具
 *
 * @author cliod
 */
public class JsonUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * json转javabean
     *
     * @param json  json字符串
     * @param clazz 类
     * @param <T>   类型
     * @return java对象
     */
    @SneakyThrows
    public static <T> T fromJson(String json, Class<T> clazz) {
        return objectMapper.readValue(json, clazz);
    }

    /**
     * json转java对象(Map)
     *
     * @param json json字符串
     * @return Map对象
     */
    @SneakyThrows
    public static Object toObject(String json) {
        return objectMapper.readValue(json, new TypeReference<Map<?, ?>>() {
            @Override
            public Type getType() {
                return Map.class;
            }
        });
    }

    /**
     * json转java列表(List)
     *
     * @param <T>  类型
     * @param json json字符串
     * @return List::Map对象
     */
    @SneakyThrows
    public static <T> List<T> toList(String json) {
        return objectMapper.readValue(json, new TypeReference<List<T>>() {
            @Override
            public Type getType() {
                return List.class;
            }
        });
    }

    /**
     * java对象转json字符串
     *
     * @param obj 对象
     * @return 字符串
     */
    @SneakyThrows
    public static String toJson(Object obj) {
        return objectMapper.writeValueAsString(obj);
    }
}
