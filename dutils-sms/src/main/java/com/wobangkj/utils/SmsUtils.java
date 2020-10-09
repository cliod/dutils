package com.wobangkj.utils;

import com.aliyuncs.AcsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.wobangkj.api.Sms;

import java.util.Map;

/**
 * 短信发送工具类
 *
 * @author cliod
 * @since 2020/08/02
 */
public class SmsUtils {

	private static Sms sms = Sms.getInstance(DefaultProfile.getProfile());

	private SmsUtils() {
	}

	/**
	 * 初始化
	 */
	public static void init(String regionId, String accessKeyId, String secret, String signName) {
		sms = Sms.getInstance(regionId, accessKeyId, secret);
		sms.setSignName(signName);
	}

	/**
	 * 根据手机号和模板 发送无参数短信
	 *
	 * @param phoneNumber  手机号
	 * @param templateCode 模板
	 * @return 响应
	 * @throws ClientException 客户端异常
	 */
	public static AcsResponse send(final String templateCode, String... phoneNumber) throws ClientException {
		return send("", templateCode, phoneNumber);
	}

	/**
	 * 根据手机号和模板 发送指定参数的短信
	 *
	 * @param phoneNumber  手机号
	 * @param templateCode 模板
	 * @return 响应
	 * @throws ClientException 客户端异常
	 */
	public static AcsResponse send(final String templateCode, Map<String, Object> params, String... phoneNumber) throws ClientException {
		final String templateParam = JsonUtils.toJson(params);
		return send(templateCode, templateParam, phoneNumber);
	}

	/**
	 * 根据手机号和模板 发送指定参数的短信
	 *
	 * @param templateParamJson 参数
	 * @param phoneNumber       手机号
	 * @param templateCode      模板
	 * @return 响应
	 * @throws ClientException 客户端异常
	 */
	public static AcsResponse send(String templateCode, String templateParamJson, String... phoneNumber) throws ClientException {
		return sms.send(templateCode, templateParamJson, phoneNumber);
	}
}
