package com.wobangkj.utils;

import org.jetbrains.annotations.NotNull;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.GeneralSecurityException;

/**
 * aes加解密工具类
 *
 * @author cliod
 * @since 7/23/20 9:51 AM
 */
public class AesUtils {
	static final String AES_IV = "github.com/dream";
	static final String AES_KEY = "github.com/dreamlu/gt dreamlu123";
	private static final String KEY_ALGORITHM = "AES";
	private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";

	private AesUtils() {
	}

	/**
	 * 加密
	 *
	 * @param content 字符串内容
	 * @return 加密结果
	 */
	public static @NotNull String encode(String content) throws GeneralSecurityException {
		return encode(content, AesUtils.AES_KEY);
	}

	/**
	 * AES 解密操作
	 *
	 * @param content 加密内容
	 * @return 解密结果
	 */
	public static @NotNull String decode(String content) throws GeneralSecurityException {
		return decode(content, AesUtils.AES_KEY);
	}

	/**
	 * 加密
	 *
	 * @param content 字符串内容
	 * @param aesKey  加密盐
	 * @return 加密结果
	 */
	public static @NotNull String encode(String content, String aesKey) throws GeneralSecurityException {
		return encode(content, aesKey, AesUtils.AES_IV);
	}

	/**
	 * AES 解密操作
	 *
	 * @param content 加密内容
	 * @param aesKey  加密盐
	 * @return 解密结果
	 */
	public static @NotNull String decode(String content, String aesKey) throws GeneralSecurityException {
		return decode(content, aesKey, AesUtils.AES_IV);
	}

	/**
	 * content: 加密内容
	 * slatKey: 加密的盐，16位字符串
	 * vectorKey: 加密的向量，16位字符串
	 */
	public static @NotNull String encode(@NotNull String content, @NotNull String slatKey, @NotNull String vectorKey) throws GeneralSecurityException {
		Cipher cipher = Cipher.getInstance(TRANSFORMATION);
		SecretKey secretKey = new SecretKeySpec(slatKey.getBytes(), KEY_ALGORITHM);
		IvParameterSpec iv = new IvParameterSpec(vectorKey.getBytes());
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
		byte[] encrypted = cipher.doFinal(content.getBytes());
		return Base64Utils.encodeToString(encrypted);
	}

	/**
	 * content: 解密内容(base64编码格式)
	 * slatKey: 加密时使用的盐，16位字符串
	 * vectorKey: 加密时使用的向量，16位字符串
	 */
	public static @NotNull String decode(String content, @NotNull String slatKey, @NotNull String vectorKey) throws GeneralSecurityException {
		Cipher cipher = Cipher.getInstance(TRANSFORMATION);
		SecretKey secretKey = new SecretKeySpec(slatKey.getBytes(), KEY_ALGORITHM);
		IvParameterSpec iv = new IvParameterSpec(vectorKey.getBytes());
		cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
		byte[] contents = Base64Utils.decode(content);
		byte[] encrypted = cipher.doFinal(contents);
		return new String(encrypted);
	}

	/**
	 * content: 加密内容
	 * slatKey: 加密的盐，16位字符串
	 * vectorKey: 加密的向量，16位字符串
	 */
	public static @NotNull String encrypt(@NotNull String content, @NotNull String slatKey, @NotNull String vectorKey) throws GeneralSecurityException {
		Cipher cipher = Cipher.getInstance(TRANSFORMATION);
		IvParameterSpec iv = new IvParameterSpec(vectorKey.getBytes());
		SecretKey secretKey = new SecretKeySpec(slatKey.getBytes(), KEY_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
		byte[] encrypted = cipher.doFinal(content.getBytes());
		return HexUtils.bytes2Hex(encrypted);
	}

	/**
	 * content: 解密内容(hex16编码格式)
	 * slatKey: 加密时使用的盐，16位字符串
	 * vectorKey: 加密时使用的向量，16位字符串
	 */
	public static @NotNull String decrypt(String content, @NotNull String slatKey, @NotNull String vectorKey) throws GeneralSecurityException {
		Cipher cipher = Cipher.getInstance(TRANSFORMATION);
		SecretKey secretKey = new SecretKeySpec(slatKey.getBytes(), KEY_ALGORITHM);
		IvParameterSpec iv = new IvParameterSpec(vectorKey.getBytes());
		cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
		byte[] contents = HexUtils.hex2Bytes(content);
		byte[] encrypted = cipher.doFinal(contents);
		return new String(encrypted);
	}
}
