package com.wobangkj.utils;

import com.wobangkj.thread.WorkThreadFactory;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

/**
 * 文件上传工具
 *
 * @author cliod
 * @since 2020/04/17
 * package : com.example.measureless.utils
 */
public class FileUtils {

	public static final String WIN_NAME = "win";
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
	private static final String OS_NAME = System.getProperty("os.name");
	private static final Pattern WIN_PATH_SRC = Pattern.compile("(^[A-Z]:(([\\\\/])([a-zA-Z0-9\\-_]){1,255}){1,255}|([A-Z]:([\\\\/])))");
	private static final Pattern LINUX_PATH_SRC = Pattern.compile("(/([a-zA-Z0-9][a-zA-Z0-9_\\-]{0,255}/)*([a-zA-Z0-9][a-zA-Z0-9_\\-]{0,255})|/)");
	public static String[] imgType = new String[]{".png", ".jpg", ".jpeg"};
	/**
	 * 文件处理线程管理
	 */
	public static ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), new WorkThreadFactory("文件处理"));

	/**
	 * 文件上传
	 *
	 * @param file     文件
	 * @return 返回相对路径
	 * @throws IOException IO异常
	 */
	public static @NotNull String upload(@NotNull MultipartFile file) throws IOException {
		return upload(file, "", "");
	}

	/**
	 * 文件上传
	 *
	 * @param file     文件
	 * @param rootPath 文件存储路径
	 * @return 返回相对路径
	 * @throws IOException IO异常
	 */
	public static @NotNull String upload(@NotNull MultipartFile file, String rootPath) throws IOException {
		return upload(file, rootPath, "");
	}

	/**
	 * 文件上传
	 *
	 * @param file       文件
	 * @param rootPath   文件存储路径
	 * @param customName 文件自定义名称，不建议
	 * @return 返回相对路径
	 * @throws IOException IO异常
	 */
	public static @NotNull String upload(@NotNull MultipartFile file, String rootPath, String customName) throws IOException {
		return upload(file, rootPath, customName, 0, 0, false);
	}

	/**
	 * 文件上传
	 *
	 * @param file          文件
	 * @param rootPath      文件存储路径
	 * @param customName    文件自定义名称，不建议
	 * @param height        自定义高度，输入负数参数表示用原来图片高
	 * @param width         自定义宽度，输入负数参数表示用原来图片宽
	 * @param isImgCompress 是否等比压缩(是否等比缩放)，true表示进行等比缩放 false表示不进行等比缩放
	 * @return 返回相对路径
	 * @throws IOException IO异常
	 */
	public static @NotNull String upload(@NotNull MultipartFile file, String rootPath, String customName,
	                                     Integer width, Integer height, boolean isImgCompress) throws IOException {
		if (StringUtils.isEmpty(rootPath)) {
			rootPath = System.getProperty("user.home");
		}
		if (!rootPath.endsWith(File.separator)) {
			rootPath += File.separator;
		}
		checkPath(rootPath);

		//虚拟路径,用与访问
		String path = "file" + File.separator + FORMATTER.format(LocalDate.now()) + File.separator;
		//真实物理路径
		String filePath = rootPath + path;

		String originalFilename = file.getOriginalFilename();
		assert originalFilename != null;
		String extendName = originalFilename.substring(originalFilename.lastIndexOf("."));

		//文件新名字
		final String fileName;
		if (StringUtils.isEmpty(customName)) {
			fileName = KeyUtils.get32uuid() + extendName;
		} else {
			fileName = customName;
		}
		transferToFile(file, filePath, fileName);
		if (!Arrays.asList(imgType).contains(extendName)) {
			// 不是指定图片，不压缩缩放
			isImgCompress = false;
		}
		if (isImgCompress) {
			executor.execute(()->{
				String realFile = filePath + File.separator + fileName;
				try {
					ImageUtils.compressImage(realFile, width, height);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});

		}
		return path + fileName;
	}

	public static void checkPath(String path) throws IllegalArgumentException {
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
	 * @throws IOException 异常
	 */
	public static void download(@NotNull HttpServletResponse response, @NotNull File file) throws IOException {
		response.setHeader("content-type", "image/png");
		// 文件流,(可能下载, 可能打开[浏览器有插件会先打开文件])
		response.setContentType("application/octet-stream");
		// 强制下载文件，不打开
		// response.setContentType("application/force-download");
		response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(file.getName(), "UTF-8"));
		byte[] buff = new byte[1024];
		//创建缓冲输入流
		try (OutputStream outputStream = response.getOutputStream();
		     BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
			int read = bis.read(buff);
			//通过while循环写入到指定了的文件夹中
			while (read != -1) {
				outputStream.write(buff, 0, buff.length);
				outputStream.flush();
				read = bis.read(buff);
			}
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
		// response.setContentType("application/force-download");
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
		return getBase64FromInputStream(new FileInputStream(file));
	}

	/**
	 * 将网络文件MultipartFile转存成内存File
	 *
	 * @param file     流文件
	 * @param filePath 文件卢静
	 * @param fileName 文件名称
	 * @throws IOException IO异常
	 */
	public static void transferToFile(MultipartFile file, String filePath, String fileName) throws IOException {
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
		transferToFile(file, dir);
	}

	/**
	 * 将网络文件MultipartFile转存成内存File
	 *
	 * @param uploadFile 流文件
	 * @param localFile  内存文件
	 * @throws IOException IO异常
	 */
	public static void transferToFile(@NotNull MultipartFile uploadFile, @NotNull File localFile) throws IOException {
		//写入文档中
		uploadFile.transferTo(localFile);
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
	@Deprecated
	public static @NotNull FileInputStream fileToInputStream(@NotNull File file) throws FileNotFoundException {
		return new FileInputStream(file);
	}

	@Deprecated
	public static @NotNull FileInputStream readFile(@NotNull String path) throws FileNotFoundException {
		return new FileInputStream(path);
	}

	/**
	 * inputStream转outputStream, 该方法阻塞
	 *
	 * @param in 输入流
	 * @return 输出流
	 * @throws IOException io异常
	 */
	public static @NotNull ByteArrayOutputStream parse(@NotNull InputStream in) throws IOException {
		ByteArrayOutputStream byteArr = new ByteArrayOutputStream(in.available());
		parse(in, byteArr);
		return byteArr;
	}

	/**
	 * inputStream转outputStream, 该方法阻塞
	 *
	 * @param in 输入流
	 * @param os 输出流
	 * @throws IOException io异常
	 */
	public static void parse(@NotNull InputStream in, OutputStream os) throws IOException {
		byte[] io = new byte[in.available()];
		int ch = in.read(io);
		if (ch > 0) {
			os.write(io);
		}
	}
}
