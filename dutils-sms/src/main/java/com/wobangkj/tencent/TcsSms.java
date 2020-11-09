package com.wobangkj.tencent;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.sms.v20190711.models.PullSmsSendStatusByPhoneNumberResponse;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;
import com.tencentcloudapi.sms.v20190711.models.SendStatusStatisticsResponse;
import com.tencentcloudapi.sms.v20190711.models.SmsPackagesStatisticsResponse;
import com.wobangkj.api.Pageable;
import com.wobangkj.api.Sms;
import com.wobangkj.api.SmsExt;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 短信api
 *
 * @author cliod
 * @since 8/7/20 2:43 PM
 */
public interface TcsSms extends Sms, SmsExt {
	/**
	 * 获取默认对象(初始化对象)
	 *
	 * @param accessKeyId 访问密钥
	 * @param secret      访问密钥
	 * @param appId       sdk-app-id
	 * @return Sms对象
	 * @see TcsSmsImpl 默认实现
	 */
	static TcsSmsImpl getInstance(String accessKeyId, String secret, String appId) {
		return TcsSmsImpl.getInstance(accessKeyId, secret, appId);
	}

	/**
	 * 获取默认对象(初始化对象)
	 *
	 * @param cred  访问参数
	 * @param appId sdk-app-id
	 * @return Sms对象
	 * @see TcsSmsImpl 默认实现
	 */
	static TcsSmsImpl getInstance(Credential cred, String appId) {
		return TcsSmsImpl.getInstance(cred, appId);
	}

	/**
	 * 获取短信签名
	 *
	 * @return 短信签名
	 */
	@Override
	String getSignName();

	/**
	 * 设置短信签名
	 *
	 * @param signName 短信签名
	 */
	void setSignName(String signName);

	/**
	 * 获取流水号
	 *
	 * @return 外部流水扩展字段
	 */
	String getAppId();

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
	 * @throws TencentCloudSDKException 发送异常
	 */
	@Override
	default SendSmsResponse send(final String template, String params, String signName, String phoneNumbers) throws TencentCloudSDKException {
		return this.send(template, params.split(","), signName, phoneNumbers.split(","));
	}

	/**
	 * 发送单条短信(可以多个手机号)
	 *
	 * @param template     短信模板
	 * @param params       模板参数
	 * @param signName     签名
	 * @param phoneNumbers 手机号, 多个
	 * @return 结果
	 * @throws TencentCloudSDKException 发送异常
	 */
	SendSmsResponse send(final String template, String[] params, String signName, String... phoneNumbers) throws TencentCloudSDKException;

	/**
	 * 发送单条短信(可以多个手机号)
	 *
	 * @param template     短信模板
	 * @param params       模板参数
	 * @param signName     签名
	 * @param phoneNumbers 手机号, 多个逗号隔开
	 * @return 结果
	 * @throws TencentCloudSDKException 发送异常
	 */
	default SendSmsResponse send(final String template, String[] params, String signName, List<String> phoneNumbers) throws TencentCloudSDKException {
		return this.send(template, params, signName, phoneNumbers.toArray(new String[0]));
	}

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
	 * @throws TencentCloudSDKException 发送异常
	 */
	default SendSmsResponse send(final String template, List<String> params, String signName, String... phoneNumbers) throws TencentCloudSDKException {
		return this.send(template, params.toArray(new String[0]), signName, phoneNumbers);
	}

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
	 * @throws TencentCloudSDKException 发送异常
	 */
	default SendSmsResponse send(final String template, List<String> params, String signName, List<String> phoneNumbers) throws TencentCloudSDKException {
		return this.send(template, params.toArray(new String[0]), signName, phoneNumbers);
	}

	/**
	 * 发送单条短信(可以多个手机号)
	 *
	 * @param template     短信模板
	 * @param params       模板参数
	 * @param phoneNumbers 手机号, 多个逗号隔开
	 * @return 结果
	 * @throws TencentCloudSDKException 发送异常
	 */
	default SendSmsResponse send(final String template, String[] params, String... phoneNumbers) throws TencentCloudSDKException {
		return this.send(template, params, getSignName(), phoneNumbers);
	}

	/**
	 * 发送单条短信(可以多个手机号)
	 *
	 * @param template     短信模板
	 * @param params       模板参数
	 * @param phoneNumbers 手机号, 多个逗号隔开
	 * @return 结果
	 * @throws TencentCloudSDKException 发送异常
	 */
	default SendSmsResponse send(final String template, String[] params, List<String> phoneNumbers) throws TencentCloudSDKException {
		return this.send(template, params, getSignName(), phoneNumbers);
	}

