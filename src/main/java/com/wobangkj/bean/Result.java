package com.wobangkj.bean;

import com.wobangkj.api.Session;
import com.wobangkj.utils.JsonUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;

/**
 * api返回统一封装
 *
 * @author cliod
 * @since 2019/5/20 13:20
 */
@Deprecated
public class Result<T> extends Maps<String, Object> implements Session {

    private static final long serialVersionUID = -1884640212713045469L;

    @Deprecated
    public Result(int initialCapacity) {
        super(initialCapacity);
    }

    @Deprecated
    public Result() {
        super();
    }

    @Deprecated
    public Result(Map<? extends String, ?> m) {
        super(m);
    }

    @Deprecated
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
    @Deprecated
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
    @Deprecated
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
    @Deprecated
    public static @NotNull Result<Object> of(int code, String msg, @NotNull Throwable o) {
        Result<Object> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        result.setErr(o.getMessage());
        return result;
    }

    @Override
    @Deprecated
    public Result<T> add(String k, Object v) {
        put(k, v);
        return this;
    }

    @Override
    @Deprecated
    public Maps<String, Object> set(String k, Object v) {
        if (!Objects.isNull(v) && !Objects.isNull(k)) {
            put(k, v);
        }
        return this;
    }

    @Override
    @Deprecated
    public Result<T> del(String k) {
        remove(k);
        return this;
    }

    @Override
    @Deprecated
    public Object rem(String k) {
        return remove(k);
    }

    /**
     * 转成字符串
     *
     * @return 字符串
     */
    @Override
    @Deprecated
    public @NotNull String toString() {
        return this.toJson();
    }

    /**
     * 转成Json
     *
     * @return Json
     */
    @Override
    @Deprecated
    public @NotNull String toJson() {
        return JsonUtils.toJson(this.toObject());
    }

    @Deprecated
    public @NotNull Object readResolve() throws Exception {
        return this.getClass().getConstructor().newInstance();
    }

    /**
     * 转成Map对象
     *
     * @return java.util.Map
     * @see Map
     */
    @Override
    @Deprecated
    public @NotNull Result<T> toObject() {
        return this;
    }

    @Deprecated
    public Integer getCode() {
        return (Integer) get("status");
    }

    @Deprecated
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

    @Deprecated
    public String getMsg() {
        return (String) get("msg");
    }

    @Deprecated
    public void setMsg(String msg) {
        put("msg", msg);
    }

    @Deprecated
    public Object getErr() {
        return get("err");
    }

    @Deprecated
    public void setErr(Object err) {
        put("err", err);
    }

    @Deprecated
    public Object getData() {
        return get("data");
    }

    @Deprecated
    public void setData(Object data) {
        put("data", data);
    }
}
