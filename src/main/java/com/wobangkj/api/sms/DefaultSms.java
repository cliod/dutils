package com.wobangkj.api.sms;

import com.aliyuncs.AcsResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.wobangkj.bean.Pageable;
import com.wobangkj.utils.JsonUtils;
import com.wobangkj.utils.KeyUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 短信发送工具类
 *
 * @author cliod
 * @since 2020/08/02
 */
@Deprecated
public class DefaultSms implements Sms {
	static {
		//设置超时时间-可自行调整
		System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
		System.setProperty("sun.net.client.defaultReadTimeout", "10000");
	}

	private final SmsParam smsParam;
	private final IClientProfile profile;
	private final IAcsClient client;
	private String outId;

	private DefaultSms(SmsParam smsParam) {
		this.smsParam = smsParam;
		profile = DefaultProfile.getProfile(smsParam.regionId, smsParam.accessKeyId, smsParam.accessSecret);
		client = new DefaultAcsClient(profile);
	}

	private DefaultSms(SmsParam smsParam, DefaultProfile profile) {
		this.smsParam = smsParam;
		if (Objects.isNull(profile))
			this.profile = DefaultProfile.getProfile(smsParam.regionId, smsParam.accessKeyId, smsParam.accessSecret);
		else
			this.profile = profile;
		this.client = new DefaultAcsClient(profile);
	}

	public static DefaultSms getInstance(SmsParam smsParam) {
		return new DefaultSms(smsParam);
	}

	public AcsResponse send(String signName, String phoneNumbers, String templateCode, String templateParamJson) throws ClientException {
		SendSmsRequest request = new SendSmsRequest();
		request.setSysMethod(MethodType.POST);
		request.setSysVersion("2017-05-25");
		request.setSysRegionId(smsParam.regionId);
		request.setPhoneNumbers(phoneNumbers);
		request.setSignName(signName);
		request.setTemplateCode(templateCode);
		request.setTemplateParam(templateParamJson);
		this.outId = KeyUtils.get32uuid();
		request.setOutId(this.outId);
		return client.getAcsResponse(request);
	}

	public String getOutId() {
		return outId;
	}

	@Override
	public String getSignName() {
		return this.smsParam.signName;
	}

	@Override
	public void setSignName(String signName) {
		this.smsParam.signName = signName;
	}

	@Override
	public AcsResponse send(String template, Map<String, Object> params, String signName, String phoneNumbers) throws ClientException {
		return this.send(signName, phoneNumbers, JsonUtils.toJson(params), template);
	}

	@Override
	public AcsResponse batchSend(String template, List<Map<String, Object>> params, String signNames, String phoneNumbers) {
		throw new UnsupportedOperationException("不支持操作");
	}

	@Override
	public AcsResponse querySendDetails(String phoneNumber, LocalDate date, String bizId, Pageable pageable) {
		throw new UnsupportedOperationException("不支持操作");
	}

	@Override
	public SmsSign getSmsSign() {
		throw new UnsupportedOperationException("不支持操作");
	}

	@Override
	public SmsSign getSmsTemplate() {
		throw new UnsupportedOperationException("不支持操作");
	}

	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class SmsParam {
		private String accessKeyId;
		private String accessSecret;
		private String signName;
		private String regionId;
	}
}
