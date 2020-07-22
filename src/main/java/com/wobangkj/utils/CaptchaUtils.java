package com.wobangkj.utils;

import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * 验证码工具类
 *
 * @author cliod
 * @since 2019/10/20
 * package : com.wobangkj.git.cliod.util
 */
public class CaptchaUtils {

    // 验证码存放在session中的key
    public static final String CODE_SESSION_KEY = "297ef5196ca786a0016ca789abc60000post";
    // 随机产生数字与字母组合的字符串
    private static final String RANDOM_STRING = "0123456789ABCDEFGHJKLMNOPQRSTUVWXYZ";
    // 白色（用于背景）
    private static final Color BG_COLOR = Color.lightGray;
    // 随机数
    private static final Random RANDOM = new Random();
    // 验证码字体
    private static final String FONT_FAMILY = "宋体";

    private static int strLen = 4;
    private static int width = 100;
    private static int height = 30;

    public static int getStrLen() {
        return strLen;
    }

    public static void setStrLen(int strLen) {
        CaptchaUtils.strLen = strLen;
    }

    public static int getWidth() {
        return width;
    }

    public static void setWidth(int width) {
        CaptchaUtils.width = width;
    }

    public static int getHeight() {
        return height;
    }

    public static void setHeight(int height) {
        CaptchaUtils.height = height;
    }

    /**
     * 生成一个指定自定义字符的验证码
     */
    public static void generateValidCode(String content, HttpServletRequest request, HttpServletResponse response) throws IOException {
        generate(getWidth(), getHeight(), content, null, null, request, response);
    }

    /**
     * 生成一个指定字符串大小及自定义随机字符范围及背景色的验证码
     */
    public static void generateValidCode(String content, Color bgColor, HttpServletRequest request, HttpServletResponse response) throws IOException {
        generate(getWidth(), getHeight(), content, bgColor, null, request, response);
    }

    /**
     * 生成一个指定字符串大小及长宽及字体的验证码
     */
    public static void generateValidCode(String content, String fontFamily, Color bgColor, HttpServletRequest request,
                                         HttpServletResponse response) throws IOException {
        generate(getWidth(), getHeight(), content, bgColor, fontFamily, request, response);
    }

    /**
     * 生成一个指定字符串大小及长宽及字体的验证码
     */
    public static void generateValidCode(String content, String fontFamily, HttpServletRequest request,
                                         HttpServletResponse response) throws IOException {
        generate(getWidth(), getHeight(), content, null, fontFamily, request, response);
    }

    /**
     * 生成一个指定字符串大小及长宽及字体的验证码
     */
    public static void generateValidCode(String content, int width, int height, String fontFamily, HttpServletRequest request,
                                         HttpServletResponse response) throws IOException {
        generate(width, height, content, null, fontFamily, request, response);
    }

