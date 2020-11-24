package com.wobangkj.api;

import com.google.zxing.*;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Basic QR code to realize simple custom generation of QR code.
 *
 * @author cliod
 * @since 8/22/20 1:23 PM
 */
public abstract class BaseQrCode implements QrCode {
	/**
	 * Specifies what character encoding to use where applicable (type {@link String})
	 */
	public static final String CHARSET = "utf-8";
	/**
	 * QR code size. The preferred size in pixels.
	 */
	protected final int size = 300;
	/**
	 * The height of the QR code LOGO. The preferred height in pixels.
	 */
	protected final int logoHeight = 60;
	/**
	 * The width of the QR code LOGO. The preferred width in pixels.
	 */
	protected final int logoWidth = 60;
	/**
	 * The x position of the LOGO in the QR code.
	 */
	protected final int x;
	/**
	 * The y position of the LOGO in the QR code.
	 */
	protected final int y;
	/**
	 * The shape of the LOGO in the QR code.
	 */
	protected final Shape shape;
	/**
	 * Two-dimensional code encoding additional parameters.
	 */
	protected final Map<EncodeHintType, Object> hints;
	/**
	 * The action object used to draw the image during the rendering process, default 3F.
	 */
	protected final Stroke stroke;
	/**
	 * The object for object which encode/generate a barcode image.
	 */
	protected final Writer writer;
	protected final Reader reader;
	/**
	 * File format of QR code image.
	 */
	@Setter
	@Getter
	protected String format = "JPG";
	/**
	 * Code color.
	 * Note that color codes need to be hexadecimal strings.
	 */
	@Setter
	protected Color foreground = Color.WHITE;
	/**
	 * Background color.
	 * Note that color codes need to be hexadecimal strings.
	 */
	@Setter
	protected Color background = Color.BLACK;
	/**
	 * Does the QR code LOGO need to be compressed/stretched?
	 */
	@Setter
	protected boolean isNeedCompress = true;
	/**
	 * Do you need to enter and exit the LOGO (not required by default)
	 */
	@Setter
	protected transient boolean isNeedLogo = false;
	/**
	 * Is the content of the two-dimensional code image and content the same?
	 */
	@Getter
	protected transient boolean isChange = true;

	/**
	 * Generated QR code image object.
	 */
	protected transient BufferedImage image;
	/**
	 * The content string used to generate the QR code.
	 */
	@Getter
	protected transient String content;
	/**
	 * LOGO image object.
	 */
	protected transient BufferedImage logo;
	/**
	 * LOGO image object.
	 */
	@Setter
	private transient Object logoObj;
	/**
	 * Default color.
	 */
	private transient int[] colors = null;

	/**
	 * QR code initialization.
	 * <p>
	 * The default LOGO is in the middle of the QR code image.
	 * </p>
	 * <p>
	 * The default LOGO shape is square with 6 radians rounded corners.
	 * </p>
	 * <p>
	 * Constructs a solid <code>BasicStroke</code> with the specified line width and with default values for the cap and join styles.
	 * </p>
	 */
	public BaseQrCode() {
		x = (this.size - this.logoWidth) / 2;
		y = (this.size - this.logoHeight) / 2;
		shape = new RoundRectangle2D.Float(x, y, this.logoWidth, this.logoHeight, 6, 6);
		hints = new HashMap<>();
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
		hints.put(EncodeHintType.MARGIN, 1);
		stroke = new BasicStroke(3F);
		writer = new MultiFormatWriter();
		reader = new MultiFormatReader();
	}

	public BaseQrCode(int x, int y, Shape shape, Map<EncodeHintType, Object> hints, Stroke stroke, Writer writer, Reader reader) {
		this.x = x;
		this.y = y;
		this.shape = shape;
		this.hints = hints;
		this.stroke = stroke;
		this.writer = writer;
		this.reader = reader;
	}

	public BaseQrCode(int x, int y, Shape shape, Stroke stroke) {
		this(x, y, shape, new HashMap<EncodeHintType, Object>() {{
			put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
			put(EncodeHintType.CHARACTER_SET, CHARSET);
			put(EncodeHintType.MARGIN, 1);
		}}, stroke, new MultiFormatWriter(), new MultiFormatReader());
	}

