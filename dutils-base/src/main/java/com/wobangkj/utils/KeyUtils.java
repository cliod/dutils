package com.wobangkj.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * @author Cliod
 * @since 2019/6/2
 */
public class KeyUtils {

    /**
     * 随机数
     *
     * @param len 长度
     * @return 随机数
     */
    public static @NotNull String randNum(int len) {
        return Integer.toString((int) ((Math.random() * 9 + 1) * Math.pow(10, len - 1)));
    }

    /**
     * 解密 utf8
     *
     * @param value 待解密字符
     * @return 解密字符
     */
    public static @NotNull String decode(String value) {
        if (BeanUtils.isEmpty(value)) {
            return "";
        }
        return new String(Base64.decodeBase64(value), StandardCharsets.UTF_8);
    }

    /**
     * 解密 utf8
     *
     * @param value 待解密字符
     * @return 解密字符
     */
    public static @NotNull String decode(byte[] value) {
        if (Objects.isNull(value) || value.length == 0) {
            return "";
        }
        return new String(Base64.decodeBase64(value), StandardCharsets.UTF_8);
    }

    /**
     * 加密 utf8
     *
     * @param value 待加密字符
     * @return 加密字符
     */
    public static @NotNull String encode(String value) {
        if (BeanUtils.isEmpty(value)) {
            return "";
        }
        return new String(Base64.encodeBase64(value.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
    }

    /**
     * 加密 utf8
     *
     * @param value 待加密字符
     * @return 加密字符
     */
    public static @NotNull String encode(byte[] value) {
        if (BeanUtils.isEmpty(value)) {
            return "";
        }
        return new String(Base64.encodeBase64(value), StandardCharsets.UTF_8);
    }

    /**
     * SHA-265 机密
     * 等效于{@link} com.wobangkj.util.EncryptUtils.encodeSha256String()
     *
     * @param value 字符串
     * @return 加密结果
     */
    public static @NotNull String encrypt(String value) {
        String encodeStr = "";
        if (BeanUtils.isEmpty(value)) {
            return encodeStr;
        }
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(value.getBytes(StandardCharsets.UTF_8));
            encodeStr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return encodeStr;
    }

    /**
     * MD5 计算
     * 等效于{@link} com.wobangkj.util.Md5Utils.encode32()
     *
     * @param value 字符串
     * @return MD5值
     */
    public static @NotNull String md5(@NotNull String value) {
        return DigestUtils.md5Hex(value.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 将byte转为16进制
     *
     * @param bytes 字符byte
     * @return 加密字符串
     */
    private static @NotNull String byte2Hex(@NotNull byte[] bytes) {
        StringBuilder stringBuffer = new StringBuilder();
        String temp;
        for (byte b : bytes) {
            temp = Integer.toHexString(b & 0xFF);
            if (temp.length() == 1) {
                // 1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }

    /**
     * 生成order id
     *
     * @return id
     */
    @NotNull
    public static String generateTimeKey() {
        return System.currentTimeMillis() + randNum(6);
    }

    /**
     * 函数功能说明 ： 获得文件名的后缀名. 修改者名字： 修改日期： 修改内容：
     *
     * @param fileName 文件名
     * @return 扩展名
     */
    @NotNull
    public static String getExt(@NotNull String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    /**
     * 获取去掉横线的长度为32的UUID串.
     *
     * @return uuid.
     * @author WuShuicheng.
     */
    @NotNull
    public static String get32uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 获取带横线的长度为36的UUID串.
     *
     * @return uuid.
     * @author WuShuicheng.
     */
    public static @NotNull String get36uuid() {
        return UUID.randomUUID().toString();
    }

    /**
     * 验证一个字符串是否完全由纯数字组成的字符串，当字符串为空时也返回false.
     *
     * @param str 要判断的字符串 .
     * @return true or false .
     * @author WuShuicheng .
     */
    public static boolean isNumeric(String str) {
        if (StringUtils.isBlank(str)) {
            return false;
        } else {
            return str.matches("\\d*");
        }
    }

    /**
     * 计算采用utf-8编码方式时字符串所占字节数
     *
     * @param content 内容
     * @return 字节数
     */
    public static int getByteSize(String content) {
        int size = 0;
        if (null != content) {
            // 汉字采用utf-8编码时占3个字节
            size = content.getBytes(StandardCharsets.UTF_8).length;
        }
        return size;
    }

    /**
     * 函数功能说明 ： 截取字符串拼接in查询参数.
     *
     * @param param 参数
     * @return String
     */
    public static @NotNull List<String> getInParam(@NotNull String param) {
        boolean flag = param.contains(",");
        List<String> list = new ArrayList<>();
        if (flag) {
            list = Arrays.asList(param.split(","));
        } else {
            list.add(param);
        }
        return list;
    }
}
