package com.wobangkj.utils;

import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 字符串加密
 * 文档：https://blog.csdn.net/chengbinbbs/article/details/78640589
 *
 * @author cliod
 * @since 2019/12/5
 */
public class EncryptUtils {

	/**
	 * 私有构造方法,将该工具类设为单例模式.
	 */
	private EncryptUtils() {
	}

	/**
	 * 用MD5算法进行加密
	 * 等效于{@link} com.wobangkj.util.Md5Utils.encode32()
	 *
	 * @param str 需要加密的字符串
	 * @return MD5加密后的结果
	 */
	@SneakyThrows
	public static @NotNull String encodeMd5(String str) {
		return encode(str, "MD5");
	}

	/**
	 * 用SHA1算法进行加密
	 *
	 * @param str 需要加密的字符串
	 * @return SHA加密后的结果
	 */
	@SneakyThrows
	public static @NotNull String encodeSha1(String str) {
		return encode(str, "SHA1");
	}

	/**
	 * 用SHA235算法进行加密
	 *
	 * @param str 需要加密的字符串
	 * @return SHA加密后的结果
	 */
	@NotNull
	@SneakyThrows
	public static String encodeSha256(String str) {
		return encode(str, "SHA-256");
	}

	/**
	 * 加密
	 *
	 * @param content 字符串内容
	 * @return 加密结果
	 */
	@SneakyThrows
	public static @NotNull String encodeAes(String content) {
		return AesUtils.encode(content);
	}

	/**
	 * AES 解密操作
	 *
	 * @param content 加密内容
	 * @return 解密结果
	 */
	@SneakyThrows
	public static @NotNull String decodeAes(String content) {
		return AesUtils.decode(content);
	}

	/**
	 * 加密
	 *
	 * @param content 字符串内容
	 * @return 加密结果
	 */
	@Deprecated
	@SneakyThrows
	public static @NotNull String encodeAse(String content) {
		return AesUtils.encode(content, AesUtils.AES_KEY, AesUtils.AES_IV);
	}

	/**
	 * AES 解密操作
	 *
	 * @param content 加密内容
	 * @return 解密结果
	 */
	@Deprecated
	@SneakyThrows
	public static @NotNull String decodeAse(String content) {
		return AesUtils.decode(content, AesUtils.AES_KEY, AesUtils.AES_IV);
	}

	/**
	 * 加密
	 * 1.构造密钥生成器
	 * 2.根据encodeRules规则初始化密钥生成器
	 * 3.产生密钥
	 * 4.创建和初始化密码器
	 * 5.内容加密
	 * 6.返回字符串
	 *
	 * @param content     字符串内容
	 * @param encodeRules 加密规则(盐值)
	 * @return 加密结果
	 */
	@Deprecated
	@SneakyThrows
	public static @NotNull String encodeAse(String encodeRules, String content) {
		return AesUtils.encode(content, encodeRules, AesUtils.AES_IV);
	}

	/**
	 * AES 解密操作
	 * 解密过程：
	 * 1.同加密1-4步
	 * 2.将加密后的字符串反纺成byte[]数组
	 * 3.将加密内容解密
	 *
	 * @param content     加密内容
	 * @param encodeRules 规则(盐值)
	 * @return 解密结果
	 */
	@Deprecated
	@SneakyThrows
	public static @NotNull String decodeAse(String encodeRules, String content) {
		return AesUtils.decode(content, encodeRules, AesUtils.AES_IV);
	}

	private static @NotNull String encode(@NotNull String str, String method) throws NoSuchAlgorithmException {
		MessageDigest mdInst;
		// 把密文转换成十六进制的字符串形式
		// 单线程用StringBuilder，速度快 多线程用stringBuffer，安全
		StringBuilder builder = new StringBuilder();
		// 获得MD5摘要算法的 MessageDigest对象
		mdInst = MessageDigest.getInstance(method);
		// 使用指定的字节更新摘要
		mdInst.update(str.getBytes());
		// 获得密文
		byte[] md = mdInst.digest();
		for (int b : md) {
			int tmp = b;
			if (tmp < 0) {
				tmp += 256;
			}
			if (tmp < 16) {
				builder.append("0");
			}
			builder.append(Integer.toHexString(tmp));
		}
		return builder.toString();
	}
}
