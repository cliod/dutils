package com.wobangkj.handler;

import com.wobangkj.bean.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 接口统一异常捕获
 *
 * @author cliod
 * @since 19-7-16
 */
public interface AbstractExceptionHandler {

    /**
     * 系统异常处理，比如：404,500
     *
     * @param request  请求
     * @param response 响应
     * @param handler  请求方法(处理器)
     * @param e        异常
     * @return 统一结果
     */
    Result<Object> handler(HttpServletRequest request, HttpServletResponse response, Object handler, Throwable e);
}