	/**
	 * 发送单条短信(可以多个手机号)
	 *
	 * @param template     短信模板
	 * @param params       模板参数
	 * @param phoneNumbers 手机号, 多个逗号隔开
	 * @return 结果
	 * @throws TencentCloudSDKException 发送异常
	 */
	default SendSmsResponse send(final String template, List<String> params, String... phoneNumbers) throws TencentCloudSDKException {
		return this.send(template, params, getSignName(), phoneNumbers);
	}

	/**
	 * 发送单条短信(可以多个手机号)
	 *
	 * @param template     短信模板
	 * @param params       模板参数
	 * @param phoneNumbers 手机号, 多个逗号隔开
	 * @return 结果
	 * @throws TencentCloudSDKException 发送异常
	 */
	default SendSmsResponse send(final String template, List<String> params, List<String> phoneNumbers) throws TencentCloudSDKException {
		return this.send(template, params, getSignName(), phoneNumbers);
	}

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
	@Override
	default Object batchSend(final String template, String params, String signName, List<String> phoneNumbers) throws Exception {
		return this.send(template, params, signName, String.join(",", phoneNumbers));
	}

	/**
	 * 批量发送短信(单个模板,但是不同签名和多个手机号)
	 *
	 * @param template     模板
	 * @param params       模板参数
	 * @param signName     签名
	 * @param phoneNumbers 手机号
	 * @return 结果
	 * @throws Exception 发送异常
	 */
	default Object batchSend(final String template, String[] params, String signName, String... phoneNumbers) throws Exception {
		return this.send(template, params, signName, phoneNumbers);
	}

	/**
	 * 查看短信发送记录和发送状态
	 *
	 * @param phoneNumber 手机号
	 * @param date        日期支持查询最近30天的记录。格式为yyyyMMdd
	 * @param page        分页查看发送记录，指定发送记录的的当前页码
	 * @param size        分页查看发送记录，指定每页显示的短信记录数量。取值范围为1~50
	 * @return 结果
	 * @throws TencentCloudSDKException 发送异常
	 */
	@Override
	PullSmsSendStatusByPhoneNumberResponse query(String phoneNumber, LocalDate date, Integer page, Integer size) throws TencentCloudSDKException;

	/**
	 * 查看短信发送记录和发送状态
	 *
	 * @param phoneNumber 手机号
	 * @param date        日期支持查询最近30天的记录。格式为yyyyMMdd
	 * @param pageable    分页查看发送记录
	 * @return 结果
	 * @throws TencentCloudSDKException 发送异常
	 */
	default PullSmsSendStatusByPhoneNumberResponse query(String phoneNumber, LocalDate date, Pageable pageable) throws TencentCloudSDKException {
		return this.query(phoneNumber, date, pageable.getPage(), pageable.getSize());
	}

	/**
	 * 套餐包信息统计
	 *
	 * @param size 分页
	 * @param page 分页
	 * @return 结果
	 * @throws TencentCloudSDKException 短息发送异常
	 */
	SmsPackagesStatisticsResponse statistics(Integer page, Integer size) throws TencentCloudSDKException;

	/**
	 * 套餐包信息统计
	 *
	 * @param pageable 分页
	 * @return 结果
	 * @throws TencentCloudSDKException 异常
	 */
	default SmsPackagesStatisticsResponse statistics(Pageable pageable) throws TencentCloudSDKException {
		return this.statistics(pageable.getPage(), pageable.getSize());
	}

	/**
	 * 发送短信数据统计
	 *
	 * @param startTime 时间段
	 * @param endTime   时间段
	 * @param page      分页
	 * @param size      分页
	 * @return 结果
	 * @throws TencentCloudSDKException 异常
	 */
	SendStatusStatisticsResponse statistics(LocalDateTime startTime, LocalDateTime endTime, Integer page, Integer size) throws TencentCloudSDKException;

	/**
	 * 发送短信数据统计
	 *
	 * @param startTime 时间段
	 * @param endTime   时间段
	 * @param pageable  分页
	 * @return 结果
	 * @throws TencentCloudSDKException 异常
	 */
	default SendStatusStatisticsResponse statistics(LocalDateTime startTime, LocalDateTime endTime, Pageable pageable) throws TencentCloudSDKException {
		return this.statistics(startTime, endTime, pageable.getPage(), pageable.getSize());
	}

	/**
	 * 获取短信操作
	 *
	 * @return 短信
	 */
	@Override
	default TcsSms getSms() {
		return this;
	}

	/**
	 * 获取短信签名操作
	 *
	 * @return 短信签名
	 */
	@Override
	TcsSmsSign getSmsSign();

	/**
	 * 获取短信模板操作
	 *
	 * @return 短信模板
	 */
	@Override
	TcsSmsTemplate getSmsTemplate();
}
