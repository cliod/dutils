package com.wobangkj.utils;

import com.google.zxing.*;
import com.google.zxing.common.HybridBinarizer;
import com.wobangkj.api.BufferedImageLuminanceSource;
import com.wobangkj.api.DefaultQrCode;
import com.wobangkj.api.QrCode;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * 二维码生成器
 *
 * @author cliod
 * @since 19-7-24
 * package : com.example.git.magicked.util
 */
public class QrCodeUtils {

	private static final QrCode qrCode = DefaultQrCode.getInstance();
	private static final String FORMAT = qrCode.getFormat();
	private static boolean DEFAULT_COMPRESS = true;

	private QrCodeUtils() {
	}

	@Deprecated
	public static boolean isDefaultCompress() {
		return DEFAULT_COMPRESS;
	}

	/**
	 * 全局设置是否压缩, 默认压缩
	 *
	 * @param defaultCompress 是否压缩
	 */
	public static void setDefaultCompress(boolean defaultCompress) {
		qrCode.setNeedCompress(defaultCompress);
		DEFAULT_COMPRESS = defaultCompress;
	}

	/**
	 * 全局设置码颜色
	 *
	 * @param huaSe 码颜色
	 */
	@Deprecated
	public static void setHuaSe(int huaSe) {
		qrCode.setForeground(new Color(huaSe));
	}

	/**
	 * 全局设置底色
	 *
	 * @param diSe 底色
	 */
	@Deprecated
	public static void setDiSe(int diSe) {
		qrCode.setBackground(new Color(diSe));
	}

	/**
	 * 生成二维码
	 *
	 * @param content 二维码内容
	 * @return 图片
	 * @throws Exception 异常
	 */
	public static @NotNull BufferedImage createImage(String content) throws Exception {
		return qrCode.createImage(content);
	}

	/**
	 * 生成二维码, 并插入LOGO, 不建议使用
	 *
	 * @param content      二维码内容
	 * @param logoPath     logo地址
	 * @param needCompress 是否压缩logo
	 * @return 图片
	 * @throws Exception 异常
	 */
	@Deprecated
	public static @NotNull BufferedImage createImage(String content, String logoPath, boolean needCompress) throws Exception {
		insertImage(null, new File(logoPath), needCompress);
		return createImage(content);
	}

	/**
	 * 生成二维码, 并插入LOGO
	 *
	 * @param content      二维码内容
	 * @param logoFile     logo文件
	 * @param needCompress 是否压缩logo
	 * @return 图片
	 * @throws Exception 异常
	 */
	public static @NotNull BufferedImage createImage(String content, File logoFile, boolean needCompress) throws Exception {
		insertImage(null, logoFile, needCompress);
		return createImage(content);
	}

	/**
	 * 生成二维码, 并插入LOGO
	 *
	 * @param content      二维码内容
	 * @param inputStream  logo流
	 * @param needCompress 是否压缩logo
	 * @return 图片
	 * @throws Exception 异常
	 */
	public static @NotNull BufferedImage createImage(String content, InputStream inputStream, boolean needCompress) throws Exception {
		insertImage(null, inputStream, needCompress);
		return createImage(content);
	}

	/**
	 * 插入LOGO, 不建议使用
	 *
	 * @param source       二维码图片
	 * @param logoPath     LOGO图片地址
	 * @param needCompress 是否压缩
	 */
	@Deprecated
	public static void insertImage(BufferedImage source, String logoPath, boolean needCompress) throws IOException {
		File file = new File(logoPath);
		if (!file.exists()) return;
		qrCode.setLogo(file, needCompress);
	}

	/**
	 * 插入LOGO
	 *
	 * @param source       二维码图片
	 * @param logoFile     LOGO图片
	 * @param needCompress 是否压缩
	 */
	@Deprecated
	public static void insertImage(BufferedImage source, File logoFile, boolean needCompress) throws IOException {
		qrCode.setLogo(logoFile, needCompress);
	}

	/**
	 * 插入LOGO
	 *
	 * @param source       二维码图片
	 * @param inputStream  LOGO图片流
	 * @param needCompress 是否压缩
	 */
	@Deprecated
	public static void insertImage(BufferedImage source, InputStream inputStream, boolean needCompress) throws IOException {
		qrCode.setLogo(inputStream, needCompress);
	}

