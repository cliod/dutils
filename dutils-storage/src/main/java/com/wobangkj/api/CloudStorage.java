package com.wobangkj.api;

import com.wobangkj.bean.Summary;

import java.io.*;
import java.util.List;

/**
 * 云存储
 *
 * @author cliod
 * @since 9/11/20 10:54 AM
 */
public interface CloudStorage {

	/**
	 * 获取当前存储空间名称
	 *
	 * @return 存储空间
	 */
	String getBucketName();

	/**
	 * 从输入流中上传
	 *
	 * @param is       输入流
	 * @param fileName 文件名称
	 * @return 文件url
	 */
	String upload(InputStream is, String fileName);

	/**
	 * 从文件中上传
	 *
	 * @param file 文件
	 * @return 文件url
	 * @throws FileNotFoundException 文件未找到
	 */
	default String upload(File file) throws FileNotFoundException {
		return this.upload(new FileInputStream(file), file.getName());
	}

	/**
	 * 下载到输出流
	 *
	 * @param fileName 文件名称, 包括OSS文件夹路径
	 * @return 文件流
	 */
	InputStream download(String fileName);

	/**
	 * 下载到文件流
	 *
	 * @param os       输出流
	 * @param fileName 文件名称
	 * @throws IOException IO异常
	 */
	default void download(OutputStream os, String fileName) throws IOException {
		InputStream is = download(fileName);
		int ch;
		while ((ch = is.read()) != -1) {
			os.write(ch);
		}
	}

	/**
	 * 列出当前存储空间中所有文件
	 *
	 * @return 文件列表
	 */
	default List<Summary> listObjects() {
		return this.listObjects(getBucketName());
	}

	/**
	 * 列出指定存储空间所有文件
	 *
	 * @param bucketName 存储空间名称
	 * @return 文件列表
	 */
	List<Summary> listObjects(String bucketName);

	/**
	 * 查看对象存储空间信息
	 *
	 * @return 对象信息
	 */
	Object getBucketInfo();

	/**
	 * 删除对象
	 *
	 * @param fileName 对象名称
	 */
	void delete(String fileName);

	/**
	 * 切换存储空间
	 *
	 * @param isCreate   不存在是否自动创建
	 * @param bucketName 存储空间名称
	 */
	void checkout(String bucketName, boolean isCreate);
}
