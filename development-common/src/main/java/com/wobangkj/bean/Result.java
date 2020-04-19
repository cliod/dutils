package com.wobangkj.bean;

import com.alibaba.fastjson.JSON;
import com.wobangkj.api.Session;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * @author cliod
 * @since 2019/5/20 13:20
 */
@EqualsAndHashCode(callSuper = true)
@Data
public final class Result<T> extends HashMap<String, Object> implements Map<String, Object>, Session {

    private static final long serialVersionUID = -1884640212713045469L;
    private Integer code;
    private Boolean state;
    private String msg;
    private Object err;
    private T data;

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
    public static <T> @NotNull Result<T> of(Integer code, Boolean state, String msg, Object err, T data) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setState(state);
        result.setMsg(msg);
        result.setData(data);
        result.setErr(err);
        return result;
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
    public static <T> @NotNull Result<T> of(int code, boolean state, String msg, T o) {
        return of(code, state, msg, null, o);
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
        return JSON.toJSONString(this);
    }

    public @NotNull Object readResolve() throws Exception {
        return this.getClass().getConstructor().newInstance();
    }
}
