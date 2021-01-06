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
@ConfigurationProperties(prefix = "ali")
public class AliProperties {

	private String accessKey;
	private String accessSecret;
	private String regionId;
}
