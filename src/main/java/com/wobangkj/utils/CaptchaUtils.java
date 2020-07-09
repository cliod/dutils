package com.wobangkj.utils;

import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
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
    private static final Color BG_COLOR = new Color(255, 255, 255);
    // 随机数
    private static final Random RANDOM = new Random();
    // 验证码字体
    private static final String FONT_FAMILY = "宋体";

    /**
     * 生成一个4位数,长100像素,宽30像素的验证码
     */
    public static String generateValidCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return generateValidCode(4, 100, 30, null, null, null, request, response);
    }

    /**
     * 生成一个指定字符串大小的验证码
     */
    public static String generateValidCode(int stringLength, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return generateValidCode(stringLength, 100, 30, null, null, null, request, response);
    }

    /**
     * 生成一个指定自定义随机字符范围的验证码
     */
    public static String generateValidCode(String randString, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return generateValidCode(4, 100, 30, randString, null, null, request, response);
    }

    /**
     * 生成一个指定背景色的验证码
     */
    public static String generateValidCode(Color bgColor, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return generateValidCode(4, 100, 30, null, bgColor, null, request, response);
    }

    /**
     * 生成一个指定字符串大小及自定义随机字符范围的验证码
     */
    public static String generateValidCode(int stringLength, String randString, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return generateValidCode(stringLength, 100, 30, randString, null, null, request, response);
    }

    /**
     * 生成一个指定字符串大小及背景色的验证码
     */
    public static String generateValidCode(int stringLength, Color bgColor, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return generateValidCode(stringLength, 100, 30, null, bgColor, null, request, response);
    }

    /**
     * 生成一个指定长宽的验证码
     */
    public static String generateValidCode(int width, int height, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return generateValidCode(4, width, height, null, null, null, request, response);
    }

    /**
     * 生成一个指定字符串大小及自定义随机字符范围及背景色的验证码
     */
    public static String generateValidCode(int stringLength, String randString, Color bgColor, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return generateValidCode(stringLength, 100, 30, randString, bgColor, null, request, response);
    }

    /**
     * 生成一个指定长宽及背景色的验证码
     */
    public static String generateValidCode(int width, int height, Color bgColor, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return generateValidCode(4, width, height, null, bgColor, null, request, response);
    }

    /**
     * 生成一个指定字符串大小及长宽的验证码
     */
    public static String generateValidCode(int stringLength, int width, int height, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return generateValidCode(stringLength, width, height, null, null, null, request, response);
    }

    /**
     * 生成一个指定字符串大小及长宽及背景色的验证码
     */
    public static String generateValidCode(int stringLength, int width, int height, Color bgColor, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return generateValidCode(stringLength, width, height, null, bgColor, null, request, response);
    }

    /**
     * 生成一个指定长宽及字体的验证码
     */
    public static String generateValidCode(int width, int height, String fontFamily, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return generateValidCode(4, width, height, null, null, fontFamily, request, response);
    }

    /**
     * 生成一个指定字符串大小及长宽及字体的验证码
     */
    public static String generateValidCode(int stringLength, int width, int height, String fontFamily, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return generateValidCode(stringLength, width, height, null, null, fontFamily, request, response);
    }

    /**
     * 生成一个指定字符串大小同时指定长宽及自定义字符范围的验证码
     */
    public static String generateValidCode(int stringLength, int width, int height, String randString, Color bgColor, String fontFamily, HttpServletRequest request, HttpServletResponse response) {
        /*
         * 1-参数接收
         */
        // 随机字符串及背景色及字体
        randString = randString == null ? CaptchaUtils.RANDOM_STRING : randString;
        bgColor = bgColor == null ? CaptchaUtils.BG_COLOR : bgColor;
        fontFamily = fontFamily == null ? CaptchaUtils.FONT_FAMILY : fontFamily;

        /*
         * 2-生成验证码
         */
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
        drowLine(g, width, height);
        // 绘制干扰线-细线(千分之一)
        for (int i = 0, len = (int) (0.001 * width * height); i < len; i++) {
            drowLine2(g, width, height);
        }
        // 添加噪点(千分之五)
        for (int i = 0, len = (int) (0.005 * width * height); i < len; i++) {
            image.setRGB(RANDOM.nextInt(width), RANDOM.nextInt(height), RANDOM.nextInt(255));
        }
        // 绘制随机字符
        String randomString = "";
        for (int i = 1; i <= stringLength; i++) {
            randomString = drowString(g, randomString, stringLength, i, width, height, randString, fontFamily);
        }
        // 使图片扭曲
        shear(g, width, height, bgColor);
        // 清除/销毁Graphics2D,避免内存溢出
        g.dispose();

        /*
         * 3-将生成的随机字符串保存到session
         */
        HttpSession session = request.getSession();
        session.removeAttribute(CODE_SESSION_KEY);
        session.setAttribute(CODE_SESSION_KEY, randomString);

        /*
         * 4-设置响应头部,并将验证码以流的形式返回给客户端
         */
        // 设置相应类型,告诉浏览器输出的内容为图片
        response.setContentType("image/jpeg");
        // 设置响应头信息，告诉浏览器不要缓存此内容
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0);
        try {
            // 将内存中的图片通过流形式输出到客户端
            ImageIO.write(image, "JPEG", response.getOutputStream());
        } catch (Exception e) {
            System.out.println("======验证码生成异常-->将内存中的图片通过流动形式输出到客户端失败======");
        }

        return randomString;
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
     * 绘制干扰线
     * 描述：绘制直线-粗直线,贯穿线
     * x1 - 第一个点的 x 坐标。
     * y1 - 第一个点的 y 坐标。
     * x2 - 第二个点的 x 坐标。
     * y2 - 第二个点的 y 坐标
     */
    private static void drowLine(@NotNull Graphics2D g, int width, int height) {
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
    private static void drowLine2(@NotNull Graphics2D g, int width, int height) {
        int x = RANDOM.nextInt(width);
        int y = RANDOM.nextInt(height);
        int xl = RANDOM.nextInt(width / 2);
        int yl = RANDOM.nextInt(height);
        g.setColor(new Color(RANDOM.nextInt(101), RANDOM.nextInt(111), RANDOM.nextInt(121)));
        g.setStroke(new BasicStroke(1f));
        g.drawLine(x, y, x + xl, yl);
    }

    /**
     * 绘制字符串
     */
    private static @NotNull String drowString(@NotNull Graphics2D graphics, String randomString, int stringLength, int i, int width, int height, @NotNull String randString, String fontFamily) {
        graphics.setFont(new Font(fontFamily, Font.BOLD, height - 4));
        graphics.setColor(new Color(RANDOM.nextInt(101), RANDOM.nextInt(111), RANDOM.nextInt(121)));
        String rand = String.valueOf(randString.charAt(RANDOM.nextInt(randString.length())));
        randomString += rand;
        graphics.drawString(rand, ((width - 10) / stringLength) * (i - 1) + RANDOM.nextInt(10) + 8 - stringLength, height - 8);
        return randomString;
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
