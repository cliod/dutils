package com.wobangkj.handler;

import com.wobangkj.bean.Result;

import javax.servlet.http.HttpServletRequest;

/**
 * 接口统一异常捕获
 *
 * @author cliod
 * @since 19-7-16
 */
public abstract class AbstractExceptionHandler {

    /**
     * 系统异常处理，比如：404,500
     *
     * @param req 请求处理
     * @param e   异常
     * @return json
     */
    public abstract Result<?> handler(HttpServletRequest req, Throwable e);
}
