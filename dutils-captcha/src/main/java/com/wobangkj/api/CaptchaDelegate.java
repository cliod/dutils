package com.wobangkj.api;

import cn.hutool.captcha.AbstractCaptcha;
import cn.hutool.captcha.ICaptcha;
import cn.hutool.captcha.generator.CodeGenerator;

import java.awt.*;

/**
 * 验证码委托对象
 *
 * @author cliod
 * @since 9/14/20 11:20 AM
 */
public class CaptchaDelegate extends BaseCustomCaptcha implements ICaptcha {
	/**
	 * 委托
	 */
	protected AbstractCaptcha delegate;

	/**
	 * 构造，使用随机验证码生成器生成验证码
	 *
	 * @param width          图片宽
	 * @param height         图片高
	 * @param codeCount      字符个数
	 * @param interfereCount 验证码干扰元素个数
	 */
	public CaptchaDelegate(int width, int height, int codeCount, int interfereCount, Class<? extends AbstractCaptcha> delegate) throws ReflectiveOperationException {
		super(width, height, codeCount, interfereCount);
		this.delegate = delegate.getConstructor(int.class, int.class, int.class, int.class).newInstance(width, height, codeCount, interfereCount);
	}

	/**
	 * 构造
	 *
	 * @param width          图片宽
	 * @param height         图片高
	 * @param generator      验证码生成器
	 * @param interfereCount 验证码干扰元素个数
	 */
	public CaptchaDelegate(int width, int height, CodeGenerator generator, int interfereCount, Class<? extends AbstractCaptcha> delegate) throws ReflectiveOperationException {
		super(width, height, generator, interfereCount);
		this.delegate = delegate.getConstructor(int.class, int.class, CodeGenerator.class, int.class).newInstance(width, height, generator, interfereCount);
	}

	public CaptchaDelegate(AbstractCaptcha delegate) {
		this.delegate = delegate;
	}

	/**
	 * 根据生成的code创建验证码图片
	 *
	 * @param code 验证码
	 * @return Image
	 */
	@Override
	protected Image createImage(String code) {
		return delegate.getImage();
	}
}
