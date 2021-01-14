package com.wobangkj.utils;

import net.coobird.thumbnailator.Thumbnails;

import java.io.IOException;

/**
 * 图片压缩工具
 *
 * @author cliod
 * @version 1.0
 * @since 2021-01-14 10:51:42
 */
public class ThumbnailsUtils {
	private ThumbnailsUtils() {
	}

	/**
	 * 压缩图片操作(文件物理存盘,使用默认格式)
	 *
	 * @param imgPaths 待处理图片
	 * @param width    输出图片的宽度    输入负数参数表示用原来图片宽
	 * @param height   输出图片的高度    输入负数参数表示用原来图片高
	 * @param destPath 文件存放路径
	 * @throws IOException IO异常：压缩图片操作异常
	 */
	public static void compressImage(String destPath, int width, int height, String... imgPaths) throws IOException {
		Thumbnails.of(imgPaths).size(width, height).toFile(destPath);
	}

	/**
	 * 压缩图片操作(文件物理存盘,使用默认格式)
	 *
	 * @param imgPaths 待处理图片
	 * @param scale    输出图片的大小，值在0到1之间，1f就是原图大小，0.5就是原图的一半大小，这里的大小是指图片的长宽
	 * @param destPath 文件存放路径
	 * @throws IOException IO异常：压缩图片操作异常
	 */
	public static void compressImage(String destPath, double scale, String... imgPaths) throws IOException {
		Thumbnails.of(imgPaths).scale(scale).toFile(destPath);
	}
}
