package com.wobangkj.bean;

import com.alibaba.fastjson.JSON;
import com.wobangkj.api.Session;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

/**
 * 请求
 *
 * @author cliod
 * @date 2019/11/9
 * package : com.wobangkj.bean
 */
@Data
public class Require<T> implements Session {

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
     * 时间戳
     */
    private Long timestamp;
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

    @NotNull
    public Object readResolve() throws Exception {
        return this.getClass().getConstructor().newInstance();
    }
}
