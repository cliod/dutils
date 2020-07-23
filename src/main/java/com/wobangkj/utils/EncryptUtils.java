package com.wobangkj.utils;

import org.jetbrains.annotations.NotNull;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

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
        return Base64.getEncoder().encodeToString(str.getBytes());
    }

    /**
     * 用base64算法进行解密
     *
     * @param str 需要解密的字符串
     * @return base64解密后的结果
     */
    @NotNull
    public static String decodeBase64(String str) {
        return new String(Base64.getDecoder().decode(str));
    }

    /**
     * 加密
     *
     * @param content 字符串内容
     * @return 加密结果
     */
    public static @NotNull String encodeAse(String content) {
        try {
            return AseUtils.encrypt(content, AseUtils.AES_KEY, AseUtils.AES_IV);
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    /**
     * AES 解密操作
     *
     * @param content 加密内容
     * @return 解密结果
     */
    public static @NotNull String decodeAse(String content) {
        try {
            return AseUtils.decrypt(content, AseUtils.AES_KEY, AseUtils.AES_IV);
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
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
    public static @NotNull String encodeAse(String encodeRules, String content) {
        try {
            return AseUtils.encrypt(content, encodeRules, AseUtils.AES_IV);
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
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
    public static @NotNull String decodeAse(String encodeRules, String content) {
        try {
            return AseUtils.decrypt(content, encodeRules, AseUtils.AES_IV);
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
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

    // https://blog.csdn.net/chengbinbbs/article/details/78640589
}
