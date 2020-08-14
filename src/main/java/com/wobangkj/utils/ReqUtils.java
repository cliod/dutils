package com.wobangkj.utils;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 拦截器获取参数
 *
 * @author cliod
 * @since 2020/06/25
 * package : com.example.measureless.aspect
 */
public class ReqUtils {

    /**
     * 从 HttpServletRequest 中获取参数
     *
     * @param request 请求封装
     * @return 参数
     */
    public static @NotNull Map<String, Object> getParams(@NotNull HttpServletRequest request) {
        return getParams(request, false, true);
    }

    /**
     * 从 HttpServletRequest 中获取参数
     *
     * @param request 请求封装
     * @param accArr  是否接收数组
     * @param rmEmpty 是否去掉空参数
     * @return 参数
     */
    public static @NotNull Map<String, Object> getParams(@NotNull HttpServletRequest request, boolean accArr, boolean rmEmpty) {
        Map<String, Object> map = new HashMap<>(16);
        Map<String, String[]> paramNames = request.getParameterMap();
        String paramName;
        String[] paramValues;
        for (Map.Entry<String, String[]> entry : paramNames.entrySet()) {
            paramName = entry.getKey();
            paramValues = entry.getValue();
            if (paramValues.length > 0) {
                // 有参数值
                if (paramValues.length == 1) {  // 一个参数值
                    String paramValue = paramValues[0];
                    // 参数值是否为空
                    if (paramValue.length() > 0)
                        // 参数不为空
                        map.put(paramName, paramValue);
                    else
                        // 参数为空
                        if (!rmEmpty) map.put(paramName, null);
                } else {  //多个参数值
                    // 接收数组
                    if (accArr) map.put(paramName, paramValues);
                    else map.put(paramName, paramValues[0]);
                }
            } else {
                // 无参数
                if (!rmEmpty) map.put(paramName, null);
            }
        }
        return map;
    }

    /**
     * 从HttpServletRequest中获取用户传过来的token
     * <p>先看<code>HttpHeaders.AUTHORIZATION</code>,再看cookie,然后header是否有token,然后看参数</p>
     *
     * @param request 请i取封装
     * @return 结果
     */
    public static @Nullable String getToken(@NotNull HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isBlank(token)) {
            Cookie[] cookies = request.getCookies();
            if (!ArrayUtils.isEmpty(cookies))
                for (Cookie cookie : cookies)
                    if (cookie.getName().equals("token"))
                        token = cookie.getValue();
        }
        if (StringUtils.isBlank(token)) {
            token = request.getHeader("token");
        }
        if (StringUtils.isBlank(token)) {
            token = request.getParameter("token");
        }
        return token;
    }
}
