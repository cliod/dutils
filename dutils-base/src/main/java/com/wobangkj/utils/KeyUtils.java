package com.wobangkj.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * 短字符串工具
 *
 * @author Cliod
 * @since 2019/6/2
 */
public class KeyUtils {

	/**
	 * 随机数
	 * 当len小于等于0时，结果为 "0"
	 * 当len在1~10之间，有正常结果
	 * 当len大于10，结果长度为10
	 *
	 * @param len 长度，取值 0~10
	 * @return 随机数
	 */
	public static @NotNull String randNum(int len) {
		return Integer.toString((int) ((Math.random() * 9 + 1) * Math.pow(10, len - 1)));
	}

	/**
	 * base64 解密 utf8
	 *
	 * @param value 待解密字符
	 * @return 解密字符
	 */
	public static @NotNull String decode(@NotNull String value) {
		return new String(Base64.getDecoder().decode(value), StandardCharsets.UTF_8);
	}

	/**
	 * base64 解密 utf8
	 *
	 * @param value 待解密字符
	 * @return 解密字符
	 */
	public static @NotNull String decode(byte[] value) {
		return new String(Base64.getDecoder().decode(value), StandardCharsets.UTF_8);
	}

	/**
	 * base64 加密 utf8
	 *
	 * @param value 待加密字符
	 * @return 加密字符
	 */
	public static @NotNull String encode(@NotNull String value) {
		return Base64.getEncoder().encodeToString(value.getBytes(StandardCharsets.UTF_8));
	}

	/**
	 * base64 加密 utf8
	 *
	 * @param value 待加密字符
	 * @return 加密字符
	 */
	public static @NotNull String encode(byte[] value) {
		return Base64.getEncoder().encodeToString(value);
	}

	/**
	 * SHA-265 机密
	 * 等效于{@link} com.wobangkj.util.EncryptUtils.encodeSha256String()
	 *
	 * @param value 字符串
	 * @return 加密结果
	 */
	public static @NotNull String encrypt(String value) throws NoSuchAlgorithmException {
		String encodeStr = "";
		if (StringUtils.isEmpty(value)) {
			return encodeStr;
		}
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(value.getBytes(StandardCharsets.UTF_8));
		encodeStr = byte2Hex(md.digest());
		return encodeStr;
	}

	/**
	 * MD5 计算
	 * 等效于{@link} com.wobangkj.util.Md5Utils.encode32()
	 *
	 * @param value 字符串
	 * @return MD5值
	 */
	public static byte[] md5(@NotNull String value) {
		return DigestUtils.md5(value.getBytes(StandardCharsets.UTF_8));
	}

	/**
	 * MD5 计算
	 * 等效于{@link} com.wobangkj.util.Md5Utils.encode32()
	 *
	 * @param value 字符串
	 * @return MD5值
	 */
	public static @NotNull String md5Hex(@NotNull String value) {
		return DigestUtils.md5Hex(value.getBytes(StandardCharsets.UTF_8));
	}

	/**
	 * 将byte转为16进制
	 *
	 * @param bytes 字符byte
	 * @return 加密字符串
	 */
	public static @NotNull String byte2Hex(byte[] bytes) {
		StringBuilder builder = new StringBuilder();
		String temp;
		for (byte b : bytes) {
			temp = Integer.toHexString(b & 0xFF);
			if (temp.length() == 1) {
				// 1得到一位的进行补0操作
				builder.append("0");
			}
			builder.append(temp);
		}
		return builder.toString();
	}

	/**
	 * 生成order id
	 *
	 * @return id
	 */
	@Deprecated
	public static @NotNull String generateTimeKey() {
		return System.currentTimeMillis() + randNum(6);
	}

	/**
	 * 函数功能说明 ： 获得文件名的后缀名. 修改者名字： 修改日期： 修改内容：
	 *
	 * @param fileName 文件名
	 * @return 扩展名
	 */
	public static @NotNull String getExt(@NotNull String fileName) {
		return fileName.substring(fileName.lastIndexOf(".") + 1);
	}

	/**
	 * 获取去掉横线的长度为32的UUID串.
	 *
	 * @return uuid.
	 * @author WuShuicheng.
	 */
	public static @NotNull String get32uuid() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	/**
	 * 获取带横线的长度为36的UUID串.
	 *
	 * @return uuid.
	 * @author WuShuicheng.
	 */
	public static @NotNull String get36uuid() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 雪花算法id生成
	 *
	 * @return id
	 */
	@Deprecated
	public static long nextId() {
		throw new UnsupportedOperationException("不支持此方法");
	}

	/**
	 * 验证一个字符串是否完全由纯数字组成的字符串，当字符串为空时也返回false.
	 *
	 * @param str 要判断的字符串 .
	 * @return true or false .
	 * @author WuShuicheng .
	 */
	public static boolean isNumeric(String str) {
		if (StringUtils.isBlank(str)) {
			return false;
		} else {
			return str.matches("\\d*");
		}
	}

	/**
	 * 计算采用utf-8编码方式时字符串所占字节数
	 *
	 * @param content 内容
	 * @return 字节数
	 */
	@Deprecated
	public static int getByteSize(String content) {
		int size = 0;
		if (null != content) {
			// 汉字采用utf-8编码时占3个字节
			size = content.getBytes(StandardCharsets.UTF_8).length;
		}
		return size;
	}

	/**
	 * 函数功能说明 ： 截取字符串拼接in查询参数.
	 *
	 * @param param 参数
	 * @return String
	 */
	@Deprecated
	public static @NotNull List<String> getInParam(@NotNull String param) {
		boolean flag = param.contains(",");
		List<String> list = new ArrayList<>();
		if (flag) {
			list = Arrays.asList(param.split(","));
		} else {
			list.add(param);
		}
		return list;
	}
}
