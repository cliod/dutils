package com.wobangkj.api;

import com.google.zxing.EncodeHintType;
import com.google.zxing.Reader;
import com.google.zxing.Writer;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.util.Map;

/**
 * qrCode
 *
 * @author cliod
 * @since 8/22/20 1:23 PM
 */
public class DefaultQrCode extends BaseQrCode {

	public DefaultQrCode() {
	}

	public DefaultQrCode(int x, int y, Shape shape, Map<EncodeHintType, Object> hints, Stroke stroke, Writer writer, Reader reader) {
		super(x, y, shape, hints, stroke, writer, reader);
	}

	public DefaultQrCode(int x, int y, Shape shape, Stroke stroke) {
		super(x, y, shape, stroke);
	}

	public DefaultQrCode(String content) {
		super();
		this.setContent(content);
	}

	public static DefaultQrCode getInstance() {
		return new DefaultQrCode();
	}

	public static DefaultQrCode getInstance(String content) {
		return new DefaultQrCode(content);
	}

	/**
	 * Insert LOGO to QR code.
	 */
	@Override
	protected void insertLogo() {
		Image src = super.logo;
		int width = src.getWidth(null);
		int height = src.getHeight(null);
		// Compress LOGO.
		int nc = 0;
		if (this.isNeedCompress) {
			if (width > this.logoWidth) {
				width = this.logoWidth;
				nc++;
			}
			if (height > this.logoHeight) {
				height = this.logoHeight;
				nc++;
			}
			src = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			Graphics g = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB).getGraphics();
			// Draw the reduced picture (Very time consuming).
			g.drawImage(src, 0, 0, null);
			g.dispose();
		}
		// Insert LOGO.
		Graphics2D graph = image.createGraphics();
		int x = (this.size - width) / 2;
		int y = (this.size - height) / 2;
		graph.drawImage(src, x, y, width, height, null);
		// The Stroke object used to draw the Shape during the rendering process.
		graph.setStroke(stroke);
		if (nc == 2) {
			graph.draw(shape);
		} else {
			Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
			graph.draw(shape);
		}
		graph.dispose();
	}
}
