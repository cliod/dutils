package com.wobangkj.utils;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 图像处理<br>
 * 对图片进行压缩、水印、伸缩变换、透明处理、格式转换操作
 *
 * @author cliod
 * @since 2020-11-30
 */
public class ImageUtils {
	private ImageUtils() {
	}

	@Deprecated
	public static final float DEFAULT_QUALITY = 0.2125f;

	/**
	 * 添加图片水印操作(物理存盘,使用默认格式)
	 *
	 * @param imgPath  待处理图片
	 * @param markPath 水印图片
	 * @param x        水印位于图片左上角的 x 坐标值
	 * @param y        水印位于图片左上角的 y 坐标值
	 * @param alpha    水印透明度 0.1f ~ 1.0f
	 * @param destPath 文件存放路径
	 * @throws IOException IO异常：添加图片水印操作异常
	 */
	public static void addWaterMark(String imgPath, String markPath, int x, int y, float alpha, String destPath) throws IOException {
		BufferedImage bufferedImage = addWaterMark(imgPath, markPath, x, y, alpha);
		ImageIO.write(bufferedImage, imageFormat(imgPath), new File(destPath));
	}

	/**
	 * 添加图片水印操作(物理存盘,自定义格式)
	 *
	 * @param imgPath  待处理图片
	 * @param markPath 水印图片
	 * @param x        水印位于图片左上角的 x 坐标值
	 * @param y        水印位于图片左上角的 y 坐标值
	 * @param alpha    水印透明度 0.1f ~ 1.0f
	 * @param format   添加水印后存储的格式
	 * @param destPath 文件存放路径
	 * @throws IOException IO异常：添加图片水印操作异常
	 */
	public static void addWaterMark(String imgPath, String markPath, int x, int y, float alpha, String format, String destPath) throws IOException {
		BufferedImage bufferedImage = addWaterMark(imgPath, markPath, x, y, alpha);
		ImageIO.write(bufferedImage, format, new File(destPath));
	}

	/**
	 * 添加图片水印操作,返回BufferedImage对象
	 *
	 * @param imgPath  待处理图片
	 * @param markPath 水印图片
	 * @param x        水印位于图片左上角的 x 坐标值
	 * @param y        水印位于图片左上角的 y 坐标值
	 * @param alpha    水印透明度 0.1f ~ 1.0f
	 * @return 处理后的图片对象
	 * @throws IOException IO异常：添加图片水印操作异常
	 */
	public static BufferedImage addWaterMark(String imgPath, String markPath, int x, int y, float alpha) throws IOException {
		BufferedImage targetImage;
		// 加载待处理图片文件
		Image img = ImageIO.read(new File(imgPath));

		//创建目标图象文件
		targetImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
		Graphics2D g = targetImage.createGraphics();
		g.drawImage(img, 0, 0, null);

		// 加载水印图片文件
		Image markImg = ImageIO.read(new File(markPath));
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
		g.drawImage(markImg, x, y, null);
		g.dispose();
		return targetImage;

	}

	/**
	 * 添加文字水印操作(物理存盘,使用默认格式)
	 *
	 * @param imgPath  待处理图片
	 * @param text     水印文字
	 * @param font     水印字体信息    不写默认值为宋体
	 * @param color    水印字体颜色
	 * @param x        水印位于图片左上角的 x 坐标值
	 * @param y        水印位于图片左上角的 y 坐标值
	 * @param alpha    水印透明度 0.1f ~ 1.0f
	 * @param destPath 文件存放路径
	 * @throws IOException IO异常：图片添加文字水印异常
	 */
	public static void addTextMark(String imgPath, String text, Font font, Color color, float x, float y, float alpha, String destPath) throws IOException {
		BufferedImage bufferedImage = addTextMark(imgPath, text, font, color, x, y, alpha);
		ImageIO.write(bufferedImage, imageFormat(imgPath), new File(destPath));
	}

