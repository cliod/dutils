package com.wobangkj.utils;

import cn.hutool.captcha.generator.RandomGenerator;
import com.wobangkj.api.BaseCustomCaptcha;
import com.wobangkj.api.CustomCaptcha;
import org.jetbrains.annotations.NotNull;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

/**
 * 旧版验证码工具类
 *
 * @author cliod
 * @since 2019/10/20
 * package : com.wobangkj.git.cliod.util
 */
public class CaptchaUtils {

	/**
	 * 验证码存放在session中的key
	 */
	public static final String CODE_SESSION_KEY = "297ef5196ca786a0016ca789abc60000post";
	/**
	 * 白色（用于背景）
	 */
	private static final Color BG_COLOR = Color.lightGray;
	/**
	 * 验证码字体
	 */
	private static final String FONT_FAMILY = "宋体";
	private static final int STR_LEN = 4;
	private static final int WIDTH = 100;
	private static final int HEIGHT = 30;
	private static final int INTERFERE_COUNT = 20;
	public static BaseCustomCaptcha CAPTCHA = CustomCaptcha.getInstance();

	/**
	 * 生成一个指定自定义字符的验证码
	 *
	 * @see CaptchaKits 验证码生成
	 * @deprecated
	 */
	public static void generateValidCode(String content, HttpServletRequest request, HttpServletResponse response) throws IOException {
		response(WIDTH, HEIGHT, content, BG_COLOR, FONT_FAMILY, request, response);
	}

	/**
	 * 生成一个指定字符串大小及自定义随机字符范围及背景色的验证码
	 *
	 * @see CaptchaKits 验证码生成
	 */
	@Deprecated
	public static void generateValidCode(String content, Color bgColor, HttpServletRequest request, HttpServletResponse response) throws IOException {
		response(WIDTH, HEIGHT, content, bgColor, FONT_FAMILY, request, response);
	}

	/**
	 * 生成一个指定字符串大小及长宽及字体的验证码
	 *
	 * @see CaptchaKits 验证码生成
	 */
	@Deprecated
	public static void generateValidCode(String content, String fontFamily, Color bgColor, HttpServletRequest request,
	                                     HttpServletResponse response) throws IOException {
		response(WIDTH, HEIGHT, content, bgColor, fontFamily, request, response);
	}

	/**
	 * 生成一个指定字符串大小及长宽及字体的验证码
	 *
	 * @see CaptchaKits 验证码生成
	 */
	@Deprecated
	public static void generateValidCode(String content, String fontFamily, HttpServletRequest request,
	                                     HttpServletResponse response) throws IOException {
		response(WIDTH, HEIGHT, content, BG_COLOR, fontFamily, request, response);
	}

	/**
	 * 生成一个指定字符串大小及长宽及字体的验证码
	 *
	 * @see CaptchaKits 验证码生成
	 */
	@Deprecated
	public static void generateValidCode(String content, int width, int height, String fontFamily, HttpServletRequest request,
	                                     HttpServletResponse response) throws IOException {
		response(width, height, content, BG_COLOR, fontFamily, request, response);
	}

