package com.wobangkj.utils;

import javax.crypto.Cipher;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * rsa非对称加密。私钥加密 & 私钥解密 & 私钥签名
 *
 * @author cliod
 * @since 10/17/20 10:48 AM
 */
public class RSAUtils {

	public static final String KEY_ALGORITHM = "RSA";
	/**
	 * 分隔符
	 */
	public static final String SPLIT = " ";
	/**
	 * 加密分段长度//不可超过117
	 */
	public static final int MAX = 117;
	private static RSAUtils INSTALL;
	private RSAPublicKey publicKey;
	private RSAPrivateCrtKey privateKey;

	private RSAUtils() {
	}//单例

	public static void main(String... args) {
		RSAUtils rsa = RSAUtils.create();
		String pubKey = rsa.getPublicKey();
		String priKey = rsa.getPrivateKey();

		System.out.println("公钥:" + pubKey);
		System.out.println("私钥:" + priKey);

		//原文
		StringBuffer res = new StringBuffer();

		res.append("123456");
		System.out.println("原文对比:" + res.toString());
		System.out.println("-----------------------");

		String enStr = rsa.encodeByPublicKey(res.toString(), pubKey);
		String deStr = rsa.decodeByPrivateKey(enStr, priKey);
		System.out.println("公钥加密:" + enStr);
		System.out.println("私钥解密:" + deStr);

		System.out.println("------------------------");
		enStr = rsa.encodeByPrivateKey(res.toString(), priKey);
		deStr = rsa.decodeByPublicKey(enStr, pubKey);
		System.out.println("私钥加密:" + enStr);
		System.out.println("公钥解密:" + deStr);
	}

