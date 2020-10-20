package com.wobangkj.config;

import com.aliyuncs.DefaultAcsClient;
import com.wobangkj.ali.AcsSms;
import com.wobangkj.ali.AcsSmsImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author cliod
 * @since 10/20/20 10:04 AM
 */
@Configuration
@ConditionalOnClass(value = DefaultAcsClient.class)
@EnableConfigurationProperties(AliProperties.class)
public class AliSmsConfiguration {

	@Bean
	public AcsSms acsSms(AliProperties aliProperties) {
		return AcsSmsImpl.getInstance(aliProperties.getRegionId(), aliProperties.getAccessKey(), aliProperties.getAccessSecret());
	}

}
