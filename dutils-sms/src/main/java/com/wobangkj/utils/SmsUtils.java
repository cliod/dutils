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

	/**
	 * 初始化
	 */
	@Deprecated
	public static void init(String regionId, String accessKeyId, String secret, String signName) {
		sms = AcsSmsImpl.getInstance(regionId, accessKeyId, secret);
		sms.setSignName(signName);
	}

	/**
	 * 初始化
	 */
	@Deprecated
	public static void init(IClientProfile profile, String signName) {
		sms = AcsSmsImpl.getInstance(profile);
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
	@Deprecated
	public static AcsResponse notify(final String templateCode, String phoneNumber, String... phoneNumbers) throws ClientException {
		return send(templateCode, "", phoneNumber, phoneNumbers);
	}

	/**
	 * 根据手机号和模板 发送指定参数的短信
	 *
	 * @param phoneNumber  手机号
	 * @param templateCode 模板
	 * @return 响应
	 * @throws ClientException 客户端异常
	 */
	@Deprecated
	public static AcsResponse send(final String templateCode, @NotNull Map<String, Object> params, String phoneNumber, String... phoneNumbers) throws ClientException {
		final String templateParam = JsonUtils.toJson(params);
		return send(templateCode, templateParam, phoneNumber, phoneNumbers);
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
	@Deprecated
	protected static AcsResponse send(String templateCode, String templateParamJson, String phoneNumber, String... phoneNumbers) throws ClientException {
		if (Objects.isNull(sms)) {
			throw new ClientException("实例未初始化");
		}
		return sms.send(templateCode, templateParamJson, phoneNumber, phoneNumbers);
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
