package com.wobangkj.utils;

import com.wobangkj.api.EnumMsg;
import com.wobangkj.exception.NullObjectException;
import org.apache.commons.collections4.CollectionUtils;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static java.lang.reflect.Modifier.*;

/**
 * Bean工具类
 *
 * @author cliod
 * @date 19-7-18
 * @desc util
 */
public class BeanUtils {
    private BeanUtils() {
        throw new UnsupportedOperationException();
    }

    /**
     * 验证对象是否为空
     *
     * @param obj 对象
     * @param e   为空时抛出的异常
     * @param <T> 对象类型
     */
    public static <T> void verifyNonNull(T obj, @NotNull EnumMsg e) throws IllegalAccessException {
        if (isNull(obj) || isEmpty(obj)) {
            throw new NullObjectException(e);
        }
    }

    /**
     * 请求对象是否为空,空抛出异常
     *
     * @param obj 对象
     * @param e   异常
     * @param <T> 对象类型
     * @return 对象
     */
    public static <T> T requireNonNull(T obj, @NotNull EnumMsg e) {
        if (isNull(obj)) {
            throw new NullObjectException(e);
        }
        return obj;
    }

    /**
     * 请求对象是否为空,空抛出异常
     *
     * @param obj 对象
     * @param <T> 对象类型
     * @return 对象
     */
    public static <T, R extends Enum<R>> T requireNonNull(T obj) {
        return requireNonNull(obj, new EnumMsg() {
            @Override
            public Integer getCode() {
                return 0;
            }

            @Override
            public String getMsg() {
                return "未知异常";
            }
        });
    }

    /**
     * 对象是否为空
     *
     * @param obj 对象
     * @param <T> 对象类型
     * @return 是否为空
     */
    public static <T> boolean isNull(T obj) {
        return Objects.isNull(obj);
    }

