package com.wobangkj.utils;

import org.jetbrains.annotations.NotNull;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

    // 密码盐
    public static final String PWD_SALT = "PWD_SALF";

    /**
     * 私有构造方法,将该工具类设为单例模式.
     */
    private EncryptUtils() {
        throw new UnsupportedOperationException();
    }

    /**
     * 用MD5算法进行加密
     * 等效于{@link} com.wobangkj.util.Md5Utils.encode32()
     * @param str 需要加密的字符串
     * @return MD5加密后的结果
     */
    @NotNull
    public static String encodeMd5String(String str) {
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
    public static String encodeSha1String(String str) {
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
    public static String encodeSha256String(String str) {
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
    public static String encodeBase64String(@NotNull String str) {
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
    public static String decodeBase64String(String str) {
        Decoder encoder = Base64.getDecoder();
        return new String(encoder.decode(str));
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