	/**
	 * 添加文字水印操作(物理存盘,自定义格式)
	 *
	 * @param imgPath  待处理图片
	 * @param text     水印文字
	 * @param font     水印字体信息    不写默认值为宋体
	 * @param color    水印字体颜色
	 * @param x        水印位于图片左上角的 x 坐标值
	 * @param y        水印位于图片左上角的 y 坐标值
	 * @param alpha    水印透明度 0.1f ~ 1.0f
	 * @param format   添加水印后存储的格式
	 * @param destPath 文件存放路径
	 * @throws IOException IO异常：图片添加文字水印异常
	 */
	public static void addTextMark(String imgPath, String text, Font font, Color color, float x, float y, float alpha, String format, String destPath) throws IOException {
		BufferedImage bufferedImage = addTextMark(imgPath, text, font, color, x, y, alpha);
		ImageIO.write(bufferedImage, format, new File(destPath));
	}

	/**
	 * 添加文字水印操作,返回BufferedImage对象
	 *
	 * @param imgPath 待处理图片
	 * @param text    水印文字
	 * @param font    水印字体信息    不写默认值为宋体
	 * @param color   水印字体颜色
	 * @param x       水印位于图片左上角的 x 坐标值
	 * @param y       水印位于图片左上角的 y 坐标值
	 * @param alpha   水印透明度 0.1f ~ 1.0f
	 * @return 处理后的图片对象
	 * @throws IOException IO异常：图片添加文字水印异常
	 */
	public static BufferedImage addTextMark(String imgPath, String text, Font font, Color color, float x, float y, float alpha) throws IOException {
		BufferedImage targetImage;
		Font dFont = (font == null) ? new Font("宋体", Font.PLAIN, 13) : font;
		Image img = ImageIO.read(new File(imgPath));
		//创建目标图像文件
		targetImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
		Graphics2D g = targetImage.createGraphics();
		g.drawImage(img, 0, 0, null);
		g.setColor(color);
		g.setFont(dFont);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
		g.drawString(text, x, y);
		g.dispose();
		return targetImage;
	}

	/**
	 * 压缩图片操作(文件物理存盘,使用默认格式)
	 *
	 * @param imgPath  待处理图片
	 * @param width    输出图片的宽度    输入负数参数表示用原来图片宽
	 * @param height   输出图片的高度    输入负数参数表示用原来图片高
	 * @param autoSize 是否等比缩放 true表示进行等比缩放 false表示不进行等比缩放
	 * @param destPath 文件存放路径
	 * @throws IOException IO异常：压缩图片操作异常
	 */
	public static void compressImage(String imgPath, int width, int height, boolean autoSize, String destPath) throws IOException {
		BufferedImage bufferedImage = compressImage(imgPath, width, height, autoSize);
		ImageIO.write(bufferedImage, imageFormat(imgPath), new File(destPath));
	}

	/**
	 * 压缩图片操作(文件物理存盘,可自定义格式)
	 *
	 * @param imgPath  待处理图片
	 * @param width    输出图片的宽度    输入负数参数表示用原来图片宽
	 * @param height   输出图片的高度    输入负数参数表示用原来图片高
	 * @param autoSize 是否等比缩放 true表示进行等比缩放 false表示不进行等比缩放
	 * @param format   压缩后存储的格式
	 * @param destPath 文件存放路径
	 * @throws IOException IO异常：压缩图片操作异常
	 */
	public static void compressImage(String imgPath, int width, int height, boolean autoSize, String format, String destPath) throws IOException {
		BufferedImage bufferedImage = compressImage(imgPath, width, height, autoSize);
		ImageIO.write(bufferedImage, format, new File(destPath));
	}

	/**
	 * 压缩图片操作(文件物理存盘,可自定义格式)
	 *
	 * @param imgPath      待处理图片
	 * @param width        输出图片的宽度    输入负数参数表示用原来图片宽
	 * @param height       输出图片的高度    输入负数参数表示用原来图片高
	 * @throws IOException IO异常：压缩图片操作异常
	 */
	public static void compressImage(String imgPath, int width, int height) throws IOException {
		BufferedImage bufferedImage = compressImage(imgPath, width, height, false);
		ImageIO.write(bufferedImage, imageFormat(imgPath), Files.newOutputStream(Paths.get(imgPath)));
	}