	/**
	 * RSA 公钥 私钥对
	 *
	 * @return RSA工具
	 */
	public static RSAUtils create() {
		if (INSTALL == null) {
			INSTALL = new RSAUtils();
		}
		//生成公钥、私钥
		try {
			KeyPairGenerator kpg = KeyPairGenerator.getInstance(KEY_ALGORITHM);
			kpg.initialize(512);
			KeyPair kp = kpg.generateKeyPair();
			INSTALL.publicKey = (RSAPublicKey) kp.getPublic();
			INSTALL.privateKey = (RSAPrivateCrtKey) kp.getPrivate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return INSTALL;
	}

	/**
	 * 将二进制转换成16进制
	 */
	private static String parseByte2HexStr(byte[] bytes) {
		StringBuffer sb = new StringBuffer();
		for (byte b : bytes) {
			String hex = Integer.toHexString(b & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * 将16进制转换为二进制
	 */
	private static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1) {
			return null;
		}
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

	/**
	 * 获取公钥
	 */
	public String getPublicKey() {
		return parseByte2HexStr(publicKey.getEncoded());
	}

	/**
	 * 获取私钥
	 */
	public String getPrivateKey() {
		return parseByte2HexStr(privateKey.getEncoded());
	}

	/**
	 * 加密-公钥
	 */
	public String encodeByPublicKey(String res, String key) {
		byte[] resBytes = res.getBytes();
		//先把公钥转为2进制
		byte[] keyBytes = parseHexStr2Byte(key);
		//结果
		StringBuffer result = new StringBuffer();
		//如果超过了100个字节就分段
		if (keyBytes.length <= MAX) {
			//不超过直接返回即可
			return encodePub(resBytes, keyBytes);
		} else {
			int size = resBytes.length / MAX + (resBytes.length % MAX > 0 ? 1 : 0);
			for (int i = 0; i < size; i++) {
				int len = i == size - 1 ? resBytes.length % MAX : MAX;
				//临时数组
				byte[] bs = new byte[len];
				System.arraycopy(resBytes, i * MAX, bs, 0, len);
				result.append(encodePub(bs, keyBytes));
				if (i != size - 1) {
					result.append(SPLIT);
				}
			}
			return result.toString();
		}
	}

	/**
	 * 加密-私钥
	 */
	public String encodeByPrivateKey(String res, String key) {
		byte[] resBytes = res.getBytes();
		byte[] keyBytes = parseHexStr2Byte(key);
		StringBuffer result = new StringBuffer();
		//如果超过了100个字节就分段
		if (keyBytes.length <= MAX) {
			//不超过直接返回即可
			return encodePri(resBytes, keyBytes);
		} else {
			int size = resBytes.length / MAX + (resBytes.length % MAX > 0 ? 1 : 0);
			for (int i = 0; i < size; i++) {
				int len = i == size - 1 ? resBytes.length % MAX : MAX;
				//临时数组
				byte[] bs = new byte[len];
				System.arraycopy(resBytes, i * MAX, bs, 0, len);
				result.append(encodePri(bs, keyBytes));
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
	public String decodeByPublicKey(String res, String key) {
		byte[] keyBytes = parseHexStr2Byte(key);
		//先分段
		String[] rs = res.split("\\" + SPLIT);
		//分段解密
		if (rs != null) {
			int len = 0;
			//组合byte[]
			byte[] result = new byte[rs.length * MAX];
			for (int i = 0; i < rs.length; i++) {
				byte[] bs = decodePub(parseHexStr2Byte(rs[i]), keyBytes);
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
	public String decodeByPrivateKey(String res, String key) {
		byte[] keyBytes = parseHexStr2Byte(key);
		//先分段
		String[] rs = res.split("\\" + SPLIT);
		//分段解密
		if (rs != null) {
			int len = 0;
			//组合byte[]
			byte[] result = new byte[rs.length * MAX];
			for (int i = 0; i < rs.length; i++) {
				byte[] bs = decodePri(parseHexStr2Byte(rs[i]), keyBytes);
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
	 * 加密-公钥-无分段
	 */
	private String encodePub(byte[] res, byte[] keyBytes) {
		//用2进制的公钥生成x509
		X509EncodedKeySpec x5 = new X509EncodedKeySpec(keyBytes);
		try {
			KeyFactory kf = KeyFactory.getInstance(KEY_ALGORITHM);
			//用KeyFactory把x509生成公钥pubKey
			Key pubKey = kf.generatePublic(x5);
			//生成相应的Cipher
			Cipher cp = Cipher.getInstance(kf.getAlgorithm());
			//给cipher初始化为加密模式，以及传入公钥pubKey
			cp.init(Cipher.ENCRYPT_MODE, pubKey);
			//以16进制的字符串返回
			return parseByte2HexStr(cp.doFinal(res));
		} catch (Exception e) {
			System.out.println("公钥加密失败");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 加密-私钥-无分段
	 */
	private String encodePri(byte[] res, byte[] keyBytes) {
		PKCS8EncodedKeySpec pk8 = new PKCS8EncodedKeySpec(keyBytes);
		try {
			KeyFactory kf = KeyFactory.getInstance(KEY_ALGORITHM);
			Key priKey = kf.generatePrivate(pk8);
			Cipher cp = Cipher.getInstance(kf.getAlgorithm());
			cp.init(Cipher.ENCRYPT_MODE, priKey);
			return parseByte2HexStr(cp.doFinal(res));
		} catch (Exception e) {
			System.out.println("私钥加密失败");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解密-公钥-无分段
	 */
	private byte[] decodePub(byte[] res, byte[] keyBytes) {
		X509EncodedKeySpec x5 = new X509EncodedKeySpec(keyBytes);
		try {
			KeyFactory kf = KeyFactory.getInstance(KEY_ALGORITHM);
			Key pubKey = kf.generatePublic(x5);
			Cipher cp = Cipher.getInstance(kf.getAlgorithm());
			cp.init(Cipher.DECRYPT_MODE, pubKey);
			return cp.doFinal(res);
		} catch (Exception e) {
			System.out.println("公钥解密失败");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解密-私钥-无分段
	 */
	private byte[] decodePri(byte[] res, byte[] keyBytes) {
		PKCS8EncodedKeySpec pk8 = new PKCS8EncodedKeySpec(keyBytes);
		try {
			KeyFactory kf = KeyFactory.getInstance(KEY_ALGORITHM);
			Key priKey = kf.generatePrivate(pk8);
			Cipher cp = Cipher.getInstance(kf.getAlgorithm());
			cp.init(Cipher.DECRYPT_MODE, priKey);
			return cp.doFinal(res);
		} catch (Exception e) {
			System.out.println("私钥解密失败");
			e.printStackTrace();
		}
		return null;
	}
}
