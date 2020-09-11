package com.wobangkj.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

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

	//公钥字符串
	private static final String PUBLIC_KEY_STR = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDaJzVjC5K6kbS2YE2fiDs6H8pB\n" +
			"JFDGEYqqJJC9I3E0Ebr5FsofdImV5eWdBSeADwcR9ppNbpORdZmcX6SipogKx9PX\n" +
			"5aAO4GPesroVeOs91xrLEGt/arteW8iSD+ZaGDUVV3+wcEdci/eCvFlc5PUuZJou\n" +
			"M2XZaDK4Fg2IRTfDXQIDAQAB";
	//私钥字符串
	private static final String PRIVATE_KEY_STR = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBANonNWMLkrqRtLZg\n" +
			"TZ+IOzofykEkUMYRiqokkL0jcTQRuvkWyh90iZXl5Z0FJ4APBxH2mk1uk5F1mZxf\n" +
			"pKKmiArH09floA7gY96yuhV46z3XGssQa39qu15byJIP5loYNRVXf7BwR1yL94K8\n" +
			"WVzk9S5kmi4zZdloMrgWDYhFN8NdAgMBAAECgYA9bz1Bn0i68b2KfqRdgOfs/nbe\n" +
			"0XNN1DLQp2t7WDfRCg01iI1zPkZgyFVZWtI85f5/uIrLs5ArLosL1oNuqqc0nNne\n" +
			"CvJK+ZxvA98Hx3ZqYTzDnleR054YhofL5awbhSciYVic204DOG1rhSsYWMqtX7J7\n" +
			"3geoWL7TYdMfYXcCAQJBAPMMKsz6ZJh98EeQ1tDG5gpAGWFQkYNrxZDelP/LjeO0\n" +
			"TP3XkQnIpcaZoCs7V/rRGRGMWwQ2BUdc/01in89ZZ5ECQQDlx2oBc1CtOAm2UAhN\n" +
			"1xWrPkZWENQ53wTrwXO4qbTGDfBKon0AehLlGCSqxQ71aufLkNO7ZlX0IHTAlnk1\n" +
			"TvENAkAGSEQ69CXxgx/Y2beTwfBkR2/gghKg0QJUUkyLqBlMz3ZGAXJwTE1sqr/n\n" +
			"HiuSAiGhwH0ByNuuEotO1sPGukrhAkAMK26a2w+nzPL+u+hkrwKPykGRZ1zGH+Cz\n" +
			"19AYNKzFXJGgclCqiMydY5T1knBDYUEbj/UW1Mmyn1FvrciHoUG1AkAEMEIuDauz\n" +
			"JabEAU08YmZw6OoDGsukRWaPfjOEiVhH88p00veM1R37nwhoDMGyEGXVeVzNPvk7\n" +
			"cELg28MSRzCK";


	public static void main(String[] args) throws SignatureException, NoSuchAlgorithmException, InvalidKeyException, IOException, InvalidKeySpecException {
		//原始报文
		String plain = "欢迎大家关注我的公众号，捡田螺的小男孩";
		//加签
		byte[] signatureByte = sign(plain);
		System.out.println("原始报文是:" + plain);
		System.out.println("加签结果:");
		System.out.println(Base64Utils.encode(signatureByte));
		//验签
		boolean verifyResult = verify(plain, signatureByte);
		System.out.println("验签结果:" + verifyResult);
	}

	/**
	 * 加签方法
	 *
	 * @param plain 需要加签的内容
	 * @return 加签结果
	 * @throws NoSuchAlgorithmException 异常
	 * @throws InvalidKeyException      异常
	 * @throws SignatureException       异常
	 */
	private static byte[] sign(String plain) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, InvalidKeySpecException, IOException {
		//根据对应算法，获取签名对象实例
		Signature signature = Signature.getInstance("SHA256WithRSA");
		//获取私钥，加签用的是私钥，私钥一般是在配置文件里面读的，这里为了演示方便，根据私钥字符串生成私钥对象
		PrivateKey privateKey = getPrivateKey(PRIVATE_KEY_STR);
		//初始化签名对象
		signature.initSign(privateKey);
		//把原始报文更新到对象
		signature.update(plain.getBytes(StandardCharsets.UTF_8));
		//加签
		return signature.sign();
	}

	/**
	 * 验签方法
	 *
	 * @param plain         字符串
	 * @param signatureByte 签名
	 * @return 结果
	 * @throws NoSuchAlgorithmException 异常
	 * @throws InvalidKeyException      异常
	 * @throws IOException              异常
	 * @throws SignatureException       异常
	 * @throws InvalidKeySpecException  异常
	 */
	private static boolean verify(String plain, byte[] signatureByte) throws NoSuchAlgorithmException, InvalidKeyException, IOException, SignatureException, InvalidKeySpecException {
		//获取公钥
		PublicKey publicKey = getPublicKey(PUBLIC_KEY_STR);
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
	 * 从文件中获取公钥
	 *
	 * @param publicKeyStr 公钥字符串
	 * @return 公钥对象
	 * @throws InvalidKeySpecException  异常
	 * @throws IOException              异常
	 * @throws NoSuchAlgorithmException 异常
	 */
	private static PublicKey getPublicKey(String publicKeyStr) throws InvalidKeySpecException, IOException, NoSuchAlgorithmException {
		java.security.spec.X509EncodedKeySpec bobPubKeySpec = new java.security.spec.X509EncodedKeySpec(
				Base64Utils.decode(publicKeyStr));
		// RSA对称加密算法
		java.security.KeyFactory keyFactory;
		keyFactory = java.security.KeyFactory.getInstance("RSA");
		// 生成公钥对象
		return keyFactory.generatePublic(bobPubKeySpec);
	}

	/**
	 * 获取配置文件
	 *
	 * @param privateKeyStr 私钥字符串
	 * @return 私钥对象
	 * @throws InvalidKeySpecException  异常
	 * @throws NoSuchAlgorithmException 异常
	 * @throws IOException              异常
	 */
	private static PrivateKey getPrivateKey(String privateKeyStr) throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {
		PKCS8EncodedKeySpec priPKCS8;
		priPKCS8 = new PKCS8EncodedKeySpec(Base64Utils.decode(privateKeyStr));
		KeyFactory keyfactory = KeyFactory.getInstance("RSA");
		return keyfactory.generatePrivate(priPKCS8);
	}
}
