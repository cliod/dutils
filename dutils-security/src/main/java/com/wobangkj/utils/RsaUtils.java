package com.wobangkj.utils;

import javax.crypto.Cipher;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

/**
 * 消息摘要算法：
 * 消息摘要算法主要分三类：MD（Message Digest，消息摘要算法）、SHA（Secure Hash Algorithm，安全散列算法）和MAC（Message Authentication Code，消息认证码算法）。
 * <p>
 * 对称加密算法:
 * 加密和解密使用「相同密钥」的加密算法就是对称加密算法。常见的对称加密算法有AES、3DES、DES、RC5、RC6等。
 * <p>
 * 本加签: SHA-256作为摘要算法(加签)，RSA作为签名验签算法(加密)
 *
 * @author cliod
 * @since 8/4/20 10:37 AM
 */
public class RsaUtils {

	/**
	 * 公钥字符串
	 */
	private static final String PUBLIC_KEY_STR = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDaJzVjC5K6kbS2YE2fiDs6H8pB" +
			"JFDGEYqqJJC9I3E0Ebr5FsofdImV5eWdBSeADwcR9ppNbpORdZmcX6SipogKx9PX" +
			"5aAO4GPesroVeOs91xrLEGt/arteW8iSD+ZaGDUVV3+wcEdci/eCvFlc5PUuZJou" +
			"M2XZaDK4Fg2IRTfDXQIDAQAB";
	/**
	 * 私钥字符串
	 */
	private static final String PRIVATE_KEY_STR = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBANonNWMLkrqRtLZg" +
			"TZ+IOzofykEkUMYRiqokkL0jcTQRuvkWyh90iZXl5Z0FJ4APBxH2mk1uk5F1mZxf" +
			"pKKmiArH09floA7gY96yuhV46z3XGssQa39qu15byJIP5loYNRVXf7BwR1yL94K8" +
			"WVzk9S5kmi4zZdloMrgWDYhFN8NdAgMBAAECgYA9bz1Bn0i68b2KfqRdgOfs/nbe" +
			"0XNN1DLQp2t7WDfRCg01iI1zPkZgyFVZWtI85f5/uIrLs5ArLosL1oNuqqc0nNne" +
			"CvJK+ZxvA98Hx3ZqYTzDnleR054YhofL5awbhSciYVic204DOG1rhSsYWMqtX7J7" +
			"3geoWL7TYdMfYXcCAQJBAPMMKsz6ZJh98EeQ1tDG5gpAGWFQkYNrxZDelP/LjeO0" +
			"TP3XkQnIpcaZoCs7V/rRGRGMWwQ2BUdc/01in89ZZ5ECQQDlx2oBc1CtOAm2UAhN" +
			"1xWrPkZWENQ53wTrwXO4qbTGDfBKon0AehLlGCSqxQ71aufLkNO7ZlX0IHTAlnk1" +
			"TvENAkAGSEQ69CXxgx/Y2beTwfBkR2/gghKg0QJUUkyLqBlMz3ZGAXJwTE1sqr/n" +
			"HiuSAiGhwH0ByNuuEotO1sPGukrhAkAMK26a2w+nzPL+u+hkrwKPykGRZ1zGH+Cz" +
			"19AYNKzFXJGgclCqiMydY5T1knBDYUEbj/UW1Mmyn1FvrciHoUG1AkAEMEIuDauz" +
			"JabEAU08YmZw6OoDGsukRWaPfjOEiVhH88p00veM1R37nwhoDMGyEGXVeVzNPvk7" +
			"cELg28MSRzCK";

	/**
	 * 默认加密键长度
	 */
	private static final int DEFAULT_KEY_SIZE = 2048;
	private static PublicKey PUBLIC_KEY = null;

	private static PrivateKey PRIVATE_KEY = null;

