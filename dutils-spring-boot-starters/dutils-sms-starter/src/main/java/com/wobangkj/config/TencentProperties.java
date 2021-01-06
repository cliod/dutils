package com.wobangkj.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * ak-as配置属性
 *
 * @author cliod
 * @since 10/20/20 10:04 AM
 */
@Data
@ConfigurationProperties(prefix = "tencent")
public class TencentProperties {

	private String accessKeyId;
	private String secret;
	private String appId;
}