	/**
	 * 压缩图片操作(文件物理存盘,可自定义格式)
	 *
	 * @param imgPath      待处理图片
	 * @param width        输出图片的宽度    输入负数参数表示用原来图片宽
	 * @param height       输出图片的高度    输入负数参数表示用原来图片高
	 * @param autoSize     是否等比缩放 true表示进行等比缩放 false表示不进行等比缩放
	 * @param format       压缩后存储的格式
	 * @param outputStream 文件输出流
	 * @throws IOException IO异常：压缩图片操作异常
	 */
	public static void compressImage(String imgPath, int width, int height, boolean autoSize, String format, OutputStream outputStream) throws IOException {
		BufferedImage bufferedImage = compressImage(imgPath, width, height, autoSize);
		ImageIO.write(bufferedImage, format, outputStream);
	}

	/**
	 * 压缩图片操作,返回BufferedImage对象
	 *
	 * @param imgPath  待处理图片
	 * @param width    输出图片的宽度    输入负数参数表示用原来图片宽
	 * @param height   输出图片的高度    输入负数参数表示用原来图片高
	 * @param autoSize 是否等比缩放 true表示进行等比缩放 false表示不进行等比缩放
	 * @return 处理后的图片对象
	 * @throws IOException IO异常：压缩图片操作异常
	 */
	public static BufferedImage compressImage(String imgPath, Integer width, Integer height, boolean autoSize) throws IOException {

		if (width == null) {
			width = 0;
		}
		if (height == null) {
			height = 0;
		}

		BufferedImage targetImage;

		Image img = ImageIO.read(new File(imgPath));
		//如果用户输入的图片参数合法则按用户定义的复制,负值参数表示执行默认值
		int newWidth;
		if (width > 0) {
			newWidth = width;
		} else {
			autoSize = false;
			newWidth = img.getWidth(null);
		}
		//如果用户输入的图片参数合法则按用户定义的复制,负值参数表示执行默认值
		int newHeight;
		if (height > 0) {
			newHeight = height;
		} else {
			autoSize = false;
			newHeight = img.getHeight(null);
		}
		//如果是自适应大小则进行比例缩放
		if (autoSize) {
			// 为等比缩放计算输出的图片宽度及高度
			double widthRate = ((double) img.getWidth(null)) / (double) width + 0.1;
			double heightRate = ((double) img.getHeight(null)) / (double) height + 0.1;
			double rate = Math.max(widthRate, heightRate);
			newWidth = (int) (((double) img.getWidth(null)) / rate);
			newHeight = (int) (((double) img.getHeight(null)) / rate);
		}
		//创建目标图像文件
		targetImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = targetImage.createGraphics();
		g.drawImage(img, 0, 0, newWidth, newHeight, null);
		//如果添加水印或者文字则继续下面操作,不添加的话直接返回目标文件----------------------
		g.dispose();

		return targetImage;
	}

	/**
	 * 压缩图片操作,返回BufferedImage对象
	 *
	 * @param imgPath 待处理图片
	 * @param factor  压缩因子，缩放倍数
	 * @return 处理后的图片对象
	 * @throws IOException IO异常：压缩图片操作异常
	 */
	public static BufferedImage compressImage(String imgPath, Integer factor) throws IOException {

		BufferedImage img = ImageIO.read(new File(imgPath));
		//如果用户输入的图片参数合法则按用户定义的复制,负值参数表示执行默认值
		int newWidth = img.getWidth() * factor;
		//如果用户输入的图片参数合法则按用户定义的复制,负值参数表示执行默认值
		int newHeight = img.getHeight() * factor;
		//如果是自适应大小则进行比例缩放
		//创建目标图像文件
		BufferedImage targetImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = targetImage.createGraphics();
		g.drawImage(img, 0, 0, newWidth, newHeight, null);
		//如果添加水印或者文字则继续下面操作,不添加的话直接返回目标文件
		g.dispose();
		return targetImage;
	}

