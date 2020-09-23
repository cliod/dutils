package com.wobangkj.api;

import cn.hutool.captcha.AbstractCaptcha;
import lombok.SneakyThrows;

import java.awt.*;

/**
 * 验证码默认实现
 *
 * @author cliod
 * @since 9/14/20 11:20 AM
 */
public abstract class BaseCaptcha extends AbstractCaptcha implements Captcha {
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
	@SneakyThrows
	public BaseCaptcha(int width, int height, int codeCount, int interfereCount, Class<? extends AbstractCaptcha> delegate) {
		super(width, height, codeCount, interfereCount);
		this.delegate = delegate.getConstructor(int.class, int.class, int.class, int.class).newInstance(width, height, codeCount, interfereCount);
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