	public static void main(String[] args) throws GeneralSecurityException {
		//原始报文
		String plain = "大家好，我是cliod";
		//加签
		byte[] signatureByte = sign(plain);
		System.out.println("原始报文是:" + plain);
		System.out.println("加签结果:");
		System.out.println(Base64Utils.encode(signatureByte));
		//验签
		boolean verifyResult = verify(plain, signatureByte);
		System.out.println("验签结果:" + verifyResult);
		System.out.println(Arrays.toString(decrypt(signatureByte)));
	}

	private static void init() throws GeneralSecurityException {
		if (PUBLIC_KEY == null) {
			PUBLIC_KEY = getPublicKey(PUBLIC_KEY_STR);
		}
		if (PRIVATE_KEY == null) {
			PRIVATE_KEY = getPrivateKey(PRIVATE_KEY_STR);
		}
	}

	/**
	 * 获取私钥
	 *
	 * @return 公钥
	 */
	private static PrivateKey getPrivateKey() throws GeneralSecurityException {
		init();
		return PRIVATE_KEY;
	}

	/**
	 * 获取公钥
	 *
	 * @return 公钥
	 */
	public static PublicKey getPublicKey() throws GeneralSecurityException {
		init();
		return PUBLIC_KEY;
	}

	/**
	 * 加签/加密
	 *
	 * @param plain 原始报文/需要加签的内容
	 * @return 加签结果
	 * @throws GeneralSecurityException 异常
	 */
	public static byte[] sign(String plain) throws GeneralSecurityException {
		init();
		//根据对应算法，获取签名对象实例
		Signature signature = Signature.getInstance("SHA256WithRSA");
		//获取私钥，加签用的是私钥，私钥一般是在配置文件里面读的，这里为了演示方便，根据私钥字符串生成私钥对象
		PrivateKey privateKey = getPrivateKey();
		//初始化签名对象
		signature.initSign(privateKey);
		//把原始报文更新到对象
		signature.update(plain.getBytes(StandardCharsets.UTF_8));
		//加签
		return signature.sign();
	}

	/**
	 * 解签/解密
	 *
	 * @param signatureByte 加密数据
	 * @return 解密数据
	 * @throws GeneralSecurityException 异常
	 */
	public static byte[] decrypt(byte[] signatureByte) throws GeneralSecurityException {
		String algorithm = "RSA/None/PKCS1Padding";
		Cipher cipher = Cipher.getInstance(algorithm);
		cipher.init(Cipher.DECRYPT_MODE, getPrivateKey());
		return cipher.doFinal(signatureByte);
	}

	/**
	 * 验签方法
	 *
	 * @param plain         原始报文字符串
	 * @param signatureByte 报文签名
	 * @return 结果
	 * @throws GeneralSecurityException 异常
	 */
	public static boolean verify(String plain, byte[] signatureByte) throws GeneralSecurityException {
		//获取公钥
		PublicKey publicKey = getPublicKey();
		//根据对应算法，获取签名对象实例
		Signature signature = Signature.getInstance("SHA256WithRSA");
		//初始化签名对象
		signature.initVerify(publicKey);
		//把原始报文更新到签名对象
		signature.update(plain.getBytes(StandardCharsets.UTF_8));
		//进行验签
		return signature.verify(signatureByte);
	}

	/**
	 * 从字符串中获取公钥
	 *
	 * @param publicKeyStr 公钥字符串
	 * @return 公钥对象
	 * @throws GeneralSecurityException 异常
	 */
	public static PublicKey getPublicKey(String publicKeyStr) throws GeneralSecurityException {
		return getPublicKey(publicKeyStr.getBytes());
	}

	/**
	 * 从字符串中获取私钥
	 *
	 * @param privateKeyStr 私钥字符串
	 * @return 私钥对象
	 * @throws GeneralSecurityException 异常
	 */
	public static PrivateKey getPrivateKey(String privateKeyStr) throws GeneralSecurityException {
		return getPrivateKey(privateKeyStr.getBytes());
	}

