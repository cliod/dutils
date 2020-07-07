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
public class Result<T> extends Maps<String, Object> implements Session {

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
        Result<Object> map = new Result<>();
        map.put(k, v);
        return map;
    }

    /**
     * 代替构造方法
     *
     * @param code 状态码
     * @param msg  响应消息
     * @return result
     */
    public static @NotNull Result<Object> of(int code, String msg) {
        Result<Object> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    /**
     * 代替构造方法
     *
     * @param code 状态码
     * @param msg  响应消息
     * @param o    响应对象
     * @return result
     */
    public static @NotNull <T> Result<T> of(int code, String msg, Object o) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(o);
        return result;
    }

    /**
     * 代替构造方法
     *
     * @param code 状态码
     * @param msg  响应消息
     * @param o    响应异常
     * @return result
     */
    public static @NotNull Result<Object> of(int code, String msg, @NotNull Throwable o) {
        Result<Object> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        result.setErr(o.getMessage());
        return result;
    }

    /**
     * 转成字符串
     *
     * @return 字符串
     */
    @Override
    public @NotNull
    final String toString() {
        return this.toJson();
    }

    /**
     * 转成Json
     *
     * @return Json
     */
    @Override
    public @NotNull
    final String toJson() {
        return JsonUtils.toJson(this);
    }

    public @NotNull
    final Object readResolve() throws Exception {
        return this.getClass().getConstructor().newInstance();
    }

    /**
     * 转成Map对象
     *
     * @return java.util.Map
     * @see java.util.Map
     */
    @Override
    public @NotNull Result<?> toObject() {
        return this;
    }

    public Integer getCode() {
        return (Integer) get("status");
    }

    public void setCode(Integer code) {
        put("status", code);
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

    public <E> E getData(Class<E> type) {
        return JsonUtils.fromJson(JsonUtils.toJson(get("data")), type);
    }

    public void setData(Object data) {
        put("data", data);
    }
}
