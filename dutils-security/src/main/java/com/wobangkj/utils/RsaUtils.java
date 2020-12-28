package com.wobangkj.utils;

import javax.crypto.Cipher;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * 非对称加密。私钥加密 & 私钥解密 & 私钥签名
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

	private static final String KEY_ALGORITHM = "RSA";
	/**
	 * 分隔符
	 */
	private static final String SPLIT = " ";
	/**
	 * 加密分段长度//不可超过117
	 */
	private static final int MAX = 117;
	/**
	 * 公钥字符串
	 */
	private static final String DEFAULT_PUBLIC_KEY_STR =
			"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDaJzVjC5K6kbS2YE2fiDs6H8pB" +
					"JFDGEYqqJJC9I3E0Ebr5FsofdImV5eWdBSeADwcR9ppNbpORdZmcX6SipogKx9PX" +
					"5aAO4GPesroVeOs91xrLEGt/arteW8iSD+ZaGDUVV3+wcEdci/eCvFlc5PUuZJou" +
					"M2XZaDK4Fg2IRTfDXQIDAQAB";
	/**
	 * 私钥字符串
	 */
	private static final String DEFAULT_PRIVATE_KEY_STR =
			"MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBANonNWMLkrqRtLZg" +
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
	private static RSAPublicKey PUBLIC_KEY = null;
	private static RSAPrivateKey PRIVATE_KEY = null;

	public static void init() throws GeneralSecurityException {
		if (PUBLIC_KEY == null) {
			PUBLIC_KEY = getPublicKey(DEFAULT_PUBLIC_KEY_STR);
		}
		if (PRIVATE_KEY == null) {
			PRIVATE_KEY = getPrivateKey(DEFAULT_PRIVATE_KEY_STR);
		}
	}

	/**
	 * 获取公钥
	 */
	public static String getRsaPublicKey() throws GeneralSecurityException {
		return HexUtils.bytes2Hex(getPublicKey().getEncoded());
	}

	/**
	 * 获取私钥
	 */
	public static String getRsaPrivateKey() throws GeneralSecurityException {
		return HexUtils.bytes2Hex(getPrivateKey().getEncoded());
	}

	/**
	 * 通过公钥加密
	 *
	 * @param res 原始报文数据
	 * @param key 公钥
	 * @return 结果
	 */
	public static String encryptByPublicKey(String res, String key) throws GeneralSecurityException {
		byte[] resBytes = res.getBytes();
		//先把公钥转为2进制
		byte[] keyBytes = HexUtils.hex2Bytes(key);
		//结果
		StringBuilder result = new StringBuilder();
		//如果超过了100个字节就分段
		if (keyBytes.length <= MAX) {
			//不超过直接返回即可
			return encodePublic(resBytes, keyBytes);
		} else {
			int size = resBytes.length / MAX + (resBytes.length % MAX > 0 ? 1 : 0);
			for (int i = 0; i < size; i++) {
				int len = i == size - 1 ? resBytes.length % MAX : MAX;
				//临时数组
				byte[] bs = new byte[len];
				System.arraycopy(resBytes, i * MAX, bs, 0, len);
				result.append(encodePublic(bs, keyBytes));
				if (i != size - 1) {
					result.append(SPLIT);
				}
			}
			return result.toString();
		}
	}

	/**
	 * 通过私钥加密
	 *
	 * @param res 原始报文数据
	 * @param key 私钥
	 * @return 结果
	 */
	public static String encryptByPrivateKey(String res, String key) throws GeneralSecurityException {
		byte[] resBytes = res.getBytes();
		byte[] keyBytes = HexUtils.hex2Bytes(key);
		StringBuilder result = new StringBuilder();
		//如果超过了100个字节就分段
		if (keyBytes.length <= MAX) {
			//不超过直接返回即可
			return encodePrivate(resBytes, keyBytes);
		} else {
			int size = resBytes.length / MAX + (resBytes.length % MAX > 0 ? 1 : 0);
			for (int i = 0; i < size; i++) {
				int len = i == size - 1 ? resBytes.length % MAX : MAX;
				//临时数组
				byte[] bs = new byte[len];
				System.arraycopy(resBytes, i * MAX, bs, 0, len);
				result.append(encodePrivate(bs, keyBytes));
				if (i != size - 1) {
					result.append(SPLIT);
				}
			}
			return result.toString();
		}
	}

	/**
	 * 解密-公钥
	 */
	public static String decryptByPublicKey(String res, String key) throws GeneralSecurityException {
		byte[] keyBytes = HexUtils.hex2Bytes(key);
		//先分段
		String[] rs = res.split("\\" + SPLIT);
		//分段解密
		if (rs != null) {
			int len = 0;
			//组合byte[]
			byte[] result = new byte[rs.length * MAX];
			for (int i = 0; i < rs.length; i++) {
				byte[] bs = decodePublic(HexUtils.hex2Bytes(rs[i]), keyBytes);
				System.arraycopy(bs, 0, result, i * MAX, bs.length);
				len += bs.length;
			}
			byte[] newResult = new byte[len];
			System.arraycopy(result, 0, newResult, 0, len);
			//还原字符串
			return new String(newResult);
		}
		return null;
	}

	/**
	 * 解密-私钥
	 */
	public static String decryptByPrivateKey(String res, String key) throws GeneralSecurityException {
		byte[] keyBytes = HexUtils.hex2Bytes(key);
		//先分段
		String[] rs = res.split("\\" + SPLIT);
		//分段解密
		if (rs != null) {
			int len = 0;
			//组合byte[]
			byte[] result = new byte[rs.length * MAX];
			for (int i = 0; i < rs.length; i++) {
				byte[] bs = decodePrivate(HexUtils.hex2Bytes(rs[i]), keyBytes);
				System.arraycopy(bs, 0, result, i * MAX, bs.length);
				len += bs.length;
			}
			byte[] newResult = new byte[len];
			System.arraycopy(result, 0, newResult, 0, len);
			//还原字符串
			return new String(newResult);
		}
		return null;
	}

	/**
	 * 通过公钥加密
	 *
	 * @param res 原始报文数据
	 * @param key 公钥
	 * @return 结果
	 */
	public static String encryptByPublicKey(String res, RSAPublicKey key) throws GeneralSecurityException {
		return encryptByPublicKey(res, HexUtils.bytes2Hex(key.getEncoded()));
	}

	/**
	 * 通过私钥加密
	 *
	 * @param res 原始报文数据
	 * @param key 私钥
	 * @return 结果
	 */
	public static String encryptByPrivateKey(String res, RSAPrivateKey key) throws GeneralSecurityException {
		return encryptByPrivateKey(res, HexUtils.bytes2Hex(key.getEncoded()));
	}

	/**
	 * 解密-公钥
	 */
	public static String decryptByPublicKey(String res, RSAPublicKey key) throws GeneralSecurityException {
		return decryptByPublicKey(res, HexUtils.bytes2Hex(key.getEncoded()));
	}

	/**
	 * 解密-私钥
	 */
	public static String decryptByPrivateKey(String res, RSAPrivateKey key) throws GeneralSecurityException {
		return decryptByPrivateKey(res, HexUtils.bytes2Hex(key.getEncoded()));
	}

	/**
	 * 加密-公钥-无分段
	 *
	 * @param res      原始报文数据
	 * @param keyBytes 公钥
	 * @return 解密结果
	 */
	private static String encodePublic(byte[] res, byte[] keyBytes) throws GeneralSecurityException {
		//用2进制的公钥生成x509
		X509EncodedKeySpec x5 = new X509EncodedKeySpec(keyBytes);
		KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
		//用KeyFactory把x509生成公钥pubKey
		Key pubKey = factory.generatePublic(x5);
		//生成相应的Cipher
		Cipher cipher = Cipher.getInstance(factory.getAlgorithm());
		//给cipher初始化为加密模式，以及传入公钥pubKey
		cipher.init(Cipher.ENCRYPT_MODE, pubKey);
		//以16进制的字符串返回
		return HexUtils.bytes2Hex(cipher.doFinal(res));
	}

	/**
	 * 加密-私钥-无分段
	 *
	 * @param res      原始报文数据
	 * @param keyBytes 秘钥
	 * @return 解密结果
	 */
	private static String encodePrivate(byte[] res, byte[] keyBytes) throws GeneralSecurityException {
		PKCS8EncodedKeySpec pk8 = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key priKey = factory.generatePrivate(pk8);
		Cipher cipher = Cipher.getInstance(factory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, priKey);
		return HexUtils.bytes2Hex(cipher.doFinal(res));
	}

	/**
	 * 解密-公钥-无分段
	 *
	 * @param res      原始报文数据
	 * @param keyBytes 公钥
	 * @return 解密结果
	 */
	private static byte[] decodePublic(byte[] res, byte[] keyBytes) throws GeneralSecurityException {
		X509EncodedKeySpec x5 = new X509EncodedKeySpec(keyBytes);
		KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key pubKey = factory.generatePublic(x5);
		Cipher cipher = Cipher.getInstance(factory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, pubKey);
		return cipher.doFinal(res);
	}

	/**
	 * 解密-私钥-无分段
	 *
	 * @param res      原始报文数据
	 * @param keyBytes 秘钥
	 * @return 解密结果
	 */
	private static byte[] decodePrivate(byte[] res, byte[] keyBytes) throws GeneralSecurityException {
		PKCS8EncodedKeySpec pk8 = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key priKey = factory.generatePrivate(pk8);
		Cipher cipher = Cipher.getInstance(factory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, priKey);
		return cipher.doFinal(res);
	}

	/**
	 * 获取私钥
	 *
	 * @return 公钥
	 */
	private static RSAPrivateKey getPrivateKey() throws GeneralSecurityException {
		init();
		return PRIVATE_KEY;
	}

	/**
	 * 获取公钥
	 *
	 * @return 公钥
	 */
	public static RSAPublicKey getPublicKey() throws GeneralSecurityException {
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
	public static RSAPublicKey getPublicKey(String publicKeyStr) throws GeneralSecurityException {
		return getPublicKey(publicKeyStr.getBytes());
	}

	/**
	 * 从字符串中获取私钥
	 *
	 * @param privateKeyStr 私钥字符串
	 * @return 私钥对象
	 * @throws GeneralSecurityException 异常
	 */
	public static RSAPrivateKey getPrivateKey(String privateKeyStr) throws GeneralSecurityException {
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
	 * @param keySize            键长度，如果小于2048，取2048，不宜太大，避免计算时间过长
	 * @throws IOException,NoSuchAlgorithmException 异常
	 */
	public static void generateKey(String publicKeyFilename, String privateKeyFilename, String secret, int keySize) throws NoSuchAlgorithmException, IOException {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
		SecureRandom secureRandom = new SecureRandom(secret.getBytes());
		keyPairGenerator.initialize(Math.max(keySize, DEFAULT_KEY_SIZE), secureRandom);
		KeyPair keyPair = keyPairGenerator.genKeyPair();
		// 获取公钥并写出
		PUBLIC_KEY = (RSAPublicKey) keyPair.getPublic();
		writeFile(publicKeyFilename, Base64Utils.encode(PUBLIC_KEY.getEncoded()));
		// 获取私钥并写出
		PRIVATE_KEY = (RSAPrivateKey) keyPair.getPrivate();
		writeFile(privateKeyFilename, Base64Utils.encode(PRIVATE_KEY.getEncoded()));
	}

	/**
	 * 获取公钥
	 *
	 * @param bytes 公钥的字节形式
	 * @return java.security.PublicKey 公钥对象
	 * @throws GeneralSecurityException 异常
	 */
	private static RSAPublicKey getPublicKey(byte[] bytes) throws GeneralSecurityException {
		bytes = Base64Utils.decode(bytes);
		X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);
		KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
		return (RSAPublicKey) factory.generatePublic(spec);
	}

	/**
	 * 获取密钥
	 *
	 * @param bytes 私钥的字节形式
	 * @return java.security.PrivateKey
	 * @throws GeneralSecurityException 异常
	 */
	private static RSAPrivateKey getPrivateKey(byte[] bytes) throws GeneralSecurityException {
		bytes = Base64Utils.decode(bytes);
		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(bytes);
		KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
		return (RSAPrivateKey) factory.generatePrivate(spec);
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

	/**
	 * 通过公钥加密
	 *
	 * @param res 原始报文数据
	 * @return 结果
	 */
	public String encryptByPublicKey(String res) throws GeneralSecurityException {
		return encryptByPublicKey(res, getRsaPublicKey());
	}

	/**
	 * 通过私钥加密
	 *
	 * @param res 原始报文数据
	 * @return 结果
	 */
	public String encryptByPrivateKey(String res) throws GeneralSecurityException {
		return encryptByPublicKey(res, getRsaPrivateKey());
	}

	/**
	 * 解密-公钥
	 */
	public String decryptByPublicKey(String res) throws GeneralSecurityException {
		return decryptByPublicKey(res, getRsaPublicKey());
	}

	/**
	 * 解密-私钥
	 */
	public String decryptByPrivateKey(String res) throws GeneralSecurityException {
		return decryptByPrivateKey(res, getRsaPrivateKey());
	}
}
