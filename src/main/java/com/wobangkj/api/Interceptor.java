package com.wobangkj.api;

import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * 拦截器
 *
 * @author cliod
 * @since 7/9/20 3:47 PM
 */
public abstract class Interceptor {

	/**
	 * 处理
	 *
	 * @param request  请求
	 * @param response 响应
	 * @param handler  请求方法
	 * @return 结果
	 */
	protected abstract boolean process(HttpServletRequest request, HttpServletResponse response, Object handler);

	/**
	 * 响应结果，将异常直接抛出
	 *
	 * @param response 响应
	 * @return 返回是否向下执行
	 * @throws IOException IO异常
	 */
	private boolean fail(@NotNull HttpServletResponse response) throws IOException {
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().write(Response.fail("token不存在或者token已失效").toString());
		return false;
	}

	/**
	 * 忽略的url
	 *
	 * @param url  请求url
	 * @param uris 忽略的ip或者域名或者路径
	 * @return 是否忽略
	 */
	private boolean ignore(String url, String... uris) {
		if (Objects.isNull(uris) || ArrayUtils.isEmpty(uris)) {
			//没有跳过的
			return false;
		}
		boolean re = false;
		for (String ip : uris) {
			re = re || url.contains(ip);
		}
		return re;
	}
}
