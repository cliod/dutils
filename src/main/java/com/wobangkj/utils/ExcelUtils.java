package com.wobangkj.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.wobangkj.api.excel.DataListener;
import com.wobangkj.api.excel.Model;
import com.wobangkj.api.excel.SaveListener;
import com.wobangkj.enums.Format;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * excel utils
 *
 * @author @cliod
 * @since 4/28/20 9:38 AM
 * package: com.wobangkj.jzlw.utils
 */
public class ExcelUtils {
    private final static String key = "/file/excel/";
    private final static String ROOT_PATH = "";
    private final static String FILE_DEFAULT_NAME_PRE = "data";

    private ExcelUtils() {
    }

    public static @NotNull File write(List<?> data, Class<?> head) throws IOException {
        return write(data, head, null, null);
    }

    public static @NotNull File write(List<?> data, Class<?> head, Map<String, Object> attach) throws IOException {
        return write(data, head, null, attach);
    }

    public static @NotNull File write(List<?> data, Class<?> head, ExcelTypeEnum fileType, Map<String, Object> attach) throws IOException {
        if (Objects.isNull(fileType)) fileType = ExcelTypeEnum.XLS;
        String path = DateUtils.getNow(Format.DATE.getPattern()) + "/" + getName(head) + "/";
        String name = KeyUtils.get32uuid();
        String filePath = ROOT_PATH + "/file/tmp/" + path;
        String fileName = filePath + name + fileType.getValue();
        File file = create(filePath, fileName);
        write(file, data, head, fileType);
        return file;
    }

    /**
     * 核心写操作
     * <p>1. 创建excel对应的实体对象 参照{@link com.alibaba.excel.EasyExcelFactory}
     * <p>2. 直接写即可
     */
    public static void write(File file, List<?> data, Class<?> head, ExcelTypeEnum fileType) {
        HorizontalCellStyleStrategy strategy = new HorizontalCellStyleStrategy(null, (WriteCellStyle) null);
        EasyExcel.write(file, head).excelType(fileType).sheet(0, "Sheet1").registerWriteHandler(strategy).doWrite(data);
    }

    /**
     * 导出
     *
     * @param response 响应
     * @param data     导出数据
     * @param clazz    导出对象类型
     * @throws Exception 异常
     */
    public static void write(HttpServletResponse response, List<?> data, Class<?> clazz) throws Exception {
        write(response, data, clazz, KeyUtils.get32uuid(), "sheet1", ExcelTypeEnum.XLS);
    }

    /**
     * 导出
     *
     * @param response  响应
     * @param data      导出数据
     * @param fileName  文件名称
     * @param sheetName 表格名称
     * @param clazz     导出对象类型
     * @throws Exception 异常
     */
    public static void write(HttpServletResponse response, List<?> data, Class<?> clazz, String fileName, String sheetName, ExcelTypeEnum typeEnum) throws Exception {
        //表头样式
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        //设置表头居中对齐
        headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        //内容样式
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        //设置内容靠左对齐
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.LEFT);
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
        EasyExcel.write(getOutputStream(fileName, response, "application/vnd.ms-excel"), clazz).excelType(typeEnum).sheet(sheetName).registerWriteHandler(horizontalCellStyleStrategy).doWrite(data);
    }

    /**
     * 创建目录和文件
     *
     * @param filePath 文件目录
     * @param fileName 目标文件
     * @return 结果
     * @throws IOException IO异常
     */
    public static @NotNull File create(String filePath, String fileName) throws IOException {
        File path = new File(filePath);
        if (!path.exists())
            if (!path.mkdirs()) throw new IOException("文件夹创建失败");
        File file = new File(fileName);
        if (!file.exists())
            if (!file.createNewFile()) throw new IOException("文件创建失败");
        return file;
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
        WriteSheet writeSheet = EasyExcel.writerSheet().build();
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
     * @param is       文件流
     * @param type     模型
     * @param listener 监听并操作
     * @param <T>      模型类型
     */
    public static <T extends Model> void read(InputStream is, @NotNull Class<T> type, DataListener<T> listener) {
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(is, type, listener).sheet().doRead();
    }

    public static <T> void read(@NotNull MultipartFile file, Class<T> type, Consumer<List<T>> func) throws IOException {
        EasyExcel.read(file.getInputStream(), type, new SaveListener<>(func));
    }

    /**
     * 获取输出流响应
     *
     * @param fileName 文件名称
     * @param response 响应
     * @return 结果
     * @throws Exception 异常
     */
    public static OutputStream getOutputStream(String fileName, @NotNull HttpServletResponse response, String contentType) throws Exception {
        fileName = URLEncoder.encode(fileName, "UTF-8");
        response.setContentType(contentType);
        response.setCharacterEncoding("utf8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
        return response.getOutputStream();
    }

    /**
     * 获取表格名称
     *
     * @param head 表头
     * @return 结果
     */
    public static String getName(@NotNull Class<?> head) {
        com.wobangkj.api.excel.Name name = head.getDeclaredAnnotation(com.wobangkj.api.excel.Name.class);
        if (Objects.isNull(name)) {
            return FILE_DEFAULT_NAME_PRE;
        }
        return StringUtils.isBlank(name.value()) ? FILE_DEFAULT_NAME_PRE : name.value();
    }
}
