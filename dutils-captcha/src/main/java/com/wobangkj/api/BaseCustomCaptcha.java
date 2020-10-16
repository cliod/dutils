package com.wobangkj.api;

import cn.hutool.captcha.AbstractCaptcha;
import cn.hutool.captcha.ICaptcha;
import cn.hutool.captcha.generator.CodeGenerator;
import org.apache.commons.lang3.StringUtils;

/**
 * 验证码默认实现
 *
 * @author cliod
 * @since 9/14/20 11:20 AM
 */
public abstract class BaseCustomCaptcha extends AbstractCaptcha implements ICaptcha {

	private String content;

	/**
	 * 构造,使用用户生成器生成验证码
	 *
	 * @param width          图片宽
	 * @param height         图片高
	 * @param generator      验证码生成器
	 * @param interfereCount 验证码干扰元素个数
	 */
	public BaseCustomCaptcha(int width, int height, CodeGenerator generator, int interfereCount) {
		super(width, height, generator, interfereCount);
	}

	/**
	 * 构造，使用随机验证码生成器生成验证码
	 *
	 * @param width          图片宽
	 * @param height         图片高
	 * @param codeCount      字符个数
	 * @param interfereCount 验证码干扰元素个数
	 */
	public BaseCustomCaptcha(int width, int height, int codeCount, int interfereCount) {
		super(width, height, codeCount, interfereCount);
	}

	/**
	 * 构造
	 *
	 * @param width     图片宽
	 * @param height    图片高
	 * @param codeCount 字符个数
	 */
	public BaseCustomCaptcha(int width, int height, int codeCount) {
		this(width, height, codeCount, 20);
	}

	/**
	 * 构造
	 *
	 * @param width  图片宽
	 * @param height 图片高
	 */
	public BaseCustomCaptcha(int width, int height) {
		this(width, height, 4);
	}

	public BaseCustomCaptcha() {
		this(100, 30);
	}

	/**
	 * 自定义设置内容
	 *
	 * @param content 内容
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 生成验证码字符串
	 *
	 * @since 3.3.0
	 */
	@Override
	protected void generateCode() {
		if (StringUtils.isEmpty(content)) {
			super.generateCode();
		} else {
			code = content;
		}
	}
}
