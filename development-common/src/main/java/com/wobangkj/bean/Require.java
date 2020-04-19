package com.wobangkj.bean;

import com.alibaba.fastjson.JSON;
import com.wobangkj.api.Session;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.Map;

/**
 * 请求
 *
 * @author cliod
 * @since 2019/11/9
 * package : com.wobangkj.bean
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Require<T> extends HashMap<String, Object> implements Map<String, Object>, Session {

    private static final long serialVersionUID = 1618155628290794827L;
    /**
     * 签名
     */
    private String sign;
    /**
     * jwt
     */
    private String token;
    /**
     * 参数
     */
    private T params;

    /**
     * 转成字符串
     *
     * @return 字符串
     */
    @Override
    public String toString() {
        return this.toJson();
    }

    /**
     * 转成Json
     *
     * @return Json
     */
    @Override
    public String toJson() {
        return JSON.toJSONString(this);
    }

    public Object readResolve() throws Exception {
        return this.getClass().getConstructor().newInstance();
    }
}
