package com.wobangkj.bean;

import com.alibaba.fastjson.JSON;
import com.wobangkj.api.Session;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

/**
 * @author cliod
 * @date 2019/5/20 13:20
 */
@Data
public final class Result<T> implements Session {

    private static final long serialVersionUID = -1884640212713045469L;
    private Integer code;
    private Boolean state;
    private Long timestamp;
    private String msg;
    private Object err;
    private T data;

    /**
     * 代替构造方法
     *
     * @param code      code
     * @param state     state
     * @param timestamp 时间戳
     * @param msg       msg
     * @param err       异常详细信息
     * @param data      T
     * @param <T>       类型
     * @return result
     */
    @NotNull
    private static <T> Result<T> of(Integer code, Boolean state, Long timestamp, String msg, Object err, T data) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setState(state);
        result.setMsg(msg);
        result.setData(data);
        result.setErr(err);
        result.setTimestamp(timestamp);
        return result;
    }

    /**
     * 代替构造方法
     *
     * @param code  code
     * @param state state
     * @param msg   msg
     * @param err   异常详细信息
     * @param data  T
     * @param <T>   类型
     * @return result
     */
    @NotNull
    public static <T> Result<T> of(int code, boolean state, String msg, Object err, T data) {
        return of(code, state, System.currentTimeMillis(), msg, err, data);
    }

    /**
     * 代替构造方法
     *
     * @param code  code
     * @param state state
     * @param msg   msg
     * @param o     T
     * @return result
     */
    @NotNull
    public static <T> Result<T> of(int code, boolean state, String msg, T o) {
        return of(code, state, System.currentTimeMillis(), msg, null, o);
    }

    /**
     * 转成字符串
     *
     * @return 字符串
     */
    @NotNull
    @Override
    public String toString() {
        return this.toJson();
    }

    /**
     * 转成Json
     *
     * @return Json
     */
    @NotNull
    @Override
    public String toJson() {
        return JSON.toJSONString(this);
    }

    @NotNull
    public Object readResolve() throws Exception {
        return this.getClass().getConstructor().newInstance();
    }
}
