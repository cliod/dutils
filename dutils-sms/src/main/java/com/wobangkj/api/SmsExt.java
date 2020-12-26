package com.wobangkj.api;

import java.util.List;

/**
 * 短信api
 *
 * @author cliod
 * @since 8/7/20 2:43 PM
 */
public interface SmsExt {

	/**
	 * 获取短信签名
	 *
	 * @return 短信签名
	 */
	String getSignName();

	/**
	 * 批量发送短信(单个模板,多个手机号)
	 *
	 * @param template     模板
	 * @param params       模板参数
	 * @param signName     签名
	 * @param phoneNumbers 手机号
	 * @return 结果
	 * @throws Exception 发送异常
	 */
	Object batchSend(final String template, String params, String signName, List<String> phoneNumbers) throws Exception;

	/**
	 * 获取短信签名操作
	 *
	 * @return 短信签名
	 */
	SmsSign getSmsSign();

	/**
	 * 获取短信模板操作
	 *
	 * @return 短信模板
	 */
	SmsTemplate getSmsTemplate();
}
