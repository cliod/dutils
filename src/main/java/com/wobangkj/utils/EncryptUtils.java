package com.wobangkj.utils;

import org.jetbrains.annotations.NotNull;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

/**
 * 字符串加密
 *
 * @author cliod
 * @since 2019/12/5
 * package : com.wobangkj.util
 */
public class EncryptUtils {

    /**
     * 私有构造方法,将该工具类设为单例模式.
     */
    private EncryptUtils() {
        throw new UnsupportedOperationException();
    }

    /**
     * 用MD5算法进行加密
     * 等效于{@link} com.wobangkj.util.Md5Utils.encode32()
     *
     * @param str 需要加密的字符串
     * @return MD5加密后的结果
     */
    @NotNull
    public static String encodeMd5(String str) {
        try {
            return encode(str, "MD5");
        } catch (NoSuchAlgorithmException e) {
            return "";
        }
    }

    /**
     * 用SHA1算法进行加密
     *
     * @param str 需要加密的字符串
     * @return SHA加密后的结果
     */
    @NotNull
    public static String encodeSha1(String str) {
        try {
            return encode(str, "SHA1");
        } catch (NoSuchAlgorithmException e) {
            return "";
        }
    }

    /**
     * 用SHA235算法进行加密
     *
     * @param str 需要加密的字符串
     * @return SHA加密后的结果
     */
    @NotNull
    public static String encodeSha256(String str) {
        try {
            return encode(str, "SHA-256");
        } catch (NoSuchAlgorithmException e) {
            return "";
        }
    }

    /**
     * 用base64算法进行加密
     *
     * @param str 需要加密的字符串
     * @return base64加密后的结果
     */
    @NotNull
    public static String encodeBase64(@NotNull String str) {
        Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(str.getBytes());
    }

    /**
     * 用base64算法进行解密
     *
     * @param str 需要解密的字符串
     * @return base64解密后的结果
     */
    @NotNull
    public static String decodeBase64(String str) {
        Decoder encoder = Base64.getDecoder();
        return new String(encoder.decode(str));
    }

    /*
     * 加密
     * 1.构造密钥生成器
     * 2.根据encodeRules规则初始化密钥生成器
     * 3.产生密钥
     * 4.创建和初始化密码器
     * 5.内容加密
     * 6.返回字符串
     */
    public static @NotNull String encodeAse(String encodeRules, String content) {
        try {
            Cipher cipher = aseInit(encodeRules);
            //8.获取加密内容的字节数组(这里要设置为utf-8)不然内容中如果有中文和英文混合中文就会解密为乱码
            byte[] byte_encode = content.getBytes(StandardCharsets.UTF_8);
            //9.根据密码器的初始化方式--加密：将数据加密
            byte[] byte_AES = cipher.doFinal(byte_encode);
            //10.将加密后的数据转换为字符串
            return Arrays.toString(Base64.getEncoder().encode(byte_AES));
        } catch (Exception e) {
            return "";
        }
    }

    /*
     * 解密
     * 解密过程：
     * 1.同加密1-4步
     * 2.将加密后的字符串反纺成byte[]数组
     * 3.将加密内容解密
     */
    public static @NotNull String decodeAse(String encodeRules, String content) {
        try {
            Cipher cipher = aseInit(encodeRules);
            //8.将加密并编码后的内容解码成字节数组
            byte[] byte_content = Base64.getDecoder().decode(content);
            // 解密
            byte[] byte_decode = cipher.doFinal(byte_content);
            return new String(byte_decode, StandardCharsets.UTF_8);
        } catch (Exception e) {
            return "";
        }
    }

    private static @NotNull Cipher aseInit(@NotNull String encodeRules) throws Exception {
        //1.构造密钥生成器，指定为AES算法,不区分大小写
        KeyGenerator keygen = KeyGenerator.getInstance("AES");
        //2.根据encodeRules规则初始化密钥生成器
        //生成一个128位的随机源,根据传入的字节数组
        keygen.init(128, new SecureRandom(encodeRules.getBytes()));
        //3.产生原始对称密钥
        SecretKey original_key = keygen.generateKey();
        //4.获得原始对称密钥的字节数组
        byte[] raw = original_key.getEncoded();
        //5.根据字节数组生成AES密钥
        SecretKey key = new SecretKeySpec(raw, "AES");
        //6.根据指定算法AES自成密码器
        Cipher cipher = Cipher.getInstance("AES");
        //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密(Decrypt_mode)操作，第二个参数为使用的KEY
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher;
    }

    @NotNull
    private static String encode(@NotNull String str, String method) throws NoSuchAlgorithmException {
        MessageDigest mdInst;
        // 把密文转换成十六进制的字符串形式
        // 单线程用StringBuilder，速度快 多线程用stringbuffer，安全
        StringBuilder dstr = new StringBuilder();
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
                dstr.append("0");
            }
            dstr.append(Integer.toHexString(tmp));
        }
        return dstr.toString();
    }
}
