package com.wobangkj.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.builder.ExcelReaderSheetBuilder;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.util.DateUtils;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.wobangkj.api.Name;
import com.wobangkj.api.SyncSaveListener;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * excel utils
 *
 * @author @cliod
 * @since 4/28/20 9:38 AM
 */
public class ExcelUtils {
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
	private static final String DEFAULT_PATH = System.getProperty("user.dir") + "/file/excel/";

	private static String ROOT_PATH;

	public static String getRootPath() {
		if (StringUtils.isEmpty(ROOT_PATH)) {
			return DEFAULT_PATH;
		}
		return ROOT_PATH;
	}

	public static void setRootPath(String rootPath) {
		ROOT_PATH = rootPath;
	}

	private ExcelUtils() {
	}

	/**
	 * 导出文件
	 *
	 * @param data 数据
	 * @return 文件
	 * @throws IOException io异常
	 */
	public static @NotNull File write(List<?> data) throws IOException {
		return write(data, null);
	}

	/**
	 * 导出文件
	 *
	 * @param data 数据
	 * @param head 标题
	 * @return 文件
	 * @throws IOException io异常
	 */
	public static @NotNull File write(List<?> data, Class<?> head) throws IOException {
		return write(data, head, ExcelTypeEnum.XLS);
	}

	/**
	 * 导出文件
	 *
	 * @param data     数据
	 * @param head     标题
	 * @param fileType 文件类型
	 * @return 文件
	 * @throws IOException io异常
	 */
	public static @NotNull File write(List<?> data, Class<?> head, @NotNull ExcelTypeEnum fileType) throws IOException {
		String path = LocalDate.now().format(FORMATTER) + "/" + getName(head) + "/";
		String name = KeyUtils.get32uuid();
		String filePath = getRootPath() + path;
		String fileName = filePath + name + fileType.getValue();
		File file = createFile(filePath, fileName);
		write(file, data, head, fileType);
		return file;
	}

	/**
	 * 核心写操作
	 * <p>1. 创建excel对应的实体对象 参照{@link com.alibaba.excel.EasyExcelFactory}
	 * <p>2. 直接写即可
	 *
	 * @param data 数据
	 * @param file 文件
	 * @param head excel标题
	 */
	public static void write(File file, List<?> data, Class<?> head) {
		write(file, data, head, "Sheet1", ExcelTypeEnum.XLS);
	}

	/**
	 * 核心写操作
	 * <p>1. 创建excel对应的实体对象 参照{@link com.alibaba.excel.EasyExcelFactory}
	 * <p>2. 直接写即可
	 *
	 * @param data     数据
	 * @param fileType 文件类型
	 * @param head     excel标题
	 */
	public static void write(File file, List<?> data, Class<?> head, @NotNull ExcelTypeEnum fileType) {
		write(file, data, head, "Sheet1", fileType);
	}

	/**
	 * 核心写操作
	 * <p>1. 创建excel对应的实体对象 参照{@link com.alibaba.excel.EasyExcelFactory}
	 * <p>2. 直接写即可
	 *
	 * @param data      数据
	 * @param fileType  文件类型
	 * @param head      excel标题
	 * @param sheetName 表格名
	 */
	public static void write(File file, List<?> data, Class<?> head, String sheetName, @NotNull ExcelTypeEnum fileType) {
		EasyExcel.write(file, head)
				.excelType(fileType).sheet(0, sheetName).registerWriteHandler(getStyleStrategy()).doWrite(data);
	}

	/**
	 * 核心写操作
	 * <p>1. 创建excel对应的实体对象 参照{@link com.alibaba.excel.EasyExcelFactory}
	 * <p>2. 直接写即可
	 *
	 * @param data      数据
	 * @param fileName  文件名
	 * @param filePath  文件路径
	 * @param fileType  文件类型
	 * @param head      excel标题
	 * @param sheetName 表格名
	 */
	public static void write(String filePath, String fileName, List<?> data, Class<?> head, String sheetName, ExcelTypeEnum fileType) throws IOException {
		File file = createFile(filePath, fileName);
		EasyExcel.write(file, head)
				.excelType(fileType).sheet(0, sheetName).registerWriteHandler(getStyleStrategy()).doWrite(data);
	}

