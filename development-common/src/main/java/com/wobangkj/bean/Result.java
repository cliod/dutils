package com.wobangkj.bean;

import com.wobangkj.JsonUtils;
import com.wobangkj.api.Maps;
import com.wobangkj.api.Session;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * api返回统一封装
 *
 * @author cliod
 * @since 2019/5/20 13:20
 */
public final class Result<T> extends Maps<String, Object> implements Session {

    private static final long serialVersionUID = -1884640212713045469L;

    public Result(int initialCapacity) {
        super(initialCapacity);
    }

    public Result() {
        super();
    }

    public Result(Map<? extends String, ?> m) {
        super(m);
    }

    public static @NotNull Result<Object> of(String k, Object v) {
        Result<Object> map = new Result<>(16);
        map.put(k, v);
        return map;
    }

    /**
     * 代替构造方法
     *
     * @param code code
     * @param msg  msg
     * @param o    T
     * @return result
     */
    public static <T> @NotNull Result<T> of(int code, String msg, T o) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(o);
        return result;
    }

    /**
     * 代替构造方法
     *
     * @param code code
     * @param msg  msg
     * @param o    T
     * @return result
     */
    public static <T> @NotNull Result<T> of(int code, String msg, @NotNull Throwable o) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        result.setErr(o.getMessage());
        return result;
    }

    /**
     * 代替构造方法
     *
     * @param code code
     * @param msg  msg
     * @return result
     */
    public static <T> @NotNull Result<T> of(int code, String msg) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    /**
     * 转成字符串
     *
     * @return 字符串
     */
    @Override
    public @NotNull String toString() {
        return this.toJson();
    }

    /**
     * 转成Json
     *
     * @return Json
     */
    @Override
    public @NotNull String toJson() {
        return JsonUtils.toJson(this);
    }

    public @NotNull Object readResolve() throws Exception {
        return this.getClass().getConstructor().newInstance();
    }

    /**
     * 转成Map对象
     *
     * @return java.util.Map
     * @see java.util.Map
     */
    @Override
    public @NotNull Result<T> toObject() {
        return this;
    }

    public Integer getCode() {
        return (Integer) get("status");
    }

    public void setCode(Integer code) {
        put("code", code);
    }

    @Deprecated
    public @NotNull Boolean getState() {
        return true;
    }

    @Deprecated
    public void setState(Boolean state) {
    }

    public String getMsg() {
        return (String) get("msg");
    }

    public void setMsg(String msg) {
        put("msg", msg);
    }

    public Object getErr() {
        return get("err");
    }

    public void setErr(Object err) {
        put("err", err);
    }

    public Object getData() {
        return get("data");
    }

    public void setData(T data) {
        put("data", data);
    }
}
