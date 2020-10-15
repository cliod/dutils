package com.wobangkj.api;

import java.time.LocalDate;

/**
 * 短信api
 *
 * @author cliod
 * @since 8/7/20 2:43 PM
 */
public interface Sms {

	/**
	 * 获取短信签名
	 *
	 * @return 短信签名
	 */
	String getSignName();

	/**
	 * 发送单条短信(可以多个手机号)
	 *
	 * @param template     短信模板ID
	 * @param params       短信模板变量对应的实际值，JSON格式
	 * @param signName     签名名称
	 * @param phoneNumbers 接收短信的手机号码。
	 *                     <p>
	 *                     格式：
	 *                     国内短信：11位手机号码，例如15951955195。
	 *                     国际/港澳台消息：国际区号+号码，例如85200000000。
	 *                     支持对多个手机号码发送短信，手机号码之间以英文逗号（,）分隔。上限为1000个手机号码。批量调用相对于单条调用及时性稍有延迟。
	 *                     </p>
	 * @return 结果
	 * @throws Exception 发送异常
	 */
	Object send(final String template, String params, String signName, String phoneNumbers) throws Exception;

	/**
	 * 查看短信发送记录和发送状态
	 *
	 * @param phoneNumber 手机号
	 * @param date        日期支持查询最近30天的记录。格式为yyyyMMdd
	 * @param bizId       回执id
	 * @param page        分页查看发送记录，指定发送记录的的当前页码
	 * @param size        分页查看发送记录，指定每页显示的短信记录数量。取值范围为1~50
	 * @return 结果
	 * @throws Exception 查询异常
	 */
	Object querySendDetails(String phoneNumber, LocalDate date, Integer page, Integer size) throws Exception;

	/**
	 * 获取短信操作
	 *
	 * @return 短信
	 */
	default Sms getSms() {
		return this;
	}

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
