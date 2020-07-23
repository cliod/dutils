package com.wobangkj.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Hashtable;
import java.util.Objects;

/**
 * 二维码生成器
 *
 * @author cliod
 * @since 19-7-24
 * package : com.wobangkj.git.magicked.util
 */
public class QrCodeUtils {
    /**
     * 二维码格式
     */
    public static String FORMAT = "JPG";
    /**
     * 编码
     */
    private static final String CHARSET = "utf-8";
    /**
     * 二维码尺寸
     */
    private static final int QR_CODE_SIZE = 300;
    /**
     * LOGO宽度
     */
    private static final int LOGO_WIDTH = 60;
    /**
     * LOGO高度
     */
    private static final int LOGO_HEIGHT = 60;

    // 需要注意 颜色码需是16进制字符串
    private static int HUA_SE = 0xFF000000; // 码颜色 0xFF000000 黑色
    private static int DI_SE = 0xFFFFFFFF; // 底色 0xFFFFFFFF 白色

    private static boolean DEFAULT_COMPRESS = true;

    public static boolean isDefaultCompress() {
        return DEFAULT_COMPRESS;
    }

    public static boolean getDefaultCompress() {
        return DEFAULT_COMPRESS;
    }

    public static void setDefaultCompress(boolean defaultCompress) {
        DEFAULT_COMPRESS = defaultCompress;
    }

    private QrCodeUtils() {
    }

    /**
     * 获取图片格式
     *
     * @return 设置图片格式
     */
    public static String getFORMAT() {
        return FORMAT;
    }

    public static void setFORMAT(String FORMAT) {
        QrCodeUtils.FORMAT = FORMAT;
    }

    /**
     * 获取码颜色
     *
     * @return 码颜色
     */
    public static int getHuaSe() {
        return HUA_SE;
    }

    public static void setHuaSe(int huaSe) {
        HUA_SE = huaSe;
    }

    /**
     * 获取底色
     *
     * @return 底色
     */
    public static int getDiSe() {
        return DI_SE;
    }

    public static void setDiSe(int diSe) {
        DI_SE = diSe;
    }

