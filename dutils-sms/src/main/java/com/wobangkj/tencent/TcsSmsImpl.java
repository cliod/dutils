package com.wobangkj.tencent;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.*;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoField;

/**
 * 发送短信
 *
 * @author cliod
 * @since 8/7/20 4:10 PM
 */
public class TcsSmsImpl implements TcsSms, Cloneable {

	private final SmsClient client;
	/**
	 * 外部流水号
	 */
	@Getter
	private final String appId;
	@Getter
	@Setter
	private String signName;

	private TcsSmsImpl(SmsClient client, String appId) {
		this.client = client;
		this.appId = appId;
	}

	protected TcsSmsImpl(Credential cred, String region, String appId) {
		this(new SmsClient(cred, region, Profile.getDefaultProfile()), appId);
	}

	protected TcsSmsImpl(String region, String secretId, String secretKey, String appId) {
		this(new Credential(secretId, secretKey), region, appId);
	}

	public static TcsSmsImpl getInstance(String accessKeyId, String secret, String regionId, String appId) {
		return new TcsSmsImpl(accessKeyId, secret, regionId, appId);
	}

	public static TcsSmsImpl getInstance(Credential cred, String region, String appId) {
		return new TcsSmsImpl(cred, region, appId);
	}

	public static TcsSmsImpl getInstance(String accessKeyId, String secret, String appId) {
		return new TcsSmsImpl(accessKeyId, secret, "", appId);
	}

	public static TcsSmsImpl getInstance(Credential cred, String appId) {
		return new TcsSmsImpl(cred, "", appId);
	}

	@Override
	public SendSmsResponse send(String template, String[] params, String signName, String... phoneNumbers) throws TencentCloudSDKException {
		// 支持对多个手机号码发送短信，手机号码之间以英文逗号（,）分隔。上限为1000个手机号码。
		SendSmsRequest request = new SendSmsRequest();
		request.setTemplateID(template);
		request.setTemplateParamSet(params);
		request.setPhoneNumberSet(phoneNumbers);
		request.setSign(signName);
		// 短信SdkAppid在 短信控制台 添加应用后生成的实际SdkAppid，示例如1400006666。
		request.setSmsSdkAppid(this.appId);
		// 用户的 session 内容，可以携带用户侧 ID 等上下文信息，server 会原样返回
		request.setSessionContext("");
		return this.client.SendSms(request);
	}

	/**
	 * 查看短信发送记录和发送状态
	 *
	 * @param phoneNumber 手机号
	 * @param date        日期支持查询最近30天的记录。格式为yyyyMMdd
	 * @param page        分页查看发送记录，指定发送记录的的当前页码
	 * @param size        分页查看发送记录，指定每页显示的短信记录数量。取值范围为1~50
	 * @return 结果
	 */
	@Override
	public PullSmsSendStatusByPhoneNumberResponse query(String phoneNumber, LocalDate date, Integer page, Integer size) throws TencentCloudSDKException {
		PullSmsSendStatusByPhoneNumberRequest query = new PullSmsSendStatusByPhoneNumberRequest();
		query.setLimit(page.longValue());
		query.setOffset(size.longValue());
		query.set("PhoneNumber", phoneNumber);
		query.setSmsSdkAppid(this.appId);
		query.setSendDateTime(date.plusDays(-7).atStartOfDay(ZoneOffset.systemDefault()).toInstant().toEpochMilli());
		query.setEndDateTime(date.atStartOfDay(ZoneOffset.systemDefault()).toInstant().toEpochMilli());
		return this.client.PullSmsSendStatusByPhoneNumber(query);
	}

	/**
	 * 套餐包信息统计
	 *
	 * @return 结果
	 */
	@Override
	public SmsPackagesStatisticsResponse statistics(Integer page, Integer size) throws TencentCloudSDKException {
		SmsPackagesStatisticsRequest request = new SmsPackagesStatisticsRequest();
		request.setSmsSdkAppid(this.appId);
		request.setLimit(size.longValue());
		request.setOffset(page.longValue());
		return this.client.SmsPackagesStatistics(request);
	}

	/**
	 * 套餐包信息统计
	 *
	 * @return 结果
	 */
	@Override
	public SendStatusStatisticsResponse statistics(LocalDateTime startTime, LocalDateTime endTime, Integer page, Integer size) throws TencentCloudSDKException {
		SendStatusStatisticsRequest request = new SendStatusStatisticsRequest();
		request.setStartDateTime(startTime.getLong(ChronoField.MILLI_OF_DAY));
		request.setEndDataTime(endTime.getLong(ChronoField.MILLI_OF_DAY));
		request.setSmsSdkAppid(this.appId);
		request.setLimit(size.longValue());
		request.setOffset(page.longValue());
		return this.client.SendStatusStatistics(request);
	}

	/**
	 * 获取短信操作
	 *
	 * @return 短信
	 */
	@Override
	@SneakyThrows
	public TcsSmsImpl getSms() {
		return this.clone();
	}

	@Override
	public TcsSmsSign getSmsSign() {
		return TcsSmsSign.getInstance(this.client);
	}

	@Override
	public TcsSmsTemplate getSmsTemplate() {
		return TcsSmsTemplate.getInstance(this.client);
	}

	/**
	 * 克隆, 返回一个新Sms对象
	 *
	 * @return a clone of this instance.
	 * @throws CloneNotSupportedException 不会发生的异常.
	 * @see Cloneable 需要实现Cloneable接口
	 */
	@Override
	protected TcsSmsImpl clone() throws CloneNotSupportedException {
		return (TcsSmsImpl) super.clone();
	}
}