	/**
	 * 导出
	 *
	 * @param os   响应
	 * @param data 导出数据
	 */
	public static void write(OutputStream os, List<?> data) {
		write(os, data, null);
	}

	/**
	 * 导出
	 *
	 * @param os   响应
	 * @param data 导出数据
	 * @param head 导出对象类型
	 */
	public static void write(OutputStream os, List<?> data, Class<?> head) {
		write(os, data, head, "Sheet1", ExcelTypeEnum.XLS);
	}

	/**
	 * 导出
	 *
	 * @param os        响应
	 * @param data      导出数据
	 * @param sheetName 表格名称
	 * @param head      导出对象类型
	 */
	public static void write(OutputStream os, List<?> data, Class<?> head, String sheetName, @NotNull ExcelTypeEnum fileType) {
		EasyExcel.write(os, head)
				.excelType(fileType).sheet(sheetName).registerWriteHandler(getStyleStrategy()).doWrite(data);
	}

	/**
	 * 导出
	 *
	 * @param response 响应
	 * @param data     导出数据
	 * @throws IOException 异常
	 */
	@Deprecated
	public static void write(HttpServletResponse response, List<?> data) throws IOException {
		write(response, data, null);
	}

	/**
	 * 导出
	 *
	 * @param response 响应
	 * @param data     导出数据
	 * @param head     导出对象类型
	 * @throws IOException 异常
	 */
	@Deprecated
	public static void write(HttpServletResponse response, List<?> data, Class<?> head) throws IOException {
		write(response, data, head, KeyUtils.get32uuid(), "Sheet1", ExcelTypeEnum.XLS);
	}