    /**
     * 生成二维码
     *
     * @param content 二维码内容
     * @return 图片
     * @throws Exception 异常
     */
    public static @NotNull BufferedImage createImage(String content) throws Exception {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QR_CODE_SIZE, QR_CODE_SIZE,
                hints);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? getHuaSe() : getDiSe());
            }
        }
        return image;
    }

    /**
     * 生成二维码, 并插入LOGO, 不建议使用
     *
     * @param content      二维码内容
     * @param logoPath     logo地址
     * @param needCompress 是否压缩logo
     * @return 图片
     * @throws Exception 异常
     */
    @Deprecated
    public static @NotNull BufferedImage createImage(String content, String logoPath, boolean needCompress) throws Exception {
        BufferedImage image = createImage(content);
        if (StringUtils.isEmpty(logoPath)) {
            return image;
        }
        // 插入图片
        insertImage(image, logoPath, needCompress);
        return image;
    }

    /**
     * 生成二维码, 并插入LOGO
     *
     * @param content      二维码内容
     * @param logoFile     logo文件
     * @param needCompress 是否压缩logo
     * @return 图片
     * @throws Exception 异常
     */
    public static @NotNull BufferedImage createImage(String content, File logoFile, boolean needCompress) throws Exception {
        BufferedImage image = createImage(content);
        if (logoFile == null || logoFile.length() == 0) {
            return image;
        }
        // 插入图片
        insertImage(image, logoFile, needCompress);
        return image;
    }

    /**
     * 生成二维码, 并插入LOGO
     *
     * @param content      二维码内容
     * @param inputStream  logo流
     * @param needCompress 是否压缩logo
     * @return 图片
     * @throws Exception 异常
     */
    public static @NotNull BufferedImage createImage(String content, InputStream inputStream, boolean needCompress) throws Exception {
        BufferedImage image = createImage(content);
        if (inputStream == null || inputStream.available() == 0) {
            return image;
        }
        // 插入图片
        insertImage(image, inputStream, needCompress);
        return image;
    }

    /**
     * 插入LOGO, 不建议使用
     *
     * @param source       二维码图片
     * @param logoPath     LOGO图片地址
     * @param needCompress 是否压缩
     */
    @Deprecated
    public static void insertImage(BufferedImage source, String logoPath, boolean needCompress) {
        try (InputStream inputStream = FileUtils.readFile(logoPath)) {
            insertImage(source, inputStream, needCompress);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 插入LOGO
     *
     * @param source       二维码图片
     * @param logoFile     LOGO图片
     * @param needCompress 是否压缩
     */
    public static void insertImage(BufferedImage source, File logoFile, boolean needCompress) throws IOException {
        InputStream in = null;
        if (Objects.nonNull(logoFile))
            in = new FileInputStream(logoFile);
        insertImage(source, in, needCompress);
    }

    /**
     * 插入LOGO
     *
     * @param source       二维码图片
     * @param inputStream  LOGO图片流
     * @param needCompress 是否压缩
     */
    public static void insertImage(BufferedImage source, InputStream inputStream, boolean needCompress) throws IOException {
        Image src = ImageIO.read(inputStream);
        int width = src.getWidth(null);
        int height = src.getHeight(null);
        // 压缩LOGO
        if (needCompress) {
            if (width > LOGO_WIDTH) {
                width = LOGO_WIDTH;
            }
            if (height > LOGO_HEIGHT) {
                height = LOGO_HEIGHT;
            }
            Image image = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            // 绘制缩小后的图
            g.drawImage(image, 0, 0, null);
            g.dispose();
            src = image;
        }
        // 插入LOGO
        Graphics2D graph = source.createGraphics();
        int x = (QR_CODE_SIZE - width) / 2;
        int y = (QR_CODE_SIZE - height) / 2;
        graph.drawImage(src, x, y, width, height, null);
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }

    /**
     * 生成二维码(内嵌LOGO), 调用者指定二维码文件名
     *
     * @param content      内容
     * @param logoPath     LOGO地址
     * @param destPath     存放目录
     * @param fileName     二维码文件名
     * @param needCompress 是否压缩LOGO
     * @throws Exception 异常
     */
    @Deprecated
    public static @NotNull File encode(@NotNull String content, String logoPath, String destPath, String fileName, boolean needCompress) throws Exception {
        BufferedImage image = createImage(content, logoPath, needCompress);
        File file = createFile(destPath, fileName);
        ImageIO.write(image, FORMAT, file);
        return file;
    }

    /**
     * 生成二维码(内嵌LOGO), 调用者指定二维码文件名
     *
     * @param content      内容
     * @param in           LOGO流
     * @param destPath     存放目录
     * @param fileName     二维码文件名
     * @param needCompress 是否压缩LOGO
     * @throws Exception 异常
     */
    @Deprecated
    public static @NotNull File encode(@NotNull String content, InputStream in, String destPath, String fileName, boolean needCompress) throws Exception {
        BufferedImage image = createImage(content, in, needCompress);
        File file = createFile(destPath, fileName);
        ImageIO.write(image, FORMAT, file);
        return file;
    }

    /**
     * 生成二维码(内嵌LOGO), 二维码文件名随机
     *
     * @param content      内容
     * @param logoPath     LOGO地址
     * @param destPath     存放目录
     * @param needCompress 是否压缩LOGO
     * @throws Exception 异常
     */
    @Deprecated
    public static @NotNull File encode(@NotNull String content, String logoPath, String destPath, boolean needCompress) throws Exception {
        return encode(content, logoPath, destPath, "", needCompress);
    }

    /**
     * 生成二维码(内嵌LOGO), 二维码文件名随机
     *
     * @param content      内容
     * @param in           LOGO流
     * @param destPath     存放目录
     * @param needCompress 是否压缩LOGO
     * @throws Exception 异常
     */
    @Deprecated
    public static @NotNull File encode(@NotNull String content, InputStream in, String destPath, boolean needCompress) throws Exception {
        return encode(content, in, destPath, "", needCompress);
    }

    /**
     * 生成二维码(内嵌LOGO)
     *
     * @param content  内容
     * @param logoPath LOGO地址
     * @param destPath 存储地址
     * @throws Exception 异常
     */
    @Deprecated
    public static @NotNull File encode(@NotNull String content, String logoPath, String destPath) throws Exception {
        return encode(content, logoPath, destPath, isDefaultCompress());
    }

    /**
     * 生成二维码
     *
     * @param content      内容
     * @param logoPath     logo地址
     * @param needCompress 是否压缩LOGO
     * @throws Exception 异常
     */
    @Deprecated
    public static @NotNull File encode(@NotNull String content, String logoPath, boolean needCompress) throws Exception {
        return encode(content, logoPath, "", needCompress);
    }

    /**
     * 生成二维码(内嵌LOGO)
     *
     * @param content      内容
     * @param logoPath     LOGO地址
     * @param file         存放文件
     * @param needCompress 是否压缩LOGO
     * @throws Exception 异常
     */
    @Deprecated
    public static void encode(@NotNull String content, String logoPath, File file, boolean needCompress) throws Exception {
        BufferedImage image = createImage(content, logoPath, needCompress);
        ImageIO.write(image, FORMAT, file);
    }

    /**
     * 生成二维码(内嵌LOGO)
     *
     * @param content      内容
     * @param logoFile     LOGO文件
     * @param file         存放文件
     * @param needCompress 是否压缩LOGO
     * @throws Exception 异常
     */
    public static void encode(@NotNull String content, @Nullable File logoFile, @NotNull File file, boolean needCompress) throws Exception {
        BufferedImage image = createImage(content, logoFile, needCompress);
        ImageIO.write(image, FORMAT, file);
    }

    /**
     * 生成二维码(内嵌LOGO)
     *
     * @param content      内容
     * @param in           LOGO输入流
     * @param file         存放文件
     * @param needCompress 是否压缩LOGO
     * @throws Exception 异常
     */
    public static void encode(@NotNull String content, @Nullable InputStream in, @NotNull File file, boolean needCompress) throws Exception {
        BufferedImage image = createImage(content, in, needCompress);
        ImageIO.write(image, FORMAT, file);
    }

    /**
     * 生成二维码(内嵌LOGO)
     *
     * @param content  内容
     * @param logoFile LOGO文件
     * @param file     存储地文件
     * @throws Exception 异常
     */
    public static void encode(@NotNull String content, File logoFile, @NotNull File file) throws Exception {
        encode(content, logoFile, file, isDefaultCompress());
    }

    /**
     * 生成二维码(内嵌LOGO)
     *
     * @param content 内容
     * @param in      LOGO输入流
     * @param file    存储地文件
     * @throws Exception 异常
     */
    public static void encode(@NotNull String content, InputStream in, @NotNull File file) throws Exception {
        encode(content, in, file, isDefaultCompress());
    }

    /**
     * 生成二维码
     *
     * @param content      内容
     * @param in           logo输入流
     * @param needCompress 是否需要压缩
     * @throws Exception 异常
     */
    public static @NotNull File encode(@NotNull String content, @Nullable InputStream in, boolean needCompress) throws Exception {
        File file = new File("tmp." + getFORMAT());
        encode(content, in, file, needCompress);
        return file;
    }

    /**
     * 生成二维码
     *
     * @param content 内容
     * @throws Exception 异常
     */
    public static @NotNull File encode(@NotNull String content, @Nullable InputStream in) throws Exception {
        return encode(content, in, getDefaultCompress());
    }

    /**
     * 生成二维码(不带LOGO)
     *
     * @param content      内容
     * @param file         存储文件
     * @param needCompress 是否压缩LOGO
     * @throws Exception 异常
     */
    public static void encode(@NotNull String content, @NotNull File file, boolean needCompress) throws Exception {
        encode(content, (InputStream) null, file, needCompress);
    }

    /**
     * 生成二维码(不带LOGO)
     *
     * @param content 内容
     * @param file    存储文件
     * @throws Exception 异常
     */
    public static void encode(@NotNull String content, @NotNull File file) throws Exception {
        encode(content, file, isDefaultCompress());
    }

    /**
     * 生成二维码
     *
     * @param content 内容
     * @throws Exception 异常
     */
    public static @NotNull File encode(@NotNull String content) throws Exception {
        File file = new File("tmp." + getFORMAT());
        encode(content, file);
        return file;
    }

    /**
     * 生成二维码(内嵌LOGO)
     *
     * @param content      内容
     * @param logoPath     LOGO地址
     * @param output       输出流
     * @param needCompress 是否压缩LOGO
     * @throws Exception 异常
     */
    @Deprecated
    public static void encode(@NotNull String content, @Nullable String logoPath, @NotNull OutputStream output, boolean needCompress) throws Exception {
        BufferedImage image = createImage(content, logoPath, needCompress);
        ImageIO.write(image, FORMAT, output);
    }

    /**
     * 生成二维码(内嵌LOGO)(直接生成到网络)
     *
     * @param content      内容
     * @param logoFile     LOGO文件
     * @param output       输出流
     * @param needCompress 是否压缩LOGO
     * @throws Exception 异常
     */
    public static void encode(@NotNull String content, @Nullable File logoFile, @NotNull OutputStream output, boolean needCompress) throws Exception {
        InputStream in = null;
        if (Objects.nonNull(logoFile))
            in = new FileInputStream(logoFile);
        BufferedImage image = createImage(content, in, needCompress);
        ImageIO.write(image, FORMAT, output);
    }

    /**
     * 生成二维码(内嵌LOGO)(直接生成到网络)
     *
     * @param content      内容
     * @param in           LOGO输入流
     * @param output       输出流
     * @param needCompress 是否压缩LOGO
     * @throws Exception 异常
     */
    public static void encode(@NotNull String content, @Nullable InputStream in, @NotNull OutputStream output, boolean needCompress) throws Exception {
        BufferedImage image = createImage(content, in, needCompress);
        ImageIO.write(image, FORMAT, output);
    }

    /**
     * 生成二维码(内嵌LOGO)(直接生成到网络)
     *
     * @param content  内容
     * @param logoFile LOGO文件
     * @param output   输出流
     * @throws Exception 异常
     */
    public static void encode(@NotNull String content, @Nullable File logoFile, @NotNull OutputStream output) throws Exception {
        encode(content, logoFile, output, isDefaultCompress());
    }

    /**
     * 生成二维码(内嵌LOGO)(直接生成到网络)
     *
     * @param content 内容
     * @param in      LOGO输入流
     * @param output  输出流
     * @throws Exception 异常
     */
    public static void encode(@NotNull String content, @Nullable InputStream in, @NotNull OutputStream output) throws Exception {
        encode(content, in, output, isDefaultCompress());
    }

    /**
     * 生成二维码(直接生成到网络)
     *
     * @param content 内容
     * @param output  输出流
     * @throws Exception 异常
     */
    public static void encode(@NotNull String content, @NotNull OutputStream output) throws Exception {
        encode(content, (InputStream) null, output);
    }

    /**
     * 生成二维码(直接生成到网络)
     *
     * @param content  内容
     * @param response 输出响应
     * @throws Exception 异常
     */
    public static void encode(@NotNull String content, @NotNull HttpServletResponse response) throws Exception {
        encode(content, (InputStream) null, response.getOutputStream());
    }

    /**
     * 生成二维码(直接生成到网络)
     *
     * @param content  内容
     * @param logoFile logo文件
     * @param response 输出响应
     * @throws Exception 异常
     */
    public static void encode(@NotNull String content, File logoFile, @NotNull HttpServletResponse response) throws Exception {
        encode(content, new FileInputStream(logoFile), response.getOutputStream());
    }

    /**
     * 生成二维码(直接生成到网络)
     *
     * @param content  内容
     * @param response 输出响应
     * @throws Exception 异常
     */
    public static void encode(@NotNull String content, InputStream in, @NotNull HttpServletResponse response) throws Exception {
        encode(content, in, response.getOutputStream());
    }

    /**
     * 生成二维码(直接生成到网络)
     *
     * @param content  内容
     * @param request  请求
     * @param response 输出响应
     * @throws Exception 异常
     */
    public static void encode(@NotNull String content, @NotNull MultipartFile request, @NotNull HttpServletResponse response) throws Exception {
        encode(content, request.getInputStream(), response);
    }

    /**
     * 当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．
     * (mkdir如果父目录不存在则会抛出异常)
     *
     * @param destPath 存放目录
     */
    public static void mkdirs(String destPath, int count) {
        File file = new File(destPath);
        if (!file.exists() && !file.isDirectory()) {
            boolean a = file.mkdirs();
            count++;
            if (!a && count < 5) {
                mkdirs(destPath, count);
            }
        }
    }

    public static @NotNull File createFile(String destPath, String fileName) {
        if (!StringUtils.isEmpty(destPath))
            mkdirs(destPath, 0);
        if (StringUtils.isEmpty(fileName)) {
            fileName = KeyUtils.get32uuid() + "." + FORMAT.toLowerCase();
        }
        fileName = fileName.substring(0, fileName.indexOf(".") > 0 ? fileName.indexOf(".") : fileName.length()) + "." + FORMAT.toLowerCase();
        return new File(destPath + "/" + fileName);
    }
}
