package com.wobangkj.api;

import com.google.zxing.WriterException;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Objects;

/**
 * qrCode
 *
 * @author cliod
 * @since 8/22/20 1:23 PM
 */
public interface QrCode {

	static QrCode of(@NotNull Object content, Object logo, Boolean needCompress, Boolean needLogo) throws IOException {
		QrCode qrCode = DefaultQrCode.getInstance();
		qrCode.setContent(content);
		if (Objects.nonNull(needCompress))
			qrCode.setNeedCompress(needCompress);
		if (Objects.nonNull(needLogo))
			qrCode.setNeedLogo(needLogo);
		if (Objects.nonNull(logo))
			if (logo instanceof BufferedImage)
				qrCode.setLogo((BufferedImage) logo);
			else if (logo instanceof InputStream)
				qrCode.setLogo((InputStream) logo);
			else if (logo instanceof File)
				qrCode.setLogo((File) logo);
			else if (logo instanceof URL)
				qrCode.setLogo((URL) logo);
			else
				qrCode.setLogo((BufferedImage) null);
		else {
			qrCode.setNeedLogo(false);
		}
		return qrCode;
	}

	@SneakyThrows
	static QrCode of(@NotNull Object content) {
		return of(content, null, null, null);
	}

	/**
	 * Generate QR code.
	 *
	 * @return image object.
	 * @throws WriterException Coding exception.
	 */
	@NotNull BufferedImage createImage() throws WriterException;

	/**
	 * Generate QR code and output to file.
	 *
	 * @param file Used to receive the generated QR code
	 * @throws WriterException 异常
	 */
	default void createImage(File file) throws WriterException, IOException {
		ImageIO.write(this.createImage(), this.getFormat(), file);
	}

	/**
	 * Generate QR code and output to network.
	 *
	 * @param response Used to receive the generated QR code
	 * @throws WriterException Coding exception.
	 */
	default void createImage(@NotNull HttpServletResponse response) throws WriterException, IOException {
		ImageIO.write(this.createImage(), this.getFormat(), response.getOutputStream());
	}

	/**
	 * Generate QR code and output to stream.
	 *
	 * @param output Used to receive the generated QR code
	 * @throws WriterException Coding exception.
	 */
	default void createImage(@NotNull OutputStream output) throws WriterException, IOException {
		ImageIO.write(this.createImage(), this.getFormat(), output);
	}

	/**
	 * Generate QR code
	 *
	 * @param content The content string used to generate the QR code.
	 * @return image object.
	 * @throws WriterException Coding exception.
	 */
	default @NotNull BufferedImage createImage(String content) throws WriterException {
		this.setContent(content);
		return createImage();
	}

	/**
	 * Re-specify content to generate QR code and output to file.
	 *
	 * @param content The content string used to generate the QR code.
	 * @param file    Used to receive the generated QR code
	 * @throws WriterException Coding exception.
	 */
	default void createImage(String content, File file) throws WriterException, IOException {
		this.setContent(content);
		ImageIO.write(this.createImage(), this.getFormat(), file);
	}

	/**
	 * Re-specify content to generate QR code and output to network.
	 *
	 * @param content  The content string used to generate the QR code.
	 * @param response Used to receive the generated QR code
	 * @throws WriterException Coding exception.
	 */
	default void createImage(String content, @NotNull HttpServletResponse response) throws WriterException, IOException {
		this.setContent(content);
		ImageIO.write(this.createImage(), this.getFormat(), response.getOutputStream());
	}

	/**
	 * Re-specify content to generate QR code and output to stream.
	 *
	 * @param content The content string used to generate the QR code.
	 * @param output  Used to receive the generated QR code
	 * @throws WriterException Coding exception.
	 */
	default void createImage(String content, @NotNull OutputStream output) throws WriterException, IOException {
		this.setContent(content);
		ImageIO.write(this.createImage(), this.getFormat(), output);
	}

	/**
	 * Set the content.
	 *
	 * @param content The content string used to generate the QR code.
	 */
	void setContent(String content);

	/**
	 * Set the content.
	 *
	 * @param content The content string used to generate the QR code.
	 */
	default void setContent(CharSequence content) {
		this.setContent(content.toString());
	}

	/**
	 * Set the content.
	 *
	 * @param content The content string used to generate the QR code.
	 */
	default void setContent(Object content) {
		this.setContent(content.toString());
	}

	/**
	 * Set the LOGO
	 *
	 * @param logo LOGO image object.
	 */
	void setLogo(BufferedImage logo);

	/**
	 * Set the LOGO
	 *
	 * @param logo LOGO image file object.
	 */
	default void setLogo(File logo) throws IOException {
		if (Objects.isNull(logo)) return;
		this.setLogo(ImageIO.read(logo));
		this.setNeedLogo(true);
	}

	/**
	 * Set the LOGO
	 *
	 * @param logo LOGO image stream object.
	 */
	default void setLogo(InputStream logo) throws IOException {
		if (Objects.isNull(logo)) return;
		this.setLogo(ImageIO.read(logo));
		this.setNeedLogo(true);
	}

	/**
	 * Set the LOGO
	 *
	 * @param logo network LOGO image.
	 */
	default void setLogo(URL logo) throws IOException {
		if (Objects.isNull(logo)) return;
		this.setLogo(ImageIO.read(logo));
		this.setNeedLogo(true);
	}

	/**
	 * Set the LOGO
	 *
	 * @param logo LOGO image file object.
	 */
	default void setLogo(File logo, boolean needCompress) throws IOException {
		if (Objects.isNull(logo)) return;
		this.setLogo(ImageIO.read(logo));
		this.setNeedLogo(true);
		this.setNeedCompress(needCompress);
	}

	/**
	 * Set the LOGO
	 *
	 * @param logo LOGO image stream object.
	 */
	default void setLogo(InputStream logo, boolean needCompress) throws IOException {
		if (Objects.isNull(logo)) return;
		this.setLogo(ImageIO.read(logo));
		this.setNeedLogo(true);
		this.setNeedCompress(needCompress);
	}

	/**
	 * Set the LOGO
	 *
	 * @param logo network LOGO image.
	 */
	default void setLogo(URL logo, boolean needCompress) throws IOException {
		if (Objects.isNull(logo)) return;
		this.setLogo(ImageIO.read(logo));
		this.setNeedLogo(true);
		this.setNeedCompress(needCompress);
	}

	/**
	 * Set whether compression is required
	 *
	 * @param needCompress whether compression is required
	 */
	void setNeedCompress(boolean needCompress);

	/**
	 * Get file format of QR code image.
	 *
	 * @return File format of QR code image. Example: tmp.jpg
	 */
	String getFormat();

	/**
	 * Set file format of QR code image.
	 *
	 * @param format File format of QR code image. Example: tmp.jpg
	 */
	void setFormat(String format);

	/**
	 * whether it needs to insert a logo, not required by default
	 *
	 * @param needLogo whether it needs
	 */
	void setNeedLogo(boolean needLogo);

	/**
	 * Set background color.
	 *
	 * @param background Background color.
	 */
	void setBackground(Color background);

	/**
	 * Set foreground color.
	 *
	 * @param foreground Foreground color.
	 */
	void setForeground(Color foreground);
}
