package com.wobangkj.utils;

import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

/**
 * base64工具类
 *
 * @author cliod
 * @since 7/23/20 1:08 PM
 */
public class Base64Utils {
	private Base64Utils() {
	}

	/**
	 * 用base64算法进行加密
	 *
	 * @param str 需要加密的字符串
	 * @return base64加密后的结果
	 */
	public static @NotNull String encodeToString(@NotNull String str) {
		return Base64.getEncoder().encodeToString(str.getBytes());
	}

	/**
	 * 用base64算法进行加密
	 *
	 * @param contents 需要加密二进制数组
	 * @return base64加密后的结果
	 */
	public static @NotNull String encodeToString(byte[] contents) {
		return Base64.getEncoder().encodeToString(contents);
	}

	/**
	 * 用base64算法进行加密
	 *
	 * @param str 需要加密的字符串
	 * @return base64加密后的结果
	 */
	public static byte[] encode(@NotNull String str) {
		return Base64.getEncoder().encode(str.getBytes());
	}

	/**
	 * 用base64算法进行加密
	 *
	 * @param contents 需要加密二进制数组
	 * @return base64加密后的结果
	 */
	public static byte[] encode(byte[] contents) {
		return Base64.getEncoder().encode(contents);
	}

	/**
	 * 用base64算法进行解密
	 *
	 * @param str 需要解密的字符串
	 * @return base64解密后的结果
	 */
	public static String decodeToString(String str) {
		return new String(decode(str));
	}

	/**
	 * 用base64算法进行解密
	 *
	 * @param str 需要解密的字符串
	 * @return base64解密后的结果
	 */
	public static String decodeToString(byte[] str) {
		return new String(decode(str));
	}

	/**
	 * 用base64算法进行解密
	 *
	 * @param str 需要解密的字符串
	 * @return base64解密后的结果
	 */
	public static byte[] decode(String str) {
		return Base64.getDecoder().decode(str);
	}

	/**
	 * 用base64算法进行解密
	 *
	 * @param str 需要解密的字符串
	 * @return base64解密后的结果
	 */
	public static byte[] decode(byte[] str) {
		return Base64.getDecoder().decode(str);
	}

	/**
	 * 将图片转成base64字符串
	 * 主要是小文件, 大多用于网络传输
	 *
	 * @param file 文件
	 * @return base64字符串
	 * @throws IOException io异常
	 */
	public static @NotNull String encodeFile(File file) throws IOException {
		byte[] bytes = IOUtils.toByteArray(new FileInputStream(file));
		return encodeToString(bytes);
	}

	/**
	 * 将base64字符串转成文件
	 * 主要用于图片文件
	 *
	 * @param contents base64字符串
	 * @return 文件
	 */
	public static @NotNull File decodeFile(@NotNull String contents, String format) throws IOException {
		File file = new File("tmp" + format);
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(decode(contents));
		return file;
	}
}
