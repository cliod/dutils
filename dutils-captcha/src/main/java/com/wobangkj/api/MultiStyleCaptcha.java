package com.wobangkj.api;

import cn.hutool.captcha.generator.CodeGenerator;
import cn.hutool.core.img.GraphicsUtil;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 自定义融合图片验证码
 *
 * @author cliod
 * @since 10/14/20 10:29 AM
 */
public class MultiStyleCaptcha extends BaseCustomCaptcha {

	private final ThreadLocalRandom random = RandomUtil.getRandom();

	/**
	 * 构造
	 *
	 * @param width          图片宽
	 * @param height         图片高
	 * @param generator      验证码生成器
	 * @param interfereCount 验证码干扰元素个数
	 */
	public MultiStyleCaptcha(int width, int height, CodeGenerator generator, int interfereCount) {
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
	public MultiStyleCaptcha(int width, int height, int codeCount, int interfereCount) {
		super(width, height, codeCount, interfereCount);
	}

	/**
	 * 构造
	 *
	 * @param width     图片宽
	 * @param height    图片高
	 * @param codeCount 字符个数
	 */
	public MultiStyleCaptcha(int width, int height, int codeCount) {
		super(width, height, codeCount);
	}

	/**
	 * 构造
	 *
	 * @param width  图片宽
	 * @param height 图片高
	 */
	public MultiStyleCaptcha(int width, int height) {
		super(width, height);
	}

	public MultiStyleCaptcha() {
		super();
	}

	/**
	 * 根据生成的code创建验证码图片
	 *
	 * @param code 验证码
	 * @return Image
	 */
	@Override
	protected Image createImage(String code) {
		// 图像buffer
		final BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		final Graphics2D g = GraphicsUtil.createGraphics(image, ObjectUtil.defaultIfNull(this.background, Color.WHITE));
		// 干扰线
		drawInterfere(g);
		// 字符串
		drawString(g, code);
		return image;
	}

	/**
	 * 绘制字符串
	 *
	 * @param g    {@link Graphics2D}画笔
	 * @param code 验证码
	 */
	private void drawString(@NotNull Graphics2D g, String code) {
		// 指定透明度
		if (null != this.textAlpha) {
			g.setComposite(this.textAlpha);
		}
		GraphicsUtil.drawStringColourful(g, code, this.font, this.width, this.height);
	}

	/**
	 * 绘制干扰线
	 * 描述：绘制直线-粗直线,贯穿线
	 * x1 - 第一个点的 x 坐标。
	 * y1 - 第一个点的 y 坐标。
	 * x2 - 第二个点的 x 坐标。
	 * y2 - 第二个点的 y 坐标
	 */
	private void drawInterfere(@NotNull Graphics2D g) {
		// 绘制干扰线-粗线/贯穿线(两条)
		drawCrudeLine(g, width, height);
		// 绘制干扰线-细线(千分之一)
		double thick = 0.001;
		for (int i = 0, len = (int) (thick * width * height); i < len; i++) {
			drawThinLine(g, width, height);
		}
		// 绘制圆形线
		drawCircleLine(g);
		// 使图片扭曲
		shear(g, width, height, Objects.isNull(background) ? ImgUtil.randomColor(random) : background);
	}

	/**
	 * 绘制干扰线
	 * 描述：绘制直线-粗直线,贯穿线
	 * x1 - 第一个点的 x 坐标。
	 * y1 - 第一个点的 y 坐标。
	 * x2 - 第二个点的 x 坐标。
	 * y2 - 第二个点的 y 坐标
	 */
	private void drawCrudeLine(@NotNull Graphics2D g, int width, int height) {
		int x = random.nextInt(width / 10);
		int y = random.nextInt(height * 6 / 10) + height * 3 / 10;
		int xl = random.nextInt(width / 10) + width * 9 / 10;
		int yl = random.nextInt(height * 6 / 10) + height * 3 / 10;
		g.setColor(ImgUtil.randomColor(random));
		// 线的粗细
		g.setStroke(new BasicStroke(2f));
		g.drawLine(x, y, xl, yl);
	}

	/**
	 * 绘制干扰线
	 * 描述：绘制细线
	 */
	private void drawThinLine(@NotNull Graphics2D g, int width, int height) {
		// 干扰线
		for (int i = 0; i < this.interfereCount; i++) {
			int xs = random.nextInt(width);
			int ys = random.nextInt(height);
			int xe = xs + random.nextInt(width / 8);
			int ye = ys + random.nextInt(height / 8);
			g.setColor(ImgUtil.randomColor(random));
			g.setStroke(new BasicStroke(1f));
			g.drawLine(xs, ys, xe, ye);
		}
	}

	/**
	 * 绘制干扰线
	 * 描述：绘制圆形线
	 */
	private void drawCircleLine(@NotNull Graphics2D g) {
		for (int i = 0; i < this.interfereCount; i++) {
			g.setColor(ImgUtil.randomColor(random));
			g.drawOval(random.nextInt(width), random.nextInt(height), random.nextInt(height >> 1), random.nextInt(height >> 1));
		}
	}

	/**
	 * 使图片扭曲
	 */
	private void shear(@NotNull Graphics g, int w1, int h1, Color bgColor) {
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
	private void shearHorizontal(@NotNull Graphics g, int weight, int height, Color bgColor) {
		int period = random.nextInt(2);
		int frames = 1;
		int phase = random.nextInt(2);
		for (int i = 0; i < height; i++) {
			double d = calculate(period, frames, phase, i);
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
	private void shearVertical(@NotNull Graphics g, int weight, int height, Color bgColor) {
		int period = random.nextInt(20) + 5;
		int frames = 1;
		int phase = 7;
		for (int i = 0; i < weight; i++) {
			double d = calculate(period, frames, phase, i);
			g.copyArea(i, 0, 1, height, 0, (int) d);
			g.setColor(bgColor);
			g.drawLine(i, (int) d, i, 0);
			g.drawLine(i, (int) d + height, i, height);
		}
	}

	private double calculate(int period, int frames, int phase, int i) {
		return (double) (period >> 1) * Math.sin((double) i / (double) period + (6.2831853071795862D * (double) phase) / (double) frames);
	}
}
