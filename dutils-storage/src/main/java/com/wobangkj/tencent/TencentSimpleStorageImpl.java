package com.wobangkj.tencent;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
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
public class TencentSimpleStorageImpl implements CloudStorage {

	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

	private final Map<String, COSClient> cache = new HashMap<>();

	private final String region;
	private final String secretId;
	private final String secretKey;
	@Getter
	private String bucketName;
	private String prefixUrl;
	private COSClient client;

	public TencentSimpleStorageImpl(String region, String secretId, String secretKey, String bucketName) {
		this.secretId = secretId;
		this.secretKey = secretKey;
		this.region = region;
		this.bucketName = bucketName;
		this.prefixUrl = bucketName + "." + region;
		init(true);
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
		this.prefixUrl = bucketName + "." + region;
	}

	/**
	 * 初始化
	 */
	public void init(boolean isCreate) {
		client = this.cache.get(bucketName);
		if (Objects.isNull(client)) {
			// 1 初始化用户身份信息（secretId, secretKey）。
			COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
			// 2 设置 bucket 的区域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
			// clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
			Region region = new Region(this.region);
			ClientConfig clientConfig = new ClientConfig(region);
			// 3 生成 cos 客户端。
			client = new COSClient(cred, clientConfig);

			// 判断Bucket是否存在。
			if (!client.doesBucketExist(bucketName)) {
				log.info("您的Bucket不存在，创建Bucket：" + bucketName + "。");
				// 创建Bucket。
				if (isCreate) {
					client.createBucket(bucketName);
				} else {
					throw new CosClientException("存储空间不存在: " + bucketName);
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
		String key = LocalDate.now().format(formatter) + File.separator + fileName;
		PutObjectResult result = client.putObject(this.bucketName, key, is, new ObjectMetadata());
		log.debug("[OSS文件存入成功]: {}", result.getETag());
		return this.prefixUrl + key;
	}

	/**
	 * 从文件中上传
	 *
	 * @param file 文件
	 * @return 文件url
	 */
	@Override
	public String upload(File file) {
		// 日期划分文件夹
		String key = LocalDate.now().format(formatter) + File.separator + file.getName();
		PutObjectResult result = client.putObject(this.bucketName, key, file);
		log.debug("[OSS文件存入成功]: {}", result.getETag());
		return this.prefixUrl + key;
	}

	/**
	 * 下载到输出流
	 *
	 * @param fileName 文件名称, 包括OSS文件夹路径
	 */
	@Override
	public InputStream download(String fileName) {
		COSObject cosObject = client.getObject(bucketName, fileName);
		return cosObject.getObjectContent();
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
	public Bucket getBucketInfo() {// 查看Bucket信息。
		List<Bucket> buckets = client.listBuckets();
		return buckets.stream().filter(e -> Objects.equals(e.getName(), this.getBucketName())).findFirst().orElse(null);
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
