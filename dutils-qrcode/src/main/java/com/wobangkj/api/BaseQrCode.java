package com.wobangkj.api;

import com.google.zxing.*;
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
 * Basic QR code to realize simple custom generation of QR code.
 *
 * @author cliod
 * @since 8/22/20 1:23 PM
 */
public abstract class BaseQrCode implements QrCode {
	/**
	 * Specifies what character encoding to use where applicable (type {@link String})
	 */
	public static final String charset = "utf-8";
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
	protected transient String content;
	/**
	 * LOGO image object.
	 */
	protected transient BufferedImage logo;

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
		hints.put(EncodeHintType.CHARACTER_SET, charset);
		hints.put(EncodeHintType.MARGIN, 1);
		stroke = new BasicStroke(3F);
		writer = new MultiFormatWriter();
	}

	public BaseQrCode(int x, int y, Shape shape, Map<EncodeHintType, Object> hints, Stroke stroke, Writer writer) {
		this.x = x;
		this.y = y;
		this.shape = shape;
		this.hints = hints;
		this.stroke = stroke;
		this.writer = writer;
	}

	public BaseQrCode(int x, int y, Shape shape, Stroke stroke) {
		this(x, y, shape, new HashMap<EncodeHintType, Object>() {{
			put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
			put(EncodeHintType.CHARACTER_SET, charset);
			put(EncodeHintType.MARGIN, 1);
		}}, stroke, new MultiFormatWriter());
	}

	/**
	 * Generate QR code.
	 *
	 * @return image object.
	 * @throws WriterException Coding exception.
	 */
	@Override
	public @NotNull BufferedImage createImage() throws WriterException {
		// 点阵
		BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, size, size, hints);
		int width = bitMatrix.getWidth();
		int height = bitMatrix.getHeight();
		this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
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
	 * Set the LOGO
	 *
	 * @param logo LOGO image object.
	 */
	@Override
	public void setLogo(BufferedImage logo) {
		if (Objects.isNull(logo)) return;
		this.logo = logo;
		this.setNeedLogo(true);
	}

	/**
	 * Set the content.
	 *
	 * @param content The content string used to generate the QR code.
	 */
	@Override
	public void setContent(String content) {
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
			// Draw the reduced picture.
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