	/**
	 * 生成二维码(内嵌LOGO), 调用者指定二维码文件名
	 *
	 * @param content      内容
	 * @param logoPath     LOGO地址
	 * @param destPath     存放目录
	 * @param fileName     二维码文件名
	 * @param needCompress 是否压缩LOGO
	 * @throws Exception 异常
	 */
	@Deprecated
	public static @NotNull File encode(@NotNull String content, String logoPath, String destPath, String fileName, boolean needCompress) throws Exception {
		BufferedImage image = createImage(content, logoPath, needCompress);
		File file = createFile(destPath, fileName);
		ImageIO.write(image, FORMAT, file);
		return file;
	}

	/**
	 * 生成二维码(内嵌LOGO), 调用者指定二维码文件名
	 *
	 * @param content      内容
	 * @param in           LOGO流
	 * @param destPath     存放目录
	 * @param fileName     二维码文件名
	 * @param needCompress 是否压缩LOGO
	 * @throws Exception 异常
	 */
	@Deprecated
	public static @NotNull File encode(@NotNull String content, InputStream in, String destPath, String fileName, boolean needCompress) throws Exception {
		BufferedImage image = createImage(content, in, needCompress);
		File file = createFile(destPath, fileName);
		ImageIO.write(image, FORMAT, file);
		return file;
	}

	/**
	 * 生成二维码(内嵌LOGO), 二维码文件名随机
	 *
	 * @param content      内容
	 * @param logoPath     LOGO地址
	 * @param destPath     存放目录
	 * @param needCompress 是否压缩LOGO
	 * @throws Exception 异常
	 */
	@Deprecated
	public static @NotNull File encode(@NotNull String content, String logoPath, String destPath, boolean needCompress) throws Exception {
		return encode(content, logoPath, destPath, "", needCompress);
	}

	/**
	 * 生成二维码(内嵌LOGO), 二维码文件名随机
	 *
	 * @param content      内容
	 * @param in           LOGO流
	 * @param destPath     存放目录
	 * @param needCompress 是否压缩LOGO
	 * @throws Exception 异常
	 */
	@Deprecated
	public static @NotNull File encode(@NotNull String content, InputStream in, String destPath, boolean needCompress) throws Exception {
		return encode(content, in, destPath, "", needCompress);
	}

	/**
	 * 生成二维码(内嵌LOGO)
	 *
	 * @param content  内容
	 * @param logoPath LOGO地址
	 * @param destPath 存储地址
	 * @throws Exception 异常
	 */
	@Deprecated
	public static @NotNull File encode(@NotNull String content, String logoPath, String destPath) throws Exception {
		return encode(content, logoPath, destPath, isDefaultCompress());
	}

	/**
	 * 生成二维码
	 *
	 * @param content      内容
	 * @param logoPath     logo地址
	 * @param needCompress 是否压缩LOGO
	 * @throws Exception 异常
	 */
	@Deprecated
	public static @NotNull File encode(@NotNull String content, String logoPath, boolean needCompress) throws Exception {
		return encode(content, logoPath, "", needCompress);
	}

	/**
	 * 生成二维码(内嵌LOGO)
	 *
	 * @param content      内容
	 * @param logoPath     LOGO地址
	 * @param file         存放文件
	 * @param needCompress 是否压缩LOGO
	 * @throws Exception 异常
	 */
	@Deprecated
	public static void encode(@NotNull String content, String logoPath, File file, boolean needCompress) throws Exception {
		BufferedImage image = createImage(content, logoPath, needCompress);
		ImageIO.write(image, FORMAT, file);
	}

	/**
	 * 生成二维码(内嵌LOGO)
	 *
	 * @param content      内容
	 * @param logoFile     LOGO文件
	 * @param file         存放文件
	 * @param needCompress 是否压缩LOGO
	 * @throws Exception 异常
	 */
	public static void encode(@NotNull String content, @Nullable File logoFile, @NotNull File file, boolean needCompress) throws Exception {
		BufferedImage image = createImage(content, logoFile, needCompress);
		ImageIO.write(image, FORMAT, file);
	}

	/**
	 * 生成二维码(内嵌LOGO)
	 *
	 * @param content      内容
	 * @param in           LOGO输入流
	 * @param file         存放文件
	 * @param needCompress 是否压缩LOGO
	 * @throws Exception 异常
	 */
	public static void encode(@NotNull String content, @Nullable InputStream in, @NotNull File file, boolean needCompress) throws Exception {
		BufferedImage image = createImage(content, in, needCompress);
		ImageIO.write(image, FORMAT, file);
	}

	/**
	 * 生成二维码(内嵌LOGO)
	 *
	 * @param content  内容
	 * @param logoFile LOGO文件
	 * @param file     存储地文件
	 * @throws Exception 异常
	 */
	public static void encode(@NotNull String content, File logoFile, @NotNull File file) throws Exception {
		encode(content, logoFile, file, isDefaultCompress());
	}

