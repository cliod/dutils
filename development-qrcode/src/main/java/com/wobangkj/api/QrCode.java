package com.wobangkj.api;

import com.google.zxing.WriterException;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
			else
				qrCode.setLogo((BufferedImage) null);
		else {
			qrCode.setNeedCompress(false);
			qrCode.setNeedLogo(false);
		}
		return qrCode;
	}

	@SneakyThrows
	static QrCode of(@NotNull Object content) {
		return of(content, null, null, null);
	}

	/**
	 * 生成二维码
	 *
	 * @return 图片
	 * @throws WriterException 异常
	 */
	@NotNull BufferedImage createImage() throws WriterException;

	/**
	 * 生成二维码
	 *
	 * @throws WriterException 异常
	 */
	default void createImageToFile(File file) throws WriterException, IOException {
		ImageIO.write(createImage(), getFormat(), file);
	}

	/**
	 * 生成二维码
	 *
	 * @throws WriterException 异常
	 */
	default void createImage(@NotNull HttpServletResponse response) throws WriterException, IOException {
		ImageIO.write(createImage(), getFormat(), response.getOutputStream());
	}

	/**
	 * 生成二维码
	 *
	 * @throws WriterException 异常
	 */
	default void createImage(@NotNull OutputStream output) throws WriterException, IOException {
		ImageIO.write(createImage(), getFormat(), output);
	}

	/**
	 * 生成二维码
	 *
	 * @return 图片
	 * @throws WriterException 异常
	 */
	default @NotNull BufferedImage createImage(String content) throws WriterException {
		setContent(content);
		return createImage();
	}

	void setContent(String content);

	default void setContent(CharSequence content) {
		setContent(content.toString());
	}

	default void setContent(Object content) {
		setContent(content.toString());
	}

	void setLogo(BufferedImage logo);

	default void setLogo(File logo) throws IOException {
		setLogo(ImageIO.read(logo));
	}

	default void setLogo(InputStream logo) throws IOException {
		setLogo(ImageIO.read(logo));
	}

	void setNeedCompress(boolean needCompress);

	/**
	 * 获取图片格式
	 *
	 * @return 格式. 例如.jpg
	 */
	String getFormat();

	/**
	 * 设置图片格式
	 *
	 * @param format 格式. 例如.jpg
	 */
	void setFormat(String format);

	/**
	 * 是否需要logo, 默认不需要
	 *
	 * @param needLogo 是否需要
	 */
	void setNeedLogo(boolean needLogo);
}