	/**
	 * 导出
	 *
	 * @param response  响应
	 * @param data      导出数据
	 * @param fileName  文件名称
	 * @param sheetName 表格名称
	 * @param head      导出对象类型
	 * @throws IOException 异常
	 */
	@Deprecated
	public static void write(HttpServletResponse response, List<?> data, Class<?> head, String fileName, String sheetName, ExcelTypeEnum fileType) throws IOException {
		fileName = URLEncoder.encode(fileName, "UTF-8");
		response.setContentType("application/octet-stream");
		response.setCharacterEncoding("utf8");
		response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName + fileType.getValue());
		EasyExcel.write(response.getOutputStream(), head)
				.excelType(fileType).sheet(sheetName).registerWriteHandler(getStyleStrategy()).doWrite(data);
	}

	/**
	 * 填充excel模板
	 *
	 * @param file   文件
	 * @param data   数据
	 * @param attach 附加数据，目前只有pay_account
	 */
	public static void fill(String templateFileName, File file, List<?> data, Map<String, Object> attach) {
		// 模板注意 用{} 来表示你要用的变量 如果本来就有"{","}" 特殊字符 用"\{","\}"代替
		// {} 代表普通变量 {.} 代表是list的变量
		ExcelWriter excelWriter = EasyExcel.write(file).withTemplate(templateFileName).build();
		WriteSheet writeSheet = EasyExcel.writerSheet().registerWriteHandler(getStyleStrategy()).build();
		// 这里注意 入参用了forceNewRow 代表在写入list的时候不管list下面有没有空行 都会创建一行，然后下面的数据往后移动。默认 是false，会直接使用下一行，如果没有则创建。
		// forceNewRow 如果设置了true,有个缺点 就是他会把所有的数据都放到内存了，所以慎用
		// 简单的说 如果你的模板有list,且list不是最后一行，下面还有数据需要填充 就必须设置 forceNewRow=true 但是这个就会把所有数据放到内存 会很耗内存
		// 如果数据量大 list不是最后一行 参照下一个
		FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.TRUE).build();
		excelWriter.fill(data, fillConfig, writeSheet);
		excelWriter.fill(attach, writeSheet);
		excelWriter.finish();
	}

	/**
	 * 读取数据进行操作
	 *
	 * @param file     文件流
	 * @param type     模型
	 * @param listener 监听并操作
	 * @param <T>      模型类型
	 */
	@Deprecated
	public static <T> void read(@NotNull MultipartFile file, @NotNull Class<T> type, ReadListener<T> listener) throws IOException {
		// 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
		EasyExcel.read(file.getInputStream(), type, listener).sheet().doRead();
	}

	/**
	 * 读取excel内容并操作
	 *
	 * @param file 网络文件
	 * @param type 存入类型
	 * @param func 监听器将执行的操作
	 * @param <T>  类型
	 * @throws IOException io异常
	 */
	@Deprecated
	public static <T> void read(@NotNull MultipartFile file, Class<T> type, Consumer<List<T>> func) throws IOException {
		EasyExcel.read(file.getInputStream(), type, SyncSaveListener.of(func)).sheet().doRead();
	}

	/**
	 * 读取数据进行操作
	 *
	 * @param is       文件流
	 * @param type     模型
	 * @param listener 监听并操作
	 * @param <T>      模型类型
	 */
	public static <T> void read(@NotNull InputStream is, @NotNull Class<T> type, ReadListener<T> listener) {
		// 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
		EasyExcel.read(is, type, listener).sheet().doRead();
	}

	/**
	 * 读取excel内容并操作
	 *
	 * @param is   文件流
	 * @param type 存入类型
	 * @param func 监听器将执行的操作
	 * @param <T>  类型
	 */
	public static <T> void read(@NotNull InputStream is, Class<T> type, Consumer<List<T>> func) {
		EasyExcel.read(is, type, SyncSaveListener.of(func)).sheet().doRead();
	}

	/**
	 * 读取数据进行操作
	 *
	 * @param is       文件流
	 * @param type     模型
	 * @param listener 监听并操作
	 * @param <T>      模型类型
	 */
	public static <T> void read(@NotNull InputStream is, @NotNull Class<T> type, Object sheet, ReadListener<T> listener) {
		// 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
		ExcelReaderBuilder builder = EasyExcel.read(is, type, listener);
		ExcelReaderSheetBuilder sheetBuilder;
		if (sheet instanceof Number) {
			sheetBuilder = builder.sheet(((Number) sheet).intValue());
		} else {
			sheetBuilder = builder.sheet(sheet.toString());
		}
		sheetBuilder.doRead();
	}

	/**
	 * 读取excel内容并操作
	 *
	 * @param is   文件流
	 * @param type 存入类型
	 * @param func 监听器将执行的操作
	 * @param <T>  类型
	 */
	public static <T> void read(@NotNull InputStream is, Class<T> type, Object sheet, Consumer<List<T>> func) {
		ExcelReaderBuilder builder = EasyExcel.read(is, type, SyncSaveListener.of(func));
		ExcelReaderSheetBuilder sheetBuilder;
		if (sheet instanceof Number) {
			sheetBuilder = builder.sheet(((Number) sheet).intValue());
		} else {
			sheetBuilder = builder.sheet(sheet.toString());
		}
		sheetBuilder.doRead();
	}

	/**
	 * 创建目录和文件
	 *
	 * @param filePath 文件目录
	 * @param fileName 目标文件
	 * @return 结果
	 * @throws IOException IO异常
	 */
	public static @NotNull File createFile(String filePath, String fileName) throws IOException {
		File path = new File(filePath);
		if (!path.exists()) {
			if (!path.mkdirs()) {
				throw new IOException("文件夹创建失败");
			}
		}
		File file = new File(fileName);
		if (!file.exists()) {
			if (!file.createNewFile()) {
				throw new IOException("文件创建失败");
			}
		}
		return file;
	}

	/**
	 * 获取表格名称
	 *
	 * @param head 表头
	 * @return 结果
	 */
	public static String getName(Class<?> head) {
		String defaultFileName = "data_" + DateUtils.format(new Date(), "yyyyMMddHHmm");
		if (Objects.isNull(head)) {
			return defaultFileName;
		}
		Name name = head.getDeclaredAnnotation(Name.class);
		if (Objects.isNull(name)) {
			return defaultFileName;
		}
		return StringUtils.isBlank(name.value()) ? defaultFileName : name.value();
	}

	@Deprecated
	public static @NotNull CellWriteHandler getStyleStrategy() {
		return new LongestMatchColumnWidthStyleStrategy();
	}
}