	/**
	 * 从文件中读取公钥
	 *
	 * @param filename 公钥保存路径，相对于classpath
	 * @return java.security.PublicKey 公钥对象
	 * @throws IOException,GeneralSecurityException IO异常
	 */
	public static PublicKey readPublicKey(String filename) throws IOException, GeneralSecurityException {
		byte[] bytes = readFile(filename);
		return getPublicKey(bytes);
	}

	/**
	 * 从文件中读取密钥
	 *
	 * @param filename 私钥保存路径，相对于classpath
	 * @return java.security.PrivateKey 私钥对象
	 * @throws IOException,GeneralSecurityException 异常
	 */
	public static PrivateKey readPrivateKey(String filename) throws IOException, GeneralSecurityException {
		byte[] bytes = readFile(filename);
		return getPrivateKey(bytes);

	}

	/**
	 * 根据密文，生存rsa公钥和私钥,并写入指定文件
	 *
	 * @param publicKeyFilename  公钥文件路径
	 * @param privateKeyFilename 私钥文件路径
	 * @param secret             生成密钥的密文
	 * @param keySize            键长度，如果小于2048，取2048
	 * @throws IOException,NoSuchAlgorithmException 异常
	 */
	public static void generateKey(String publicKeyFilename, String privateKeyFilename, String secret, int keySize) throws NoSuchAlgorithmException, IOException {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		SecureRandom secureRandom = new SecureRandom(secret.getBytes());
		keyPairGenerator.initialize(Math.max(keySize, DEFAULT_KEY_SIZE), secureRandom);
		KeyPair keyPair = keyPairGenerator.genKeyPair();
		// 获取公钥并写出
		byte[] publicKeyBytes = keyPair.getPublic().getEncoded();
		publicKeyBytes = java.util.Base64.getEncoder().encode(publicKeyBytes);
		writeFile(publicKeyFilename, publicKeyBytes);
		// 获取私钥并写出
		byte[] privateKeyBytes = keyPair.getPrivate().getEncoded();
		privateKeyBytes = java.util.Base64.getEncoder().encode(privateKeyBytes);
		writeFile(privateKeyFilename, privateKeyBytes);
	}

	/**
	 * 获取公钥
	 *
	 * @param bytes 公钥的字节形式
	 * @return java.security.PublicKey 公钥对象
	 * @throws GeneralSecurityException 异常
	 */
	private static PublicKey getPublicKey(byte[] bytes) throws GeneralSecurityException {
		bytes = Base64Utils.decode(bytes);
		X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);
		KeyFactory factory = KeyFactory.getInstance("RSA");
		return factory.generatePublic(spec);
	}

	/**
	 * 获取密钥
	 *
	 * @param bytes 私钥的字节形式
	 * @return java.security.PrivateKey
	 * @throws GeneralSecurityException 异常
	 */
	private static PrivateKey getPrivateKey(byte[] bytes) throws GeneralSecurityException {
		bytes = Base64Utils.decode(bytes);
		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(bytes);
		KeyFactory factory = KeyFactory.getInstance("RSA");
		return factory.generatePrivate(spec);
	}

	/**
	 * 读文件
	 *
	 * @param fileName 文件地址
	 * @return byte[]
	 * @throws IOException IO异常
	 */
	private static byte[] readFile(String fileName) throws IOException {
		return Files.readAllBytes(new File(fileName).toPath());
	}

	/**
	 * 写文件
	 *
	 * @param destPath 文件地址名称
	 * @param bytes    数据
	 * @throws IOException IO异常
	 */
	private static void writeFile(String destPath, byte[] bytes) throws IOException {
		File dest = new File(destPath);
		if (!dest.exists()) {
			if (!dest.createNewFile()) {
				throw new IOException("文件创建失败");
			}
		}
		Files.write(dest.toPath(), bytes);
	}
}
