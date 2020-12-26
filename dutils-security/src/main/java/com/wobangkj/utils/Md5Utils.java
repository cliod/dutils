package com.wobangkj.utils;

import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * MD5签名工具类
 *
 * @author cliod
 * @since 2019/12/5
 */
public class Md5Utils {

    private static final String[] HEX = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    /**
     * 私有构造方法,将该工具类设为单例模式.
     */
    private Md5Utils() {
        throw new UnsupportedOperationException();
    }

    /**
     * 32位MD5签名值
     *
     * @param password 密码
     * @return 签名
     */
    public static String encode32(String password) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] byteArray = md5.digest(password.getBytes(StandardCharsets.UTF_8));
            return byteArrayToHexString(byteArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return password;
    }

    /**
     * 32位大写MD5签名值
     *
     * @param password 密码
     * @return 签名
     */
    @NotNull
    public static String encode32ToUpperCase(String password) {
        return encode32(password).toUpperCase();
    }

    /**
     * 16位MD5签名值
     *
     * @param password 密码
     * @return 签名
     */
    @NotNull
    public static String encode16(String password) {
        return encode32(password).substring(8, 24);
    }

    /**
     * 16位大写MD5签名值
     *
     * @param password 密码
     * @return 签名
     */
    @NotNull
    public static String encode16ToUpperCase(String password) {
        return encode32ToUpperCase(password).substring(8, 24);
    }

    public static String encode(String password, String enc) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] byteArray = md5.digest(password.getBytes(enc));
            return byteArrayToHexString(byteArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return password;
    }

    @NotNull
    private static String byteArrayToHexString(byte[] byteArray) {
        StringBuffer sb = new StringBuffer();
        for (byte b : byteArray) {
            sb.append(byteToHexChar(b));
        }
        return sb.toString();
    }

    @NotNull
    private static Object byteToHexChar(byte b) {
        int n = b;
        if (n < 0) {
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return HEX[d1] + HEX[d2];
    }
}
