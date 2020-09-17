package com.wobangkj.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 异常处理接口
 *
 * @author cliod
 * @since 9/17/20 2:02 PM
 */
public interface ExceptionHandler {
	/**
	 * 异常处理
	 *
	 * @param request  请求
	 * @param response 响应
	 * @param e        异常
	 * @return 结果消息
	 */
	Object handler(Throwable e, HttpServletRequest request, HttpServletResponse response);
}
