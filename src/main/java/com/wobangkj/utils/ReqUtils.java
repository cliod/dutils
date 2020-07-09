package com.wobangkj.utils;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
        Map<String, Object> map = new HashMap<>(16);
        Map<String, String[]> paramNames = request.getParameterMap();
        for (Map.Entry<String, String[]> entry : paramNames.entrySet()) {
            String paramName = entry.getKey();
            String[] paramValues = entry.getValue();
            if (paramValues.length == 1) {
                String paramValue = paramValues[0];
                if (paramValue.length() != 0) {
                    map.put(paramName, paramValue);
                }
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
        String token = request.getHeader("Authorization");
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
