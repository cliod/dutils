package com.wobangkj.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Hashtable;
import java.util.Random;

/**
 * 二维码生成器
 *
 * @author cliod
 * @date 19-7-24
 * package : com.wobangkj.git.magicked.util
 */
public class QrCodeUtils {
    /**
     * 二维码格式
     */
    public static final String FORMAT = "JPG";
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

    public QrCodeUtils() {
        throw new UnsupportedOperationException();
    }

    /**
     * 生成二维码
     *
     * @param content      二维码内容
     * @param logoPath     logo地址
     * @param needCompress 是否压缩logo
     * @return 图片
     * @throws Exception 异常
     */
    @NotNull
    public static BufferedImage createImage(String content, String logoPath, boolean needCompress) throws Exception {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
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
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        if (logoPath == null || "".equals(logoPath)) {
            return image;
        }
        // 插入图片
        insertImage(image, logoPath, needCompress);
        return image;
    }

    /**
     * 插入LOGO
     *
     * @param source       二维码图片
     * @param logoPath     LOGO图片地址
     * @param needCompress 是否压缩
     */
    private static void insertImage(BufferedImage source, String logoPath, boolean needCompress) {
        try (InputStream inputStream = QrCodeUtils.class.getResourceAsStream(logoPath)) {
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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 生成二维码(内嵌LOGO)
     * 二维码文件名随机，文件名可能会有重复
     *
     * @param content      内容
     * @param logoPath     LOGO地址
     * @param destPath     存放目录
     * @param needCompress 是否压缩LOGO
     * @throws Exception 异常
     */
    @NotNull
    public static String encode(String content, String logoPath, String destPath, boolean needCompress) throws Exception {
        BufferedImage image = createImage(content, logoPath, needCompress);
        mkdirs(destPath, 0);
        String fileName = new Random().nextInt(99999999) + "." + FORMAT.toLowerCase();
        ImageIO.write(image, FORMAT, new File(destPath + "/" + fileName));
        return fileName;
    }

    /**
     * 生成二维码(内嵌LOGO)
     * 调用者指定二维码文件名
     *
     * @param content      内容
     * @param logoPath     LOGO地址
     * @param destPath     存放目录
     * @param fileName     二维码文件名
     * @param needCompress 是否压缩LOGO
     * @throws Exception 异常
     */
    @NotNull
    public static String encode(String content, String logoPath, String destPath, String fileName, boolean needCompress) throws Exception {
        BufferedImage image = createImage(content, logoPath, needCompress);
        mkdirs(destPath, 0);
        fileName = fileName.substring(0, fileName.indexOf(".") > 0 ? fileName.indexOf(".") : fileName.length())
                + "." + FORMAT.toLowerCase();
        ImageIO.write(image, FORMAT, new File(destPath + "/" + fileName));
        return fileName;
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

    /**
     * 生成二维码(内嵌LOGO)
     *
     * @param content  内容
     * @param logoPath LOGO地址
     * @param destPath 存储地址
     * @throws Exception 异常
     */
    @NotNull
    public static String encode(String content, String logoPath, String destPath) throws Exception {
        return encode(content, logoPath, destPath, false);
    }

    /**
     * 生成二维码
     *
     * @param content      内容
     * @param destPath     存储地址
     * @param needCompress 是否压缩LOGO
     * @throws Exception 异常
     */
    @NotNull
    public static String encode(String content, String destPath, boolean needCompress) throws Exception {
        return encode(content, null, destPath, needCompress);
    }

    /**
     * 生成二维码
     *
     * @param content  内容
     * @param destPath 存储地址
     * @throws Exception 异常
     */
    @NotNull
    public static String encode(String content, String destPath) throws Exception {
        return encode(content, null, destPath, false);
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
    public static void encode(String content, String logoPath, OutputStream output, boolean needCompress) throws Exception {
        BufferedImage image = createImage(content, logoPath, needCompress);
        ImageIO.write(image, FORMAT, output);
    }

    /**
     * 生成二维码
     *
     * @param content 内容
     * @param output  输出流
     * @throws Exception 异常
     */
    public static void encode(String content, OutputStream output) throws Exception {
        encode(content, null, output, false);
    }
}
