package com.wobangkj.utils;

import org.jetbrains.annotations.NotNull;

import javax.servlet.http.HttpServletRequest;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

/**
 * ip工具
 *
 * @author cliod
 * @since 2019/10/8
 * package : com.wobangkj.git.cliod.util
 */
public class IPv4Utils {

	private static final String IS255 = "(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
	private static final Pattern PATTERN = Pattern.compile("^(?:" + IS255 + "\\.){3}" + IS255 + "$");

	public static @NotNull String longToIpV4(long longIp) {
		return IpUtils.toString(longIp);
	}

	/**
	 * 将IP地址转换为长整型
	 *
	 * @param ip ip地址
	 * @return 长整型数值
	 */
	public static long ipV4ToLong(@NotNull String ip) {
		return IpUtils.ipV4ToLong(ip);
	}

	/**
	 * 是否私有ipv4
	 *
	 * @param ip ip地址字符串
	 * @return 是否私有ipv4地址
	 */
	public static boolean isIPv4Private(String ip) {
		return IpUtils.isIPv4Private(ip);
	}

	/**
	 * 是否有效ipv4
	 *
	 * @param ip 字符串
	 * @return 是否ipv4地址
	 */
	public static boolean isIPv4Valid(String ip) {
		return IpUtils.isIPv4Valid(ip);
	}

	/**
	 * 获取请求的ip地址
	 * nginx转发需要设置 //proxy_set_header X-Forwarded-For $remote_addr;
	 *
	 * @param request 请求
	 * @return ip地址
	 */
	public static String getIpFromRequest(@NotNull HttpServletRequest request) {
		String ip;
		boolean found = false;
		String xff = "x-forwarded-for";
		if ((ip = request.getHeader(xff)) != null) {
			StringTokenizer tokenizer = new StringTokenizer(ip, ",");
			while (tokenizer.hasMoreTokens()) {
				ip = tokenizer.nextToken().trim();
				if (isIPv4Valid(ip)) {
					found = true;
					break;
				}
			}
		}
		if (!found) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
