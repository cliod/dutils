package com.wobangkj.utils;

import cn.hutool.captcha.AbstractCaptcha;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.ShearCaptcha;
import cn.hutool.core.util.RandomUtil;
import com.wobangkj.api.MultiStyleCaptcha;
import org.jetbrains.annotations.NotNull;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 验证码工具类
 *
 * @author cliod
 * @since 2019/10/20
 * package : com.wobangkj.git.cliod.util
 */
public class CaptchaKits {

	private static final ThreadLocalRandom RANDOM = RandomUtil.getRandom();
	protected static AbstractCaptcha captcha;

	/**
	 * 获取验证码的文字内容
	 *
	 * @return 验证码文字内容
	 */
	public static String getCode() {
		init();
		return captcha.getCode();
	}

	/**
	 * 获取验证码的文字内容
	 *
	 * @return 验证码文字内容
	 */
	public static String getNewCode() {
		init();
		captcha.createCode();
		return captcha.getCode();
	}

	/**
	 * 验证验证码是否正确，建议忽略大小写
	 *
	 * @param userInputCode 用户输入的验证码
	 * @return 是否与生成的一直
	 */
	public static boolean verify(String userInputCode) {
		if (Objects.isNull(captcha)) {
			throw new NullPointerException("未初始化");
		}
		return captcha.verify(userInputCode);
	}

	public static AbstractCaptcha generateRandomCaptcha() {
		return generateRandomCaptcha(100, 30);
	}

	public static AbstractCaptcha generateRandomCaptcha(int width, int height) {
		switch (RANDOM.nextInt(4)) {
			case 0:
				captcha = new LineCaptcha(width, height, 5, 150);
				break;
			case 1:
				captcha = new CircleCaptcha(width, height, 5, 15);
				break;
			case 2:
				captcha = new ShearCaptcha(width, height, 5, 4);
				break;
			default:
				captcha = new MultiStyleCaptcha(width, height, 5, 20);
				break;
		}
		return captcha;
	}

	/**
	 * 创建线干扰的验证码，默认5位验证码，150条干扰线
	 *
	 * @param width  图片宽
	 * @param height 图片高
	 * @return {@link LineCaptcha}
	 */
	public static LineCaptcha generateLineCaptcha(int width, int height) {
		captcha = new LineCaptcha(width, height);
		return (LineCaptcha) captcha;
	}

	/**
	 * 创建线干扰的验证码
	 *
	 * @param width     图片宽
	 * @param height    图片高
	 * @param codeCount 字符个数
	 * @param lineCount 干扰线条数
	 * @return {@link LineCaptcha}
	 */
	public static LineCaptcha generateLineCaptcha(int width, int height, int codeCount, int lineCount) {
		captcha = new LineCaptcha(width, height, codeCount, lineCount);
		return (LineCaptcha) captcha;
	}

	/**
	 * 创建圆圈干扰的验证码，默认5位验证码，15个干扰圈
	 *
	 * @param width  图片宽
	 * @param height 图片高
	 * @return {@link CircleCaptcha}
	 * @since 3.2.3
	 */
	public static CircleCaptcha generateCircleCaptcha(int width, int height) {
		captcha = new CircleCaptcha(width, height);
		return (CircleCaptcha) captcha;
	}

	/**
	 * 创建圆圈干扰的验证码
	 *
	 * @param width       图片宽
	 * @param height      图片高
	 * @param codeCount   字符个数
	 * @param circleCount 干扰圆圈条数
	 * @return {@link CircleCaptcha}
	 * @since 3.2.3
	 */
	public static CircleCaptcha generateCircleCaptcha(int width, int height, int codeCount, int circleCount) {
		captcha = new CircleCaptcha(width, height, codeCount, circleCount);
		return (CircleCaptcha) captcha;
	}

	/**
	 * 创建扭曲干扰的验证码，默认5位验证码
	 *
	 * @param width  图片宽
	 * @param height 图片高
	 * @return {@link ShearCaptcha}
	 * @since 3.2.3
	 */
	public static ShearCaptcha generateShearCaptcha(int width, int height) {
		captcha = new ShearCaptcha(width, height);
		return (ShearCaptcha) captcha;
	}

	/**
	 * 创建扭曲干扰的验证码，默认5位验证码
	 *
	 * @param width     图片宽
	 * @param height    图片高
	 * @param codeCount 字符个数
	 * @param thickness 干扰线宽度
	 * @return {@link ShearCaptcha}
	 * @since 3.3.0
	 */
	public static ShearCaptcha generateShearCaptcha(int width, int height, int codeCount, int thickness) {
		captcha = new ShearCaptcha(width, height, codeCount, thickness);
		return (ShearCaptcha) captcha;
	}

	/**
	 * 创建扭曲干扰的验证码，默认5位验证码
	 *
	 * @param width  图片宽
	 * @param height 图片高
	 * @return {@link ShearCaptcha}
	 * @since 3.2.3
	 */
	public static MultiStyleCaptcha generateMultiCaptcha(int width, int height) {
		captcha = new MultiStyleCaptcha(width, height);
		return (MultiStyleCaptcha) captcha;
	}

	/**
	 * 创建扭曲干扰的验证码，默认5位验证码
	 *
	 * @param width     图片宽
	 * @param height    图片高
	 * @param codeCount 字符个数
	 * @param thickness 干扰线宽度
	 * @return {@link ShearCaptcha}
	 * @since 3.3.0
	 */
	public static MultiStyleCaptcha generateMultiCaptcha(int width, int height, int codeCount, int thickness) {
		captcha = new MultiStyleCaptcha(width, height, codeCount, thickness);
		return (MultiStyleCaptcha) captcha;
	}

	/**
	 * 生成,输出并返回验证码
	 */
	public static void generate(HttpServletResponse response) throws IOException {
		generate(response, false);
	}

	/**
	 * 生成,输出并返回指定长宽的验证码
	 */
	public static void generate(int width, int height, HttpServletResponse response) throws IOException {
		generate(width, height, response, false);
	}

	/**
	 * 生成,输出并返回验证码
	 */
	public static void generate(HttpServletResponse response, boolean renew) throws IOException {
		if (renew) {
			captcha = generateRandomCaptcha();
		}
		init();
		response(captcha, response);
	}

	/**
	 * 生成,输出并返回指定长宽的验证码
	 */
	public static void generate(int width, int height, HttpServletResponse response, boolean renew) throws IOException {
		if (renew) {
			captcha = generateRandomCaptcha(width, height);
		}
		init(width, height);
		response(captcha, response);
	}

	/**
	 * 将图片响应到网页
	 *
	 * @param response 响应
	 * @throws IOException io异常
	 */
	public static void response(AbstractCaptcha captcha, @NotNull HttpServletResponse response) throws IOException {
		// 设置相应类型,告诉浏览器输出的内容为图片
		response.setContentType("image/png");
		// 设置响应头信息，告诉浏览器不要缓存此内容
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expire", 0);
		// 将内存中的图片通过流形式输出到客户端
		captcha.write(response.getOutputStream());
	}

	/**
	 * 初始化
	 */
	private static void init() {
		if (Objects.isNull(captcha)) {
			captcha = generateRandomCaptcha();
		}
	}

	/**
	 * 初始化
	 */
	private static void init(int width, int height) {
		if (Objects.isNull(captcha)) {
			captcha = generateRandomCaptcha(width, height);
		}
	}
}
