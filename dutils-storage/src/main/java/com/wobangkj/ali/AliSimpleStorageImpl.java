package com.wobangkj.ali;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.model.*;
import com.wobangkj.api.CloudStorage;
import com.wobangkj.bean.Summary;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 阿里OSS储存
 *
 * @author cliod
 * @since 9/11/20 11:03 AM
 */
@Slf4j
public class AliSimpleStorageImpl implements CloudStorage {

	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

	private final Map<String, OSSClient> cache = new HashMap<>();

	private final String endpoint;
	private final String accessKeyId;
	private final String accessKeySecret;
	@Getter
	private String bucketName;
	private String prefixUrl;
	private OSSClient client;

	public AliSimpleStorageImpl(String endpoint, String accessKeyId, String accessKeySecret, String bucketName) {
		this.accessKeyId = accessKeyId;
		this.accessKeySecret = accessKeySecret;
		this.endpoint = endpoint;
		this.bucketName = bucketName;
		this.prefixUrl = bucketName + "." + endpoint;
		init(true);
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
		this.prefixUrl = bucketName + "." + endpoint;
	}

	/**
	 * 初始化
	 */
	public void init(boolean isCreate) {
		client = this.cache.get(bucketName);
		if (Objects.isNull(client)) {
			client = new OSSClient(endpoint, new DefaultCredentialProvider(accessKeyId, accessKeySecret), new ClientBuilderConfiguration());
			// 判断Bucket是否存在。
			if (!client.doesBucketExist(bucketName)) {
				log.info("您的Bucket不存在，创建Bucket：" + bucketName + "。");
				// 创建Bucket。
				if (isCreate) {
					client.createBucket(bucketName);
				} else {
					throw new ClientException("存储空间不存在: " + bucketName);
				}
			}
			//设置权限 这里是公开读
			client.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
			this.cache.put(bucketName, client);
		}
	}

	/**
	 * 从输入流中上传
	 *
	 * @param is       输入流
	 * @param fileName 文件名称
	 */
	@Override
	public String upload(InputStream is, String fileName) {
		// 日期划分文件夹
		String suffix = LocalDate.now().format(formatter) + File.separator + fileName;
		PutObjectResult result = client.putObject(this.bucketName, suffix, is);
		log.debug("[OSS文件存入成功]: {}", result.getETag());
		return this.prefixUrl + suffix;
	}

	/**
	 * 下载到输出流
	 *
	 * @param fileName 文件名称, 包括OSS文件夹路径
	 */
	@Override
	public InputStream download(String fileName) {
		OSSObject ossObject = client.getObject(bucketName, fileName);
		return ossObject.getObjectContent();
	}

	/**
	 * 列出所有文件
	 *
	 * @return 文件列表
	 */
	@Override
	public List<Summary> listObjects(String bucketName) {
		// 查看Bucket中的Object。
		ObjectListing objectListing = client.listObjects(bucketName);
		return objectListing.getObjectSummaries().stream().map(e ->
				Summary.of(e.getSize(), prefixUrl + e.getKey(), e.getStorageClass(), com.wobangkj.bean.Owner.of(e.getOwner()), e.getLastModified())
		).collect(Collectors.toList());
	}

	/**
	 * 查看对象信息
	 *
	 * @return 对象信息
	 */
	@Override
	public BucketInfo getBucketInfo() {// 查看Bucket信息。
		return client.getBucketInfo(bucketName);
	}

	/**
	 * 删除对象
	 *
	 * @param fileName 对象名称
	 */
	@Override
	public void delete(String fileName) {
		client.deleteObject(bucketName, fileName);
		log.debug("[OSS文件删除成功]: {}", fileName);
	}

	/**
	 * 切换存储空间
	 *
	 * @param bucketName 存储空间名称
	 * @param isCreate   不存在是否自动创建
	 */
	@Override
	public void checkout(String bucketName, boolean isCreate) {
		this.setBucketName(bucketName);
		this.init(isCreate);
	}
}
