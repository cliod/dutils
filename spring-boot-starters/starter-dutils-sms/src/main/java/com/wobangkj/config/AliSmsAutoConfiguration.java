package com.wobangkj.config;

import com.aliyuncs.DefaultAcsClient;
import com.wobangkj.ali.AcsSms;
import com.wobangkj.ali.AcsSmsImpl;
import com.wobangkj.tencent.TcsSms;
import com.wobangkj.tencent.TcsSmsImpl;
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
@EnableConfigurationProperties({AliProperties.class, TencentProperties.class})
public class AliSmsAutoConfiguration {

	@Bean
	public AcsSms acsSms(AliProperties aliProperties) {
		return AcsSmsImpl.getInstance(aliProperties.getRegionId(), aliProperties.getAccessKey(), aliProperties.getAccessSecret());
	}

	@Bean
	public TcsSms tcsSms(TencentProperties tencentProperties) {
		return TcsSmsImpl.getInstance(tencentProperties.getAccessKeyId(), tencentProperties.getSecret(), tencentProperties.getAppId());
	}
}
