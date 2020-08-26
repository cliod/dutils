package com.wobangkj.api;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * qrCode
 *
 * @author cliod
 * @since 8/22/20 1:23 PM
 */
@Data
public class QrCode {
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
	public String format = "JPG";
	// 点阵
	private transient BitMatrix bitMatrix;
	// 需要注意 颜色码需是16进制字符串
	private Color foreground = Color.WHITE; //码颜色
	private Color background = Color.BLACK; //底色
	// 是否需要压缩
	private boolean isCompress = true;
	// 额外色彩
	private transient Color color;
	// 自定义
	private boolean isCustomColor = false;

	// 生成二维码的内容
	private transient String content;

	public QrCode() {
		hints = new HashMap<>();
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		hints.put(EncodeHintType.CHARACTER_SET, charset);
		hints.put(EncodeHintType.MARGIN, 1);
	}

	/**
	 * 生成二维码
	 *
	 * @return 图片
	 * @throws Exception 异常
	 */
	public @NotNull BufferedImage createImage() throws Exception {
		bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, size, size, hints);
		int width = bitMatrix.getWidth();
		int height = bitMatrix.getHeight();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		if (!isCompress)
			return image;
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, bitMatrix.get(x, y) ? foreground.getRGB() : background.getRGB());
			}
		}
		return image;
	}
}