	/**
	 * 生成二维码(内嵌LOGO)
	 *
	 * @param content 内容
	 * @param in      LOGO输入流
	 * @param file    存储地文件
	 * @throws Exception 异常
	 */
	public static void encode(@NotNull String content, InputStream in, @NotNull File file) throws Exception {
		encode(content, in, file, isDefaultCompress());
	}

	/**
	 * 生成二维码
	 *
	 * @param content      内容
	 * @param in           logo输入流
	 * @param needCompress 是否需要压缩
	 * @throws Exception 异常
	 */
	public static @NotNull File encode(@NotNull String content, @Nullable InputStream in, boolean needCompress) throws Exception {
		File file = new File("tmp." + FORMAT);
		encode(content, in, file, needCompress);
		return file;
	}

	/**
	 * 生成二维码
	 *
	 * @param content 内容
	 * @throws Exception 异常
	 */
	public static @NotNull File encode(@NotNull String content, @Nullable InputStream in) throws Exception {
		return encode(content, in, DEFAULT_COMPRESS);
	}

	/**
	 * 生成二维码(不带LOGO)
	 *
	 * @param content      内容
	 * @param file         存储文件
	 * @param needCompress 是否压缩LOGO
	 * @throws Exception 异常
	 */
	public static void encode(@NotNull String content, @NotNull File file, boolean needCompress) throws Exception {
		encode(content, (InputStream) null, file, needCompress);
	}

	/**
	 * 生成二维码(不带LOGO)
	 *
	 * @param content 内容
	 * @param file    存储文件
	 * @throws Exception 异常
	 */
	public static void encode(@NotNull String content, @NotNull File file) throws Exception {
		encode(content, file, isDefaultCompress());
	}

	/**
	 * 生成二维码
	 *
	 * @param content 内容
	 * @throws Exception 异常
	 */
	public static @NotNull File encode(@NotNull String content) throws Exception {
		File file = new File("tmp." + FORMAT);
		encode(content, file);
		return file;
	}

	/**
	 * 生成二维码(内嵌LOGO)
	 *
	 * @param content      内容
	 * @param logoPath     LOGO地址
	 * @param output       输出流
	 * @param needCompress 是否压缩LOGO
	 * @throws Exception 异常
	 */
	@Deprecated
	public static void encode(@NotNull String content, @Nullable String logoPath, @NotNull OutputStream output, boolean needCompress) throws Exception {
		BufferedImage image = createImage(content, logoPath, needCompress);
		ImageIO.write(image, FORMAT, output);
	}

	/**
	 * 生成二维码(内嵌LOGO)(直接生成到网络)
	 *
	 * @param content      内容
	 * @param logoFile     LOGO文件
	 * @param output       输出流
	 * @param needCompress 是否压缩LOGO
	 * @throws Exception 异常
	 */
	public static void encode(@NotNull String content, @Nullable File logoFile, @NotNull OutputStream output, boolean needCompress) throws Exception {
		InputStream in = null;
		if (Objects.nonNull(logoFile))
			in = new FileInputStream(logoFile);
		BufferedImage image = createImage(content, in, needCompress);
		ImageIO.write(image, FORMAT, output);
	}

	/**
	 * 生成二维码(内嵌LOGO)(直接生成到网络)
	 *
	 * @param content      内容
	 * @param in           LOGO输入流
	 * @param output       输出流
	 * @param needCompress 是否压缩LOGO
	 * @throws Exception 异常
	 */
	public static void encode(@NotNull String content, @Nullable InputStream in, @NotNull OutputStream output, boolean needCompress) throws Exception {
		BufferedImage image = createImage(content, in, needCompress);
		ImageIO.write(image, FORMAT, output);
	}

	/**
	 * 生成二维码(内嵌LOGO)(直接生成到网络)
	 *
	 * @param content  内容
	 * @param logoFile LOGO文件
	 * @param output   输出流
	 * @throws Exception 异常
	 */
	public static void encode(@NotNull String content, @Nullable File logoFile, @NotNull OutputStream output) throws Exception {
		encode(content, logoFile, output, isDefaultCompress());
	}

	/**
	 * 生成二维码(内嵌LOGO)(直接生成到网络)
	 *
	 * @param content 内容
	 * @param in      LOGO输入流
	 * @param output  输出流
	 * @throws Exception 异常
	 */
	public static void encode(@NotNull String content, @Nullable InputStream in, @NotNull OutputStream output) throws Exception {
		encode(content, in, output, isDefaultCompress());
	}

