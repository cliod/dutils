package com.wobangkj.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wobangkj.api.Serializer;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * json 工具
 *
 * @author cliod
 */
public class JsonUtils {
    /**
     * json序列化
     */
    private static ObjectMapper objectMapper = Serializer.getInstance().objectMapper();
    private static GsonBuilder gsonBuilder = new GsonBuilder();
    private static Gson gson = gsonBuilder.create();

    public static @NotNull ObjectMapper getJackson() {
        return objectMapper;
    }

    public static @NotNull Gson getGoogleJson() {
        return gson;
    }

    public static void setObjectMapper(@NotNull ObjectMapper objectMapper) {
        JsonUtils.objectMapper = objectMapper;
    }

    public static void setGsonBuilder(@NotNull GsonBuilder gsonBuilder) {
        JsonUtils.gsonBuilder = gsonBuilder;
        setGson(gsonBuilder.create());
    }

    public static void setGson(@NotNull Gson gson) {
        JsonUtils.gson = gson;
    }

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
    public static <K, V> Map<K, V> toMap(String json) {
        return objectMapper.readValue(json, new TypeReference<Map<K, V>>() {
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