    /**
     * 生成一个4位数,长100像素,宽30像素的验证码
     */
    public static @NotNull String generateValidCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return generateValidCode(getStrLen(), getWidth(), getHeight(), null, null, null, request, response);
    }

    /**
     * 生成一个指定字符串大小的验证码
     */
    public static @NotNull String generateValidCode(int stringLength, HttpServletRequest request, HttpServletResponse response) throws IOException {
        return generateValidCode(stringLength, getWidth(), getHeight(), null, null, null, request, response);
    }

    /**
     * 生成一个指定背景色的验证码
     */
    public static @NotNull String generateValidCode(Color bgColor, HttpServletRequest request, HttpServletResponse response) throws IOException {
        return generateValidCode(getStrLen(), getWidth(), getHeight(), null, bgColor, null, request, response);
    }

    /**
     * 生成一个指定字符串大小及自定义随机字符范围的验证码
     */
    public static @NotNull String generateValidCode(int stringLength, String randString, HttpServletRequest request, HttpServletResponse response) throws IOException {
        return generateValidCode(stringLength, getWidth(), getHeight(), randString, null, null, request, response);
    }

    /**
     * 生成一个指定字符串大小及背景色的验证码
     */
    public static @NotNull String generateValidCode(int stringLength, Color bgColor, HttpServletRequest request, HttpServletResponse response) throws IOException {
        return generateValidCode(stringLength, getWidth(), getHeight(), null, bgColor, null, request, response);
    }

    /**
     * 生成一个指定长宽的验证码
     */
    public static @NotNull String generateValidCode(int width, int height, HttpServletRequest request, HttpServletResponse response) throws IOException {
        return generateValidCode(getStrLen(), width, height, null, null, null, request, response);
    }

    /**
     * 生成一个指定字符串大小及自定义随机字符范围及背景色的验证码
     */
    public static @NotNull String generateValidCode(int stringLength, String randString, Color bgColor, HttpServletRequest request, HttpServletResponse response) throws IOException {
        return generateValidCode(stringLength, getWidth(), getHeight(), randString, bgColor, null, request, response);
    }

    /**
     * 生成一个指定长宽及背景色的验证码
     */
    public static @NotNull String generateValidCode(int width, int height, Color bgColor, HttpServletRequest request, HttpServletResponse response) throws IOException {
        return generateValidCode(getStrLen(), width, height, null, bgColor, null, request, response);
    }

    /**
     * 生成一个指定字符串大小及长宽的验证码
     */
    public static @NotNull String generateValidCode(int stringLength, int width, int height, HttpServletRequest request, HttpServletResponse response) throws IOException {
        return generateValidCode(stringLength, width, height, null, null, null, request, response);
    }

    /**
     * 生成一个指定字符串大小及长宽及背景色的验证码
     */
    public static @NotNull String generateValidCode(int stringLength, int width, int height, Color bgColor, HttpServletRequest request, HttpServletResponse response) throws IOException {
        return generateValidCode(stringLength, width, height, null, bgColor, null, request, response);
    }

    /**
     * 生成一个指定长宽及字体的验证码
     */
    public static @NotNull String generateValidCode(int width, int height, String fontFamily, HttpServletRequest request, HttpServletResponse response) throws IOException {
        return generateValidCode(getStrLen(), width, height, null, null, fontFamily, request, response);
    }

    /**
     * 生成一个指定字符串大小及长宽及字体的验证码
     */
    public static @NotNull String generateValidCode(int stringLength, int width, int height, String fontFamily, HttpServletRequest request, HttpServletResponse response) throws IOException {
        return generateValidCode(stringLength, width, height, null, null, fontFamily, request, response);
    }

    /**
     * 生成一个指定字符串大小同时指定长宽及自定义字符范围的验证码
     */
    public static @NotNull String generateValidCode(int stringLength, int width, int height, String randString, Color bgColor, String fontFamily, HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 随机字符串及背景色及字体
        randString = randString == null ? CaptchaUtils.RANDOM_STRING : randString;

        // 2-生成验证码
        StringBuilder contentStr = new StringBuilder();
        for (int i = 0; i < stringLength; i++) {
            contentStr.append(randString.charAt(RANDOM.nextInt(randString.length())));
        }
        generate(width, height, contentStr.toString(), bgColor, fontFamily, request, response);
        return contentStr.toString();
    }

    /**
     * 生成一个指定字符串大小同时指定长宽及自定义字符范围的验证码
     */
    public static void generate(int width, int height, String content, Color bgColor,
                                String fontFamily, HttpServletRequest request, HttpServletResponse response) throws IOException {
        /*
         * 1-参数接收
         */
        // 随机字符串及背景色及字体
        bgColor = bgColor == null ? CaptchaUtils.BG_COLOR : bgColor;
        fontFamily = fontFamily == null ? CaptchaUtils.FONT_FAMILY : fontFamily;

        /*
         * 2-生成验证码
         */
        // BufferedImage类是具有缓冲区的Image类,Image类是用于描述图像信息的类
        BufferedImage image = drawImage(width, height, bgColor, fontFamily, content);

        /*
         * 3-将字符串保存到session
         */
        addCodeToSession(content, request);

        /*
         * 4-将验证码以流的形式返回给客户端
         */
        responseImage(image, response);
    }

    /**
     * 将字符串保存到session
     */
    public static void addCodeToSession(String content, @NotNull HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute(CODE_SESSION_KEY);
        session.setAttribute(CODE_SESSION_KEY, content);

    }

    /**
     * 将图片响应到网页
     *
     * @param image    图片
     * @param response 响应
     * @throws IOException io异常
     */
    public static void responseImage(BufferedImage image, @NotNull HttpServletResponse response) throws IOException {
        // 设置相应类型,告诉浏览器输出的内容为图片
        response.setContentType("image/jpeg");
        // 设置响应头信息，告诉浏览器不要缓存此内容
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0);
        // 将内存中的图片通过流形式输出到客户端
        ImageIO.write(image, "JPEG", response.getOutputStream());
    }

    /**
     * 画图
     *
     * @param width      图片宽度
     * @param height     图片长度
     * @param bgColor    图片底色
     * @param fontFamily 图片字体
     * @param content    内容
     * @return 图片
     */
    public static @NotNull BufferedImage drawImage(int width, int height, Color bgColor, String fontFamily, String content) {
        // BufferedImage类是具有缓冲区的Image类,Image类是用于描述图像信息的类
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        // 产生Image对象的Graphics对象,改对象可以在图像上进行各种绘制操作
        Graphics2D g = image.createGraphics();
        // 消除文字锯齿
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        // 消除画图锯齿
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // 设置背景色
        g.setColor(bgColor);
        // 图片大小
        g.fillRect(0, 0, width, height);
        // 绘制干扰线-粗线/贯穿线(两条)
        drawCrudeLine(g, width, height);
        // 绘制干扰线-细线(千分之一)
        for (int i = 0, len = (int) (0.001 * width * height); i < len; i++) {
            drawThinLine(g, width, height);
        }
        // 添加噪点(千分之五)
        for (int i = 0, len = (int) (0.005 * width * height); i < len; i++) {
            image.setRGB(RANDOM.nextInt(width), RANDOM.nextInt(height), RANDOM.nextInt(255));
        }
        // 绘制字符
        char[] contents = content.toCharArray();
        int stringLength = contents.length;
        for (int i = 0; i < stringLength; i++) {
            drawString(g, String.valueOf(contents[i]), stringLength, i + 1, width, height, fontFamily);
        }
        // 使图片扭曲
        shear(g, width, height, bgColor);
        // 清除/销毁Graphics2D,避免内存溢出
        g.dispose();
        return image;
    }

    /**
     * 校验验证码
     */
    public static boolean checkValidCode(String validCode, @NotNull HttpSession session) {
        // 从session中获取随机数
        String random = (String) session.getAttribute(CODE_SESSION_KEY);
        if (random == null) {
            return false;
        }
        // 忽略大小写，比较字符串是否相等
        return random.equalsIgnoreCase(validCode);
    }

    /**
     * 绘制字符串
     *
     * @param graphics     图形
     * @param var          字符串
     * @param stringLength 字符串长度
     * @param i            第几个
     * @param width        图片宽度
     * @param height       图片长度
     * @param fontFamily   字体样式
     */
    private static void drawString(@NotNull Graphics2D graphics, String var, int stringLength, int i, int width, int height, String fontFamily) {
        graphics.setFont(new Font(fontFamily, Font.BOLD, height - 4));
        graphics.setColor(new Color(RANDOM.nextInt(101), RANDOM.nextInt(111), RANDOM.nextInt(121)));

        graphics.drawString(var, ((width - 10) / stringLength) * (i - 1) + RANDOM.nextInt(10) + 8 - stringLength, height - 8);
    }

    /**
     * 绘制干扰线
     * 描述：绘制直线-粗直线,贯穿线
     * x1 - 第一个点的 x 坐标。
     * y1 - 第一个点的 y 坐标。
     * x2 - 第二个点的 x 坐标。
     * y2 - 第二个点的 y 坐标
     */
    private static void drawCrudeLine(@NotNull Graphics2D g, int width, int height) {
        int x = RANDOM.nextInt(width / 10);
        int y = RANDOM.nextInt(height * 6 / 10) + height * 3 / 10;
        int xl = RANDOM.nextInt(width / 10) + width * 9 / 10;
        int yl = RANDOM.nextInt(height * 6 / 10) + height * 3 / 10;
        g.setColor(new Color(RANDOM.nextInt(101), RANDOM.nextInt(111), RANDOM.nextInt(121)));
        // 线的粗细
        g.setStroke(new BasicStroke(2f));
        g.drawLine(x, y, xl, yl);
    }

    /**
     * 绘制干扰线
     * 描述：绘制细线
     * x1 - 第一个点的 x 坐标。
     * y1 - 第一个点的 y 坐标。
     * x2 - 第二个点的 x 坐标。
     * y2 - 第二个点的 y 坐标
     */
    private static void drawThinLine(@NotNull Graphics2D g, int width, int height) {
        int x = RANDOM.nextInt(width);
        int y = RANDOM.nextInt(height);
        int xl = RANDOM.nextInt(width / 2);
        int yl = RANDOM.nextInt(height);
        g.setColor(new Color(RANDOM.nextInt(101), RANDOM.nextInt(111), RANDOM.nextInt(121)));
        g.setStroke(new BasicStroke(1f));
        g.drawLine(x, y, x + xl, yl);
    }

    /**
     * 使图片扭曲
     */
    private static void shear(Graphics g, int w1, int h1, Color bgColor) {
        shearHorizontal(g, w1, h1, bgColor);
        shearVertical(g, w1, h1, bgColor);
    }

    /**
     * 裁剪横坐标
     *
     * @param g       画
     * @param weight  宽
     * @param height  高
     * @param bgColor 颜色
     */
    private static void shearHorizontal(Graphics g, int weight, int height, Color bgColor) {
        int period = RANDOM.nextInt(2), frames = 1, phase = RANDOM.nextInt(2);
        for (int i = 0; i < height; i++) {
            double d = (double) (period >> 1) * Math.sin((double) i / (double) period + (6.2831853071795862D * (double) phase) / (double) frames);
            g.copyArea(0, i, weight, 1, (int) d, 0);
            g.setColor(bgColor);
            g.drawLine((int) d, i, 0, i);
            g.drawLine((int) d + weight, i, weight, i);
        }
    }

    /**
     * 裁剪纵坐标
     *
     * @param g       画
     * @param weight  宽
     * @param height  高
     * @param bgColor 颜色
     */
    private static void shearVertical(Graphics g, int weight, int height, Color bgColor) {
        int period = RANDOM.nextInt(20) + 5, frames = 1, phase = 7;
        for (int i = 0; i < weight; i++) {
            double d = (double) (period >> 1) * Math.sin((double) i / (double) period + (6.2831853071795862D * (double) phase) / (double) frames);
            g.copyArea(i, 0, 1, height, 0, (int) d);
            g.setColor(bgColor);
            g.drawLine(i, (int) d, i, 0);
            g.drawLine(i, (int) d + height, i, height);
        }
    }
}