	/**
	 * 生成二维码(直接生成到网络)
	 *
	 * @param content 内容
	 * @param output  输出流
	 * @throws Exception 异常
	 */
	public static void encode(@NotNull String content, @NotNull OutputStream output) throws Exception {
		encode(content, (InputStream) null, output);
	}

	/**
	 * 生成二维码(直接生成到网络)
	 *
	 * @param content  内容
	 * @param response 输出响应
	 * @throws Exception 异常
	 */
	public static void encode(@NotNull String content, @NotNull HttpServletResponse response) throws Exception {
		encode(content, (InputStream) null, response.getOutputStream());
	}

	/**
	 * 生成二维码(直接生成到网络)
	 *
	 * @param content  内容
	 * @param logoFile logo文件
	 * @param response 输出响应
	 * @throws Exception 异常
	 */
	public static void encode(@NotNull String content, File logoFile, @NotNull HttpServletResponse response) throws Exception {
		encode(content, new FileInputStream(logoFile), response.getOutputStream());
	}

	/**
	 * 生成二维码(直接生成到网络)
	 *
	 * @param content  内容
	 * @param response 输出响应
	 * @throws Exception 异常
	 */
	public static void encode(@NotNull String content, InputStream in, @NotNull HttpServletResponse response) throws Exception {
		encode(content, in, response.getOutputStream());
	}

	/**
	 * 生成二维码(直接生成到网络)
	 *
	 * @param content  内容
	 * @param request  请求
	 * @param response 输出响应
	 * @throws Exception 异常
	 */
	public static void encode(@NotNull String content, @NotNull MultipartFile request, @NotNull HttpServletResponse response) throws Exception {
		encode(content, request.getInputStream(), response);
	}

	/**
	 * 解码获取二维码内容
	 *
	 * @param path 二维码文件路径
	 * @return 二维码内容
	 * @throws Exception 异常
	 */
	@Deprecated
	public static String decode(String path) throws Exception {
		if (StringUtils.isEmpty(path)) {
			return null;
		}
		if (path.startsWith("http"))
			return decode(new URL(path));
		else
			return decode(new File(path));
	}

	/**
	 * 解码获取二维码内容
	 *
	 * @param file 二维码文件
	 * @return 二维码内容
	 * @throws Exception 异常
	 */
	public static String decode(File file) throws Exception {
		BufferedImage image;
		image = ImageIO.read(file);
		if (image == null) {
			return null;
		}
		return getContentByImage(image);
	}

	/**
	 * 解码获取二维码内容
	 *
	 * @param is 二维码流
	 * @return 二维码内容
	 * @throws Exception 异常
	 */
	public static String decode(InputStream is) throws Exception {
		BufferedImage image = ImageIO.read(is);
		if (image == null) {
			return null;
		}
		return getContentByImage(image);
	}

	/**
	 * 解码获取二维码内容
	 *
	 * @param url 二维码网络地址
	 * @return 二维码内容
	 * @throws Exception 异常
	 */
	public static String decode(URL url) throws Exception {
		BufferedImage image = ImageIO.read(url);
		if (image == null) {
			return null;
		}
		return getContentByImage(image);
	}

	/**
	 * 从二维码中解析内容
	 *
	 * @param image 二维码image
	 * @return 内容
	 * @throws NotFoundException 文件异常
	 */
	public static String getContentByImage(BufferedImage image) throws NotFoundException {
		BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
		Result result;
		Map<DecodeHintType, String> hints = new HashMap<>();
		hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
		result = new MultiFormatReader().decode(bitmap, hints);
		return result.getText();
	}

	/**
	 * 当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．
	 * (mkdir如果父目录不存在则会抛出异常)
	 *
	 * @param destPath 存放目录
	 */
	public static void mkdirs(String destPath, int count) {
		File file = new File(destPath);
		if (!file.exists() && !file.isDirectory()) {
			boolean a = file.mkdirs();
			count++;
			if (!a && count < 5) {
				mkdirs(destPath, count);
			}
		}
	}

	public static @NotNull File createFile(String destPath, String fileName) {
		if (!StringUtils.isEmpty(destPath))
			mkdirs(destPath, 0);
		if (StringUtils.isEmpty(fileName)) {
			fileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + FORMAT.toLowerCase();
		}
		fileName = fileName.substring(0, fileName.indexOf(".") > 0 ? fileName.indexOf(".") : fileName.length()) + "." + FORMAT.toLowerCase();
		return new File(destPath + "/" + fileName);
	}
}