	/**
	 * 代替静态方法
	 *
	 * @param content      内容
	 * @param logo         logo（nullable）
	 * @param needCompress 是否压缩
	 * @param needLogo     是否需要插入logo
	 * @return 二维码对象
	 * @throws IOException IO异常
	 */
	public static BaseQrCode of(@NotNull Object content, Object logo, Boolean needCompress, Boolean needLogo) throws IOException {
		BaseQrCode qrCode = new BaseQrCode() {
		};
		qrCode.setContent(content);
		if (Objects.nonNull(needCompress)) {
			qrCode.setNeedCompress(needCompress);
		}
		if (Objects.nonNull(needLogo)) {
			qrCode.setNeedLogo(needLogo);
		}
		if (Objects.nonNull(logo)) {
			if (logo instanceof BufferedImage) {
				qrCode.setLogo((BufferedImage) logo);
			} else if (logo instanceof InputStream) {
				qrCode.setLogo((InputStream) logo);
			} else if (logo instanceof File) {
				qrCode.setLogo((File) logo);
			} else if (logo instanceof URL) {
				qrCode.setLogo((URL) logo);
			} else {
				qrCode.setLogo((BufferedImage) null);
			}
		} else {
			qrCode.setNeedLogo(false);
		}
		return qrCode;
	}

	@SneakyThrows
	public static QrCode of(@NotNull Object content) {
		return of(content, null, null, null);
	}

	/**
	 * Generate QR code.
	 *
	 * @return image object.
	 * @throws WriterException Coding exception.
	 */
	@Override
	public @NotNull BufferedImage createImage() throws WriterException {
		if (!isChange) {
			return image;
		}
		// 点阵
		BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, size, size, hints);
		int width = bitMatrix.getWidth();
		int height = bitMatrix.getHeight();
		this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		if (Objects.isNull(colors) || colors.length == 0) {
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					image.setRGB(x, y, bitMatrix.get(x, y) ? foreground.getRGB() : background.getRGB());
				}
			}
			colors = image.getRGB(0, 0, width, height, null, 0, width);
		} else {
			image.setRGB(0, 0, width, height, colors, 0, width);
		}
		if (isNeedLogo && Objects.nonNull(this.logo)) {
			insertLogo();
		}
		isChange = false;
		return image;
	}

	@Override
	public Object getLogo() {
		return logoObj;
	}

	/**
	 * Set the LOGO
	 *
	 * @param logo LOGO image object.
	 */
	@Override
	public void setLogo(BufferedImage logo) {
		if (Objects.isNull(logo)) {
			return;
		}
		if (Objects.equals(logo, this.logo)) {
			return;
		}
		this.logo = logo;
		this.isChange = true;
		this.setNeedLogo(true);
	}

	/**
	 * Set the content.
	 *
	 * @param content The content string used to generate the QR code.
	 */
	@Override
	public void setContent(String content) {
		if (Objects.isNull(content)) {
			return;
		}
		if (Objects.equals(content, this.content)) {
			return;
		}
		this.isChange = true;
		this.content = content;
	}

	/**
	 * Insert LOGO to QR code.
	 */
	protected void insertLogo() {
		Image src = logo;
		// Compress LOGO.
		if (isNeedCompress) {
			src = src.getScaledInstance(this.logoWidth, this.logoHeight, Image.SCALE_SMOOTH);
			Graphics g = new BufferedImage(this.logoWidth, this.logoHeight, BufferedImage.TYPE_INT_RGB).getGraphics();
			// Draw the reduced picture (Very time consuming)..
			g.drawImage(src, 0, 0, null);
			g.dispose();
		}
		// Insert LOGO.
		Graphics2D graph = image.createGraphics();
		graph.drawImage(src, x, y, this.logoWidth, this.logoHeight, null);
		// The Stroke object used to draw the Shape during the rendering process.
		graph.setStroke(stroke);
		graph.draw(shape);
		graph.dispose();
	}
}
