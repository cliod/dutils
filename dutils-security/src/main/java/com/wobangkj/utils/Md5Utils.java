package com.wobangkj.utils;

import org.jetbrains.annotations.NotNull;
import org.springframework.util.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5签名工具类
 *
 * @author cliod
 * @since 2019/12/5
 */
public class Md5Utils {

	private static final String[] HEX = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

	/**
	 * 私有构造方法,将该工具类设为单例模式.
	 */
	private Md5Utils() {
	}

	/**
	 * 32位MD5签名值
	 *
	 * @param password 密码
	 * @return 签名
	 */
	public static @NotNull String encode32(String password) throws NoSuchAlgorithmException {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		byte[] byteArray = md5.digest(password.getBytes(StandardCharsets.UTF_8));
		return HexUtils.bytes2Hex(byteArray);
	}

	/**
	 * 32位大写MD5签名值
	 *
	 * @param password 密码
	 * @return 签名
	 */
	public static @NotNull String encode32ToUpper(String password) throws NoSuchAlgorithmException {
		return encode32(password).toUpperCase();
	}

	/**
	 * 32位大写MD5签名值
	 *
	 * @param password 密码
	 * @return 签名
	 */
	public static @NotNull String encode32ToLower(String password) throws NoSuchAlgorithmException {
		return encode32(password).toLowerCase();
	}

	/**
	 * 32位大写MD5签名值
	 *
	 * @param password 密码
	 * @return 签名
	 */
	@Deprecated
	public static @NotNull String encode32ToUpperCase(String password) throws NoSuchAlgorithmException {
		return encode32(password).toUpperCase();
	}

	/**
	 * 16位MD5签名值
	 *
	 * @param password 密码
	 * @return 签名
	 */
	public static @NotNull String encode16(String password) throws NoSuchAlgorithmException {
		return encode32(password).substring(8, 24);
	}

	/**
	 * 16位大写MD5签名值
	 *
	 * @param password 密码
	 * @return 签名
	 */
	public static @NotNull String encode16ToUpper(String password) throws NoSuchAlgorithmException {
		return encode32ToUpper(password).substring(8, 24);
	}

	/**
	 * 16位大写MD5签名值
	 *
	 * @param password 密码
	 * @return 签名
	 */
	@Deprecated
	public static @NotNull String encode16ToUpperCase(String password) throws NoSuchAlgorithmException {
		return encode32ToUpper(password).substring(8, 24);
	}

	public static String encode(String password, String enc) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		byte[] byteArray = md5.digest(password.getBytes(enc));
		return HexUtils.bytes2Hex(byteArray);
	}
}
