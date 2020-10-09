package com.wobangkj.api;

import com.aliyuncs.AcsResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendBatchSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.wobangkj.utils.KeyUtils;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 发送短信
 *
 * @author cliod
 * @since 8/7/20 4:10 PM
 */
public class SmsImpl implements Sms {

	private final IClientProfile profile;
	private final IAcsClient client;
	/**
	 * 参数
	 */
	private final SendSmsRequest sms;
	private final SendBatchSmsRequest batch;

	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	@Getter
	@Setter
	private String signName;
	/**
	 * 外部流水号
	 */
	@Getter
	private String outId;

	protected SmsImpl(IClientProfile profile, IAcsClient client) {
		this.profile = profile;
		this.client = client;
		String regionId = profile.getRegionId();
		sms = new SendSmsRequest();
		sms.setSysRegionId(regionId);
		batch = new SendBatchSmsRequest();
		batch.setSysRegionId(regionId);
	}

	public SmsImpl(IClientProfile profile) {
		this(profile, new DefaultAcsClient(profile));
	}

	public SmsImpl(String regionId, String accessKeyId, String accessSecret) {
		this(DefaultProfile.getProfile(regionId, accessKeyId, accessSecret));
	}

	public static SmsImpl getInstance(String regionId, String accessKeyId, String secret) {
		return new SmsImpl(regionId, accessKeyId, secret);
	}

	public static SmsImpl getInstance(IClientProfile profile) {
		return new SmsImpl(profile);
	}

	@Override
	public AcsResponse send(String template, String params, String signName, String phoneNumbers) throws ClientException {
		// 支持对多个手机号码发送短信，手机号码之间以英文逗号（,）分隔。上限为1000个手机号码。
		this.sms.setPhoneNumbers(phoneNumbers);
		this.sms.setSignName(signName);
		this.sms.setTemplateCode(template);
		this.sms.setTemplateParam(params);
		this.outId = KeyUtils.get32uuid();
		this.sms.setOutId(this.outId);
		return this.client.getAcsResponse(this.sms);
	}

	@Override
	public AcsResponse batchSend(String template, String params, String signNames, String phoneNumbers) throws ClientException {
		this.batch.setTemplateParamJson(params);
		this.batch.setSignNameJson(signNames);
		this.batch.setTemplateCode(template);
		this.batch.setPhoneNumberJson(phoneNumbers);
		return this.client.getAcsResponse(this.batch);
	}

	@Override
	public AcsResponse querySendDetails(String phoneNumber, LocalDate date, String bizId, Pageable pageable) throws ClientException {
		QuerySendDetailsRequest query = new QuerySendDetailsRequest();
		query.setPhoneNumber(phoneNumber);
		query.setSendDate(date.format(this.formatter));
		query.setPageSize(pageable.getSize().longValue());
		query.setCurrentPage(pageable.getPage().longValue());
		query.setBizId(bizId);
		return this.client.getAcsResponse(query);
	}

	/**
	 * 获取短信操作
	 *
	 * @return 短信
	 */
	@Override
	public Sms getSms() {
		return new SmsImpl(this.profile);
	}

	@Override
	public SmsSign getSmsSign() {
		return SmsSign.getInstance(this.profile);
	}

	@Override
	public SmsTemplate getSmsTemplate() {
		return SmsTemplate.getInstance(this.profile);
	}
}
