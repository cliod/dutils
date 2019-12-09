package com.wobangkj.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 结果处理
 *
 * @author cliod
 * @date 2019/10/20
 * package : com.wobangkj.handler
 */
public abstract class AbstractResultHandler {

    /**
     * 返回结果处理
     *
     * @param request  请求
     * @param response 响应
     * @param handler  处理
     * @return 结果
     */
    public abstract boolean handler(HttpServletRequest request, HttpServletResponse response, Object handler);
}