	/**
	 * 生成一个4位数,长100像素,宽30像素的验证码
	 *
	 * @see CaptchaKits 验证码生成
	 * @deprecated
	 */
	public static @NotNull String generateValidCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return generate(request, response);
	}

	/**
	 * 生成一个指定字符串大小的验证码
	 *
	 * @see CaptchaKits 验证码生成
	 */
	@Deprecated
	public static @NotNull String generateValidCode(int strLen, HttpServletRequest request, HttpServletResponse response) throws IOException {
		return generate(strLen, WIDTH, HEIGHT, BG_COLOR, FONT_FAMILY, request, response);
	}

	/**
	 * 生成一个指定背景色的验证码
	 *
	 * @see CaptchaKits 验证码生成
	 */
	@Deprecated
	public static @NotNull String generateValidCode(Color bgColor, HttpServletRequest request, HttpServletResponse response) throws IOException {
		return generate(STR_LEN, WIDTH, HEIGHT, bgColor, FONT_FAMILY, request, response);
	}

	/**
	 * 生成一个指定字符串大小及自定义随机字符范围的验证码
	 *
	 * @see CaptchaKits 验证码生成
	 */
	@Deprecated
	public static @NotNull String generateValidCode(int strLen, String randString, HttpServletRequest request, HttpServletResponse response) throws IOException {
		CAPTCHA = CustomCaptcha.of(WIDTH, HEIGHT, new RandomGenerator(randString, strLen), INTERFERE_COUNT);
		CAPTCHA.setFont(new Font(FONT_FAMILY, Font.PLAIN, (int) (HEIGHT * 0.75)));
		CAPTCHA.setBackground(BG_COLOR);
		return generate(request, response);
	}

	/**
	 * 生成一个指定字符串大小及背景色的验证码
	 *
	 * @see CaptchaKits 验证码生成
	 */
	@Deprecated
	public static @NotNull String generateValidCode(int strLen, Color bgColor, HttpServletRequest request, HttpServletResponse response) throws IOException {
		return generate(strLen, WIDTH, HEIGHT, bgColor, FONT_FAMILY, request, response);
	}

	/**
	 * 生成一个指定长宽的验证码
	 *
	 * @see CaptchaKits 验证码生成
	 * @deprecated
	 */
	public static @NotNull String generateValidCode(int width, int height, HttpServletRequest request, HttpServletResponse response) throws IOException {
		return generate(STR_LEN, width, height, BG_COLOR, FONT_FAMILY, request, response);
	}

	/**
	 * 生成一个指定字符串大小及自定义随机字符范围及背景色的验证码
	 *
	 * @see CaptchaKits 验证码生成
	 */
	@Deprecated
	public static @NotNull String generateValidCode(int strLen, String randString, Color bgColor, HttpServletRequest request, HttpServletResponse response) throws IOException {
		CAPTCHA = CustomCaptcha.of(WIDTH, HEIGHT, new RandomGenerator(randString, strLen), INTERFERE_COUNT);
		CAPTCHA.setFont(new Font(FONT_FAMILY, Font.PLAIN, (int) (HEIGHT * 0.75)));
		CAPTCHA.setBackground(bgColor);
		return generate(request, response);
	}

	/**
	 * 生成一个指定长宽及背景色的验证码
	 *
	 * @see CaptchaKits 验证码生成
	 */
	@Deprecated
	public static @NotNull String generateValidCode(int width, int height, Color bgColor, HttpServletRequest request, HttpServletResponse response) throws IOException {
		return generate(STR_LEN, width, height, bgColor, FONT_FAMILY, request, response);
	}

	/**
	 * 生成一个指定字符串大小及长宽的验证码
	 *
	 * @see CaptchaKits 验证码生成
	 */
	@Deprecated
	public static @NotNull String generateValidCode(int strLen, int width, int height, HttpServletRequest request, HttpServletResponse response) throws IOException {
		return generate(strLen, width, height, BG_COLOR, FONT_FAMILY, request, response);
	}

	/**
	 * 生成一个指定字符串大小及长宽及背景色的验证码
	 *
	 * @see CaptchaKits 验证码生成
	 */
	@Deprecated
	public static @NotNull String generateValidCode(int strLen, int width, int height, Color bgColor, HttpServletRequest request, HttpServletResponse response) throws IOException {
		return generate(strLen, width, height, bgColor, FONT_FAMILY, request, response);
	}

	/**
	 * 生成一个指定长宽及字体的验证码
	 *
	 * @see CaptchaKits 验证码生成
	 */
	@Deprecated
	public static @NotNull String generateValidCode(int width, int height, String fontFamily, HttpServletRequest request, HttpServletResponse response) throws IOException {
		return generate(STR_LEN, width, height, BG_COLOR, fontFamily, request, response);
	}

	/**
	 * 生成一个指定字符串大小及长宽及字体的验证码
	 *
	 * @see CaptchaKits 验证码生成
	 */
	@Deprecated
	public static @NotNull String generate(int strLen, int width, int height, Color bgColor, String fontFamily,
	                                       HttpServletRequest request, HttpServletResponse response) throws IOException {
		CAPTCHA = CustomCaptcha.of(width, height, strLen, INTERFERE_COUNT);
		CAPTCHA.setFont(new Font(fontFamily, Font.PLAIN, (int) (height * 0.75)));
		CAPTCHA.setBackground(bgColor);
		return generate(request, response);
	}

	/**
	 * 生成一个指定字符串大小同时指定长宽及自定义字符范围的验证码
	 *
	 * @see CaptchaKits 验证码生成
	 */
	public static @NotNull String generate(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 2-生成验证码
		CAPTCHA.createCode();
		// 存下验证码
		String code = CAPTCHA.getCode();
		// 将图片返回
		response(code, request, response);
		return code;
	}

	/**
	 * @see CaptchaKits 验证码生成
	 */
	@Deprecated
	public static void response(int width, int height, String content, Color bgColor, String fontFamily,
	                            HttpServletRequest request, HttpServletResponse response) throws IOException {
		CAPTCHA = CustomCaptcha.of(width, height, STR_LEN, INTERFERE_COUNT);
		CAPTCHA.setFont(new Font(fontFamily, Font.PLAIN, (int) (height * 0.75)));
		CAPTCHA.setBackground(bgColor);
		CAPTCHA.setContent(content);
		response(content, request, response);
	}

	/**
	 * 生成一个指定字符串大小同时指定长宽及自定义字符范围的验证码
	 *
	 * @see CaptchaKits 验证码生成
	 */
	protected static void response(String content, HttpServletRequest request, HttpServletResponse response) throws IOException {
		/*
		 * 3-将字符串保存到session
		 */
		addCodeToSession(content, request);

		/*
		 * 4-将验证码以流的形式返回给客户端
		 */
		responseImage(response);
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
	 * @param response 响应
	 * @throws IOException io异常
	 * @see CaptchaKits 验证码生成
	 */
	protected static void responseImage(@NotNull HttpServletResponse response) throws IOException {
		// 设置相应类型,告诉浏览器输出的内容为图片
		response.setContentType("image/png");
		// 设置响应头信息，告诉浏览器不要缓存此内容
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expire", 0);
		// 将内存中的图片通过流形式输出到客户端
		CAPTCHA.write(response.getOutputStream());
	}

	/**
	 * 校验验证码
	 *
	 * @see CaptchaKits 验证码生成
	 */
	public static boolean checkValidCode(String code, @NotNull HttpSession session) {
		if (Objects.isNull(CAPTCHA)) {
			throw new NullPointerException("未初始化");
		}
		// 从session中获取随机数
		String random = (String) session.getAttribute(CODE_SESSION_KEY);
		if (random == null) {
			return false;
		}
		if (!Objects.equals(code, random)) {
			return false;
		}
		// 忽略大小写，比较字符串是否相等
		return CAPTCHA.verify(random);
	}

	/**
	 * 验证验证码是否正确，建议忽略大小写
	 *
	 * @param userInputCode 用户输入的验证码
	 * @return 是否与生成的一直
	 * @see CaptchaKits 验证码生成
	 */
	public static boolean verify(String userInputCode) {
		if (Objects.isNull(CAPTCHA)) {
			throw new NullPointerException("未初始化");
		}
		return CAPTCHA.verify(userInputCode);
	}
}