	/**
	 * 图片黑白化操作(文件物理存盘,使用默认格式)
	 *
	 * @param imgPath  处理的图片对象
	 * @param destPath 目标文件地址
	 * @throws IOException IO异常：图片黑白化操作异常
	 */
	public static void imageGray(String imgPath, String destPath) throws IOException {
		imageGray(imgPath, imageFormat(imgPath), destPath);
	}

	/**
	 * 图片黑白化操作(文件物理存盘,可自定义格式)
	 *
	 * @param imgPath  处理的图片对象
	 * @param format   图片格式
	 * @param destPath 目标文件地址
	 * @throws IOException IO异常：图片黑白化操作异常
	 */
	public static void imageGray(String imgPath, String format, String destPath) throws IOException {
		BufferedImage bufferedImage = ImageIO.read(new File(imgPath));
		ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
		ColorConvertOp op = new ColorConvertOp(cs, null);
		bufferedImage = op.filter(bufferedImage, null);
		ImageIO.write(bufferedImage, format, new File(destPath));
	}

	/**
	 * 图片透明化操作(文件物理存盘,使用默认格式)
	 *
	 * @param imgPath  图片路径
	 * @param destPath 图片存放路径
	 * @throws IOException IO异常：图片透明化操作异常
	 */
	public static void imageLucency(String imgPath, String destPath) throws IOException {
		BufferedImage bufferedImage = imageLucency(imgPath);
		ImageIO.write(bufferedImage, imageFormat(imgPath), new File(destPath));
	}

	/**
	 * 图片透明化操作(文件物理存盘,可自定义格式)
	 *
	 * @param imgPath  图片路径
	 * @param format   图片格式
	 * @param destPath 图片存放路径
	 * @throws IOException IO异常：图片透明化操作异常
	 */
	public static void imageLucency(String imgPath, String format, String destPath) throws IOException {
		BufferedImage bufferedImage = imageLucency(imgPath);
		ImageIO.write(bufferedImage, format, new File(destPath));
	}

	/**
	 * 图片透明化操作返回BufferedImage对象
	 *
	 * @param imgPath 图片路径
	 * @return 透明化后的图片对象
	 * @throws IOException IO异常：图片透明化操作异常
	 */
	public static BufferedImage imageLucency(String imgPath) throws IOException {
		BufferedImage targetImage;
		//读取图片
		BufferedImage img = ImageIO.read(new FileInputStream(imgPath));
		//透明度
		int alpha = 0;
		//执行透明化
		executeRgb(img, alpha);
		targetImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
		Graphics2D g = targetImage.createGraphics();
		g.drawImage(img, 0, 0, null);
		g.dispose();
		return targetImage;
	}

	/**
	 * 执行透明化的核心算法
	 *
	 * @param img   图片对象
	 * @param alpha 透明度
	 */
	public static void executeRgb(BufferedImage img, int alpha) {
		//RGB值
		int rgb;
		//x表示BufferedImage的x坐标，y表示BufferedImage的y坐标
		for (int x = img.getMinX(); x < img.getWidth(); x++) {
			for (int y = img.getMinY(); y < img.getHeight(); y++) {
				//获取点位的RGB值进行比较重新设定
				rgb = img.getRGB(x, y);
				int r = (rgb & 0xff0000) >> 16;
				int g = (rgb & 0xff00) >> 8;
				int b = (rgb & 0xff);
				if (((255 - r) < 30) && ((255 - g) < 30) && ((255 - b) < 30)) {
					rgb = ((alpha + 1) << 24) | (rgb & 0x00ffffff);
					img.setRGB(x, y, rgb);
				}
			}
		}
	}

	/**
	 * 图片格式转化操作(文件物理存盘)
	 *
	 * @param imgPath  原始图片存放地址
	 * @param format   待转换的格式 jpeg,gif,png,bmp等
	 * @param destPath 目标文件地址
	 * @throws IOException IO异常：图片格式转化操作异常
	 */
	public static void formatConvert(String imgPath, String format, String destPath) throws IOException {
		BufferedImage bufferedImage = ImageIO.read(new File(imgPath));
		ImageIO.write(bufferedImage, format, new File(destPath));
	}

