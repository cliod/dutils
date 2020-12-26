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
public class IpUtils {

	private static final String IS255 = "(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
	private static final Pattern PATTERN = Pattern.compile("^(?:" + IS255 + "\\.){3}" + IS255 + "$");

	public static @NotNull String longToIpV4(long longIp) {
		return toString(longIp);
	}

	@NotNull
	public static String toString(long longIp) {
		int octet3 = (int) ((longIp >> 24) % 256);
		int octet2 = (int) ((longIp >> 16) % 256);
		int octet1 = (int) ((longIp >> 8) % 256);
		int octet0 = (int) ((longIp) % 256);
		return octet3 + "." + octet2 + "." + octet1 + "." + octet0;
	}

	/**
	 * 将IP地址转换为长整型
	 *
	 * @param ip ip地址
	 * @return 长整型数值
	 */
	public static long ipV4ToLong(@NotNull String ip) {
		String[] octets = ip.split("\\.");
		return (Long.parseLong(octets[0]) << 24) + (Long.parseLong(octets[1]) << 16)
				+ (Long.parseLong(octets[2]) << 8) + Long.parseLong(octets[3]);
	}

	/**
	 * 是否私有ipv4
	 *
	 * @param ip ip地址字符串
	 * @return 是否私有ipv4地址
	 */
	public static boolean isIPv4Private(String ip) {
		long longIp = ipV4ToLong(ip);
		return (longIp >= ipV4ToLong("10.0.0.0") && longIp <= ipV4ToLong("10.255.255.255"))
				|| (longIp >= ipV4ToLong("172.16.0.0") && longIp <= ipV4ToLong("172.31.255.255"))
				|| longIp >= ipV4ToLong("192.168.0.0") && longIp <= ipV4ToLong("192.168.255.255");
	}

	/**
	 * 是否有效ipv4
	 *
	 * @param ip 字符串
	 * @return 是否ipv4地址
	 */
	public static boolean isIPv4Valid(String ip) {
		return PATTERN.matcher(ip).matches();
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
				if (isIPv4Valid(ip) && !isIPv4Private(ip)) {
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