    /**
     * 判断对象以及内容是否为空
     * <li>只要有一个不为空即不为空</li>
     *
     * @param obj 对象
     * @return 是否为空
     */
    public static boolean isEmpty(Object obj) throws IllegalAccessException {
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
        if (obj instanceof CharSequence) {
            //字符串,看长度
            return ((CharSequence) obj).length() == 0;
        }
        if (obj instanceof Enum) {
            //不为null的枚举--> not empty
            return false;
        }
        if (obj instanceof Date || obj instanceof LocalDateTime || obj instanceof LocalTime || obj instanceof LocalDate) {
            //时间不为空
            return false;
        }
        Class<?> clazz = obj.getClass();
        if (clazz.isArray()) {
            //数组,看长度
            return Array.getLength(obj) == 0;
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
        Field[] fields = clazz.getDeclaredFields();
        int mod;
        Object inner;
        boolean exclude;
        for (Field field : fields) {
            field.setAccessible(true);
            //判断字段是否为空，并且对象属性中的基本都会转为对象类型来判断
            mod = field.getModifiers();
            //静态不算 //最终不算 //公开不算
            exclude = isStatic(mod) || isFinal(mod) || isPublic(mod);
            if (exclude) {
                continue;
            }
            inner = field.get(obj);
            if (isEmpty(inner)) {
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
     * 判断对象是否相等(对象需要重写 equals() 方法)
     *
     * @param var0 对象1
     * @param var1 对象2
     * @return 是否相等
     */
    public static boolean equals(Object var0, Object var1) {
        return Objects.equals(var0, var1);
    }

    /**
     * 获取对象字段值
     *
     * @param obj 对象
     * @param key 字段
     * @return 值
     */
    public static Object getFieldValue(@NotNull Object obj, String key) throws NoSuchFieldException, IllegalAccessException {
        Field field = obj.getClass().getDeclaredField(key);
        field.setAccessible(true);
        return field.get(obj);
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
    public static void setFieldValue(@NotNull Object obj, String key, Object value) throws NoSuchFieldException,
            IllegalAccessException {
        Field field = obj.getClass().getDeclaredField(key);
        //设置对象的访问权限，保证对private的属性的访问
        field.setAccessible(true);
        field.set(obj, value);
    }

    @NotNull
    public static String[] getFieldNames(@NotNull Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        String[] result = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            result[i] = fields[i].getName();
        }
        return result;
    }

    @NotNull
    public static String[] getFieldNames(@NotNull Class<?> obj) {
        Field[] fields = obj.getDeclaredFields();
        String[] result = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            result[i] = fields[i].getName();
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
    public static String getFieldNames(@NotNull Class<?> obj, CharSequence var) {
        Field[] fields = obj.getDeclaredFields();
        StringBuilder sb = new StringBuilder();
        for (Field field : fields) {
            sb.append(field.getName());
            sb.append(var);
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    /**
     * 获取对象字段名
     *
     * @param obj 对象
     * @param var 分隔符
     * @return 字符串
     */
    @NotNull
    public static String getFieldNames(@NotNull Object obj, CharSequence var) {
        Field[] fields = obj.getClass().getDeclaredFields();
        StringBuilder sb = new StringBuilder();
        for (Field field : fields) {
            sb.append(field.getName());
            sb.append(var);
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    @NotNull
    public static Map<String, Class<?>> getFieldNameAndType(@NotNull Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        Map<String, Class<?>> map = new HashMap<>();
        for (Field field : fields) {
            map.put(field.getName(), field.getType());
        }
        return map;
    }

    @NotNull
    public static <T> T newInstance(@NotNull Class<T> clazz) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        return clazz.getConstructor().newInstance();
    }

    @NotNull
    @SuppressWarnings("unchecked")
    public static Map<String, Object> extend(Object obj, Map<String, Object> extObj)
            throws NoSuchFieldException, IllegalAccessException {
        Map<String, Object> result;
        if (obj instanceof Map) {
            result = (Map<String, Object>) obj;
            result.putAll(extObj);
            return result;
        }
        result = new HashMap<>(16);
        String[] titles = getFieldNames(obj);
        for (String title : titles) {
            result.put(title, getFieldValue(obj, title));
            result.putAll(extObj);
            result.remove("serialVersionUID");
        }
        return result;
    }

    @NotNull
    public static List<Map<String, Object>> extend(List<Object> objs, List<Map<String, Object>> extObjs)
            throws NoSuchFieldException, IllegalAccessException {
        List<Map<String, Object>> result = new ArrayList<>();
        if (CollectionUtils.isEmpty(objs)) {
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

    @NotNull
    public static List<Map<String, Object>> extend(Map<Object, Map<String, Object>> maps)
            throws NoSuchFieldException, IllegalAccessException {
        List<Map<String, Object>> result = new ArrayList<>();
        if (isNull(maps) || CollectionUtils.isEmpty(maps.keySet())) {
            return result;
        }
        for (Map.Entry<Object, Map<String, Object>> entry : maps.entrySet()) {
            result.add(extend(entry.getKey(), entry.getValue()));
        }
        return result;
    }

    @NotNull
    @SuppressWarnings("unchecked")
    public static Map<String, Object> convert(Object obj) throws NoSuchFieldException, IllegalAccessException {
        if (obj instanceof Map) {
            return (Map<String, Object>) obj;
        }
        String[] titles = getFieldNames(obj);
        Map<String, Object> result = new HashMap<>(titles.length * 4 / 3 + 1);
        for (String title : titles) {
            result.put(title, getFieldValue(obj, title));
        }
        result.remove("serialVersionUID");
        return result;
    }

    @NotNull
    public static <T> T convert(@NotNull Map<String, Object> map, @NotNull Class<T> clazz)
            throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException,
            InvocationTargetException, InstantiationException {
        T t = clazz.getConstructor().newInstance();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            setFieldValue(t, entry.getKey(), entry.getValue());
        }
        return t;
    }
}