	/**
	 * 图片格式转化操作返回BufferedImage对象
	 *
	 * @param bufferedImage BufferedImage图片转换对象
	 * @param format        待转换的格式 jpeg,gif,png,bmp等
	 * @param destPath      目标文件地址
	 * @throws IOException IO异常：图片格式转化操作异常
	 */
	public static void formatConvert(BufferedImage bufferedImage, String format, String destPath) throws IOException {
		ImageIO.write(bufferedImage, format, new File(destPath));
	}

	/**
	 * 获取图片文件的真实格式信息
	 *
	 * @param imgPath 图片原文件存放地址
	 * @return 图片格式
	 */
	public static String imageFormat(String imgPath) {
		String[] files = imgPath.split("\\\\");
		String[] formats = files[files.length - 1].split("\\.");
		return formats[formats.length - 1];
	}

	/**
	 * 缩小图片操作
	 *
	 * @param srcFilePath  图片所在的文件夹路径
	 * @param destFilePath 存放路径
	 * @param name         图片名
	 * @param w            目标宽
	 * @param h            目标高
	 * @param quality      百分比
	 * @throws IOException IO异常：缩小图片操作异常
	 */
	public static void toSmallerPic(String srcFilePath, String destFilePath, String name, String newFileName, int w, int h, float quality, int multiple) throws IOException {
		Image src;
		//构造Image对象
		src = ImageIO.read(new File(srcFilePath + name));

		String outputFilename = destFilePath + newFileName;
		//得到源图宽
		int oldWidth = src.getWidth(null);
		//得到源图长
		int oldHeight = src.getHeight(null);
		int newWidth = oldWidth;
		int newHeight = oldHeight;
		if (w != 0) {
			newWidth = w;
		}
		if (h != 0) {
			//计算新图长宽
			newHeight = h;
		}
		newWidth *= multiple;
		newHeight *= multiple;

		BufferedImage imageToSave = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
		imageToSave.getGraphics().drawImage(src.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);
		//输出到文件流
		FileOutputStream fos = new FileOutputStream(outputFilename);

		//新的方法
		saveAsJPEG(imageToSave, quality, fos);

		fos.close();
	}

	/**
	 * 以JPEG编码保存图片
	 *
	 * @param imageToSave     要处理的图像图片
	 * @param JpegCompression 压缩比
	 * @param fos             文件输出流
	 * @throws IOException IO异常：以JPEG编码保存图片异常
	 */
	public static void saveAsJPEG(BufferedImage imageToSave, float JpegCompression, FileOutputStream fos) throws IOException {
		//useful documentation at http://docs.oracle.com/javase/7/docs/api/javax/imageio/metadata/doc-files/jpeg_metadata.html
		//useful example program at http://johnbokma.com/java/obtaining-image-metadata.html to output JPEG data

		ImageWriter imageWriter = ImageIO.getImageWritersBySuffix("jpg").next();
		ImageOutputStream ios = ImageIO.createImageOutputStream(fos);
		imageWriter.setOutput(ios);
		//and metadata
		IIOMetadata imageMetaData = imageWriter.getDefaultImageMetadata(new ImageTypeSpecifier(imageToSave), null);

		JPEGImageWriteParam jpegParams = null;
		if (JpegCompression >= 0 && JpegCompression <= 1F) {
			// new Compression
			jpegParams = (JPEGImageWriteParam) imageWriter.getDefaultWriteParam();
			jpegParams.setCompressionMode(JPEGImageWriteParam.MODE_EXPLICIT);
			jpegParams.setCompressionQuality(JpegCompression);
		}

		//old write and clean
		//jpegEncoder.encode(image_to_save, jpegEncodeParam);

		//new Write and clean up
		imageWriter.write(imageMetaData, new IIOImage(imageToSave, null, null), jpegParams);
		ios.close();
		imageWriter.dispose();
	}
}