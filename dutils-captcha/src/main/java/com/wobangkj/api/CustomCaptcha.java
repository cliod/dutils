package com.wobangkj.api;

import cn.hutool.captcha.generator.CodeGenerator;

/**
 * 自定义图片验证码
 *
 * @author cliod
 * @since 10/14/20 10:29 AM
 */
public class CustomCaptcha extends MultiStyleCaptcha {
	/**
	 * 构造，使用随机验证码生成器生成验证码
	 *
	 * @param width          图片宽
	 * @param height         图片高
	 * @param codeCount      字符个数
	 * @param interfereCount 验证码干扰元素个数
	 */
	public CustomCaptcha(int width, int height, int codeCount, int interfereCount) {
		super(width, height, codeCount, interfereCount);
	}

	/**
	 * 构造
	 *
	 * @param width          图片宽
	 * @param height         图片高
	 * @param generator      验证码生成器
	 * @param interfereCount 验证码干扰元素个数
	 */
	public CustomCaptcha(int width, int height, CodeGenerator generator, int interfereCount) {
		super(width, height, generator, interfereCount);
	}

	protected CustomCaptcha() {
	}

	public static CustomCaptcha getInstance() {
		return new CustomCaptcha();
	}

	public static CustomCaptcha of(int width, int height, CodeGenerator generator, int interfereCount) {
		return new CustomCaptcha(width, height, generator, interfereCount);
	}

	public static CustomCaptcha of(int width, int height, int codeCount, int interfereCount) {
		return new CustomCaptcha(width, height, codeCount, interfereCount);
	}
}
