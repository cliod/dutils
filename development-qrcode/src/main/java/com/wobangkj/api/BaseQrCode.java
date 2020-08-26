package com.wobangkj.api;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * qrCode
 *
 * @author cliod
 * @since 8/22/20 1:23 PM
 */
public abstract class BaseQrCode implements QrCode {
	/**
	 * 编码
	 */
	private final String charset = "utf-8";
	/**
	 * 二维码尺寸
	 */
	private final int size = 300;
	/**
	 * LOGO宽度
	 */
	private final int logoHeight = 60;
	/**
	 * LOGO高度
	 */
	private final int logoWidth = 60;

	// 二维码生成额外参数
	private final Map<EncodeHintType, Object> hints;
	/**
	 * 二维码格式
	 */
	@Setter
	@Getter
	public String format = "JPG";
	// 需要注意 颜色码需是16进制字符串
	@Getter
	@Setter
	private Color foreground = Color.WHITE; //码颜色
	@Getter
	@Setter
	private Color background = Color.BLACK; //底色
	// 是否需要压缩
	@Getter
	@Setter
	private boolean isNeedCompress = true;

	// 生成的图片
	private transient BufferedImage image;
	// 生成二维码的内容
	private transient String content;
	@Getter
	private transient boolean isChange = true;
	@Setter
	private transient BufferedImage logo;
	@Setter
	private transient boolean isNeedLogo = false;

	public BaseQrCode() {
		hints = new HashMap<>();
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		hints.put(EncodeHintType.CHARACTER_SET, charset);
		hints.put(EncodeHintType.MARGIN, 1);
	}

	@Override
	public void setContent(String content) {
		this.isChange = true;
		this.content = content;
	}

	/**
	 * 生成二维码
	 *
	 * @return 图片
	 * @throws WriterException 异常
	 */
	@Override
	public @NotNull BufferedImage createImage() throws WriterException {
		// 点阵
		BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, size, size, hints);
		int width = bitMatrix.getWidth();
		int height = bitMatrix.getHeight();
		this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		if (isNeedCompress)
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					image.setRGB(x, y, bitMatrix.get(x, y) ? foreground.getRGB() : background.getRGB());
				}
			}
		if (isNeedLogo && Objects.nonNull(this.logo))
			insertLogo();
		isChange = false;
		return image;
	}

	/**
	 * 插入LOGO
	 */
	protected void insertLogo() {
		Image src = logo;
		int width = src.getWidth(null);
		int height = src.getHeight(null);
		// 压缩LOGO
		if (isNeedCompress) {
			if (width > this.logoWidth) {
				width = this.logoWidth;
			}
			if (height > this.logoHeight) {
				height = this.logoHeight;
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
		Graphics2D graph = image.createGraphics();
		int x = (this.size - width) / 2;
		int y = (this.size - height) / 2;
		graph.drawImage(src, x, y, width, height, null);
		Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
		graph.setStroke(new BasicStroke(3F));
		graph.draw(shape);
		graph.dispose();
	}
}
