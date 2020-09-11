package com.wobangkj.utils;

import org.jetbrains.annotations.NotNull;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * ase加解密工具类
 *
 * @author cliod
 * @since 7/23/20 9:51 AM
 */
public class AseUtils {
	static final String AES_IV = "github.com/dream";
	static final String AES_KEY = "github.com/dreamlu/gt dreamlu123";

	private AseUtils() {
	}

	/**
	 * content: 加密内容
	 * slatKey: 加密的盐，16位字符串
	 * vectorKey: 加密的向量，16位字符串
	 */
	public static @NotNull String encode(@NotNull String content, @NotNull String slatKey, @NotNull String vectorKey)
			throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException,
			InvalidAlgorithmParameterException, InvalidKeyException {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		SecretKey secretKey = new SecretKeySpec(slatKey.getBytes(), "AES");
		IvParameterSpec iv = new IvParameterSpec(vectorKey.getBytes());
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
		byte[] encrypted = cipher.doFinal(content.getBytes());
		return Base64Utils.encode(encrypted);
	}

	/**
	 * content: 解密内容(base64编码格式)
	 * slatKey: 加密时使用的盐，16位字符串
	 * vectorKey: 加密时使用的向量，16位字符串
	 */
	public static @NotNull String decode(String content, @NotNull String slatKey, @NotNull String vectorKey)
			throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException,
			InvalidAlgorithmParameterException, InvalidKeyException {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		SecretKey secretKey = new SecretKeySpec(slatKey.getBytes(), "AES");
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
	public static @NotNull String encrypt(@NotNull String content, @NotNull String slatKey, @NotNull String vectorKey)
			throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException,
			InvalidAlgorithmParameterException, InvalidKeyException {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		IvParameterSpec iv = new IvParameterSpec(vectorKey.getBytes());
		SecretKey secretKey = new SecretKeySpec(slatKey.getBytes(), "AES");
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
		byte[] encrypted = cipher.doFinal(content.getBytes());
		return HexUtils.bytes2Hex(encrypted);
	}

	/**
	 * content: 解密内容(base64编码格式)
	 * slatKey: 加密时使用的盐，16位字符串
	 * vectorKey: 加密时使用的向量，16位字符串
	 */
	public static @NotNull String decrypt(String content, @NotNull String slatKey, @NotNull String vectorKey)
			throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException,
			InvalidAlgorithmParameterException, InvalidKeyException {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		SecretKey secretKey = new SecretKeySpec(slatKey.getBytes(), "AES");
		IvParameterSpec iv = new IvParameterSpec(vectorKey.getBytes());
		cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
		byte[] contents = HexUtils.hex2Bytes(content);
		byte[] encrypted = cipher.doFinal(contents);
		return new String(encrypted);
	}

	public static void main(String[] args) throws NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
		String a = encode("admin", AES_KEY, AES_IV);
		String b = encrypt("admin", AES_KEY, AES_IV);
		System.out.println(a);
		System.out.println(b);
		System.out.println(decode(a, AES_KEY, AES_IV));
		System.out.println(decrypt(b, AES_KEY, AES_IV));
		System.out.println(decode("sPa0sTmDf6gasS9tHvIqKw==", AES_KEY, AES_IV));
	}
}
