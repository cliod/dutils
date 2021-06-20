package com.wobangkj.utils;

import com.aliyuncs.AcsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.IClientProfile;
import com.wobangkj.ali.AcsSms;
import com.wobangkj.ali.AcsSmsImpl;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;

/**
 * 短信发送工具类
 *
 * @author cliod
 * @since 2020/08/02
 */
public class SmsUtils {

	private static AcsSms sms = null;

	private SmsUtils() {
	}

	public static AcsResponse send(IClientProfile profile, String signName, String templateCode, String templateParamJson, String phoneNumber, String... phoneNumbers) throws ClientException {
		sms = AcsSmsImpl.getInstance(profile);
		sms.setSignName(signName);
		return sms.send(templateCode, templateParamJson, phoneNumber, phoneNumbers);
	}

	public static AcsResponse send(IClientProfile profile, String signName, String templateCode, Map<String, Object> templateParam, String phoneNumber, String... phoneNumbers) throws ClientException {
		sms = AcsSmsImpl.getInstance(profile);
		sms.setSignName(signName);
		return sms.send(templateCode, templateParam, phoneNumber, phoneNumbers);
	}
}
