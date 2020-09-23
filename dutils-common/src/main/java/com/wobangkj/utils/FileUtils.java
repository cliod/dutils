package com.wobangkj.utils;

import lombok.SneakyThrows;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * 文件上传工具
 *
 * @author cliod
 * @since 2020/04/17
 * package : com.example.measureless.utils
 */
public class FileUtils {

	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
	public static final String WIN_NAME = "win";
	private static final String OS_NAME = System.getProperty("os.name");
	private static final Pattern WIN_PATH_SRC = Pattern.compile("(^[A-Z]:(([\\\\/])([a-zA-Z0-9\\-_]){1,255}){1,255}|([A-Z]:([\\\\/])))");
	private static final Pattern LINUX_PATH_SRC = Pattern.compile("(/([a-zA-Z0-9][a-zA-Z0-9_\\-]{0,255}/)*([a-zA-Z0-9][a-zA-Z0-9_\\-]{0,255})|/)");

	/**
	 * 文件上传
	 *
	 * @param file     文件
	 * @param rootPath 文件存储路径
	 * @return 返回相对路径
	 * @throws IOException IO异常
	 */
	@NotNull
	public static String upload(@NotNull MultipartFile file, String rootPath) throws IOException {
		if (StringUtils.isEmpty(rootPath)) {
			rootPath = System.getProperty("user.home");
		}
		checkPath(rootPath);

		//虚拟路径,用与访问
		String path = "/file/" + FORMATTER.format(LocalDate.now()) + "/";
		//真实物理路径
		String filePath = rootPath + path;

		String originalFilename = file.getOriginalFilename();
		assert originalFilename != null;
		String extendName = originalFilename.substring(originalFilename.lastIndexOf("."));

		//文件新名字
		String fileName = KeyUtils.get32uuid() + extendName;
		transferToFile(file, filePath, fileName);
		return path + fileName;
	}

	private static void checkPath(String path) throws IllegalArgumentException {
		if (OS_NAME.toLowerCase().contains(WIN_NAME)) {
			Assert.isTrue(!WIN_PATH_SRC.matcher(path).matches(), "文件路径不正确");
		} else {
			Assert.isTrue(!LINUX_PATH_SRC.matcher(path).matches(), "文件路径不正确");
		}
	}

	/**
	 * 文件下载: 通过http请求下载文件
	 *
	 * @param response http响应
	 * @param file     文件
	 * @throws Exception 异常
	 */
	public static void download(@NotNull HttpServletResponse response, @NotNull File file) throws Exception {
		response.setHeader("content-type", "image/png");
		// 文件流,(可能下载, 可能打开[浏览器有插件会先打开文件])
		response.setContentType("application/octet-stream");
		// 强制下载文件，不打开
		response.setContentType("application/force-download");
		response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(file.getName(), "UTF-8"));
		byte[] buff = new byte[1024];
		//创建缓冲输入流
		try (OutputStream outputStream = response.getOutputStream();
		     BufferedInputStream bis = new BufferedInputStream(fileToInputStream(file))) {
			int read = bis.read(buff);
			//通过while循环写入到指定了的文件夹中
			while (read != -1) {
				outputStream.write(buff, 0, buff.length);
				outputStream.flush();
				read = bis.read(buff);
			}
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	/**
	 * 文件下载: 通过http请求下载文件
	 *
	 * @param response http响应
	 * @param is       输入流
	 * @param fileName 下载的文件名
	 * @throws Exception 异常
	 */
	public static void download(@NotNull HttpServletResponse response, @NotNull InputStream is, String fileName) throws Exception {
		response.setHeader("content-type", "image/png");
		// 文件流,(可能下载, 可能打开[浏览器有插件会先打开文件])
		response.setContentType("application/octet-stream");
		// 强制下载文件，不打开
		response.setContentType("application/force-download");
		response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
		parse(is, response.getOutputStream());
	}

	/**
	 * 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
	 *
	 * @param in 字节流
	 * @return 结果字符串
	 */
	public static @NotNull String getBase64FromInputStream(@NotNull InputStream in) throws IOException {
		byte[] bytes = IOUtils.toByteArray(in);
		return Base64.encodeBase64String(bytes);
	}

	/**
	 * 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
	 *
	 * @param file 文件
	 * @return 结果字符串
	 */
	public static @NotNull String getBase64FromInputStream(@NotNull File file) throws IOException {
		return FileUtils.getBase64FromInputStream(new FileInputStream(file));
	}

	/**
	 * 将网络文件MultipartFile转存成内存File
	 *
	 * @param file     流文件
	 * @param filePath 文件卢静
	 * @param fileName 文件名称
	 * @throws IOException IO异常
	 */
	private static void transferToFile(MultipartFile file, String filePath, String fileName) throws IOException {
		File dir = new File(filePath, fileName);
		File path = new File(filePath);
		boolean makeDir;
		if (!path.exists()) {
			makeDir = path.mkdirs();
			if (!makeDir) {
				return;
			}
		}
		//写入文档中
		file.transferTo(dir);
	}

	/**
	 * 将网络文件MultipartFile转存成内存File
	 *
	 * @param file 流文件
	 * @param path 内存文件
	 * @throws IOException IO异常
	 */
	private static void transferToFile(@NotNull MultipartFile file, File path) throws IOException {
		//写入文档中
		file.transferTo(path);
	}

	/**
	 * 删除文件
	 *
	 * @param filePath 文件路径
	 * @return 结果
	 */
	public static boolean delete(String filePath) {
		return org.apache.commons.io.FileUtils.deleteQuietly(new File(filePath));
	}

	/**
	 * 文件转输入流
	 *
	 * @param file 文件
	 * @return 输入流
	 * @throws FileNotFoundException 文件找不到异常
	 */
	public static @NotNull InputStream fileToInputStream(@NotNull File file) throws FileNotFoundException {
		return new FileInputStream(file);
	}

	@SneakyThrows
	public static @Nullable InputStream readFile(String path) {
		File file = new File(path);
		if (file.exists()) {
			return new FileInputStream(file);
		}
		return null;
	}

	/**
	 * inputStream转outputStream, 该方法阻塞
	 *
	 * @param in 输入流
	 * @return 输出流
	 * @throws IOException io异常
	 */
	public static @Nullable ByteArrayOutputStream parse(InputStream in) throws IOException {
		if (Objects.isNull(in)) {
			return null;
		}
		byte[] io = new byte[in.available()];
		int ch = in.read(io);
		if (ch > 0) {
			ByteArrayOutputStream swapStream = new ByteArrayOutputStream(io.length);
			swapStream.write(io, 0, io.length);
			return swapStream;
		}
		return null;
	}

	/**
	 * inputStream转outputStream, 该方法阻塞
	 *
	 * @param in 输入流
	 * @param os 输出流
	 * @throws IOException io异常
	 */
	public static void parse(InputStream in, OutputStream os) throws IOException {
		if (Objects.isNull(in)) {
			return;
		}
		byte[] io = new byte[in.available()];
		int ch = in.read(io);
		if (ch > 0) {
			os.write(io);
		}
	}
}
