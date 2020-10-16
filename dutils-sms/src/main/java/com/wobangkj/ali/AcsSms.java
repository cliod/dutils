package com.wobangkj.ali;

import com.aliyuncs.AcsResponse;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.IClientProfile;
import com.wobangkj.ali.bean.BatchSendParam;
import com.wobangkj.api.Pageable;
import com.wobangkj.api.Sms;
import com.wobangkj.api.SmsExt;
import com.wobangkj.utils.JsonUtils;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 短信api
 *
 * @author cliod
 * @since 8/7/20 2:43 PM
 */
public interface AcsSms extends Sms, SmsExt {
	/**
	 * 获取默认对象(初始化对象)
	 *
	 * @param regionId    区域id
	 * @param accessKeyId 访问密钥
	 * @param secret      访问密钥
	 * @return Sms对象
	 * @see AcsSmsImpl 默认实现
	 */
	static AcsSms getInstance(String regionId, String accessKeyId, String secret) {
		return AcsSmsImpl.getInstance(regionId, accessKeyId, secret);
	}

	/**
	 * 获取默认对象(初始化对象)
	 *
	 * @param profile 访问参数
	 * @return Sms对象
	 * @see AcsSmsImpl 默认实现
	 */
	static AcsSms getInstance(IClientProfile profile) {
		return AcsSmsImpl.getInstance(profile);
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
	String getOutId();

	/**
	 * 兼容发送短信
	 *
	 * @param templateCode      短信模板ID
	 * @param templateParamJson 短信模板参数
	 * @param signName          短信签名
	 * @param phoneNumbers      手机号
	 * @return 发送结果封装
	 * @throws ClientException 短信发送异常
	 */
	CommonResponse commonSend(String templateCode, String templateParamJson, String signName, String phoneNumbers) throws ClientException;

	/**
	 * 兼容发送短信
	 *
	 * @param templateCode      短信模板ID
	 * @param templateParamJson 短信模板参数
	 * @param phoneNumbers      手机号
	 * @return 发送结果封装
	 * @throws ClientException 短信发送异常
	 */
	default CommonResponse commonSend(String templateCode, String templateParamJson, String phoneNumbers) throws ClientException {
		return this.commonSend(templateCode, templateParamJson, getSignName(), phoneNumbers);
	}

	/**
	 * 发送短信
	 *
	 * @param phoneNumbers  手机号
	 * @param templateCode  短信模板ID
	 * @param templateParam 短信模板参数
	 * @return 发送结果封装
	 * @throws ClientException 短信发送异常
	 */
	default CommonResponse commonSend(String templateCode, Map<String, Object> templateParam, List<String> phoneNumbers) throws ClientException {
		return this.commonSend(templateCode, JsonUtils.toJson(templateParam), getSignName(), String.join(",", phoneNumbers));
	}

	/**
	 * 发送短信
	 *
	 * @param phoneNumbers  手机号
	 * @param templateCode  短信模板ID
	 * @param templateParam 短信模板参数
	 * @return 发送结果封装
	 * @throws ClientException 短信发送异常
	 */
	default CommonResponse commonSend(String templateCode, Map<String, Object> templateParam, String phoneNumber, String... phoneNumbers) throws ClientException {
		return this.commonSend(templateCode, JsonUtils.toJson(templateParam), getSignName(), phoneNumber + "," + String.join(",", phoneNumbers));
	}

	/**
	 * 发送单条短信(可以多个手机号)
	 *
	 * @param template    短信模板ID
	 * @param params      短信模板变量对应的实际值，JSON格式
	 * @param signName    签名名称
	 * @param phoneNumber 接收短信的手机号码。
	 *                    <p>
	 *                    格式：
	 *                    国内短信：11位手机号码，例如15951955195。
	 *                    国际/港澳台消息：国际区号+号码，例如85200000000。
	 *                    支持对多个手机号码发送短信，手机号码之间以英文逗号（,）分隔。上限为1000个手机号码。批量调用相对于单条调用及时性稍有延迟。
	 *                    </p>
	 * @return 结果
	 * @throws ClientException 发送异常
	 */
	@Override
	default AcsResponse send(final String template, String params, String signName, String phoneNumber) throws ClientException {
		return this.send(template, params, signName, Collections.singletonList(phoneNumber));
	}

	/**
	 * 发送单条短信(可以多个手机号)
	 *
	 * @param template     短信模板
	 * @param params       模板参数
	 * @param signName     签名
	 * @param phoneNumbers 手机号, 多个
	 * @return 结果
	 * @throws ClientException 发送异常
	 */
	default AcsResponse send(final String template, String params, String signName, String phoneNumber, String... phoneNumbers) throws ClientException {
		return this.send(template, params, signName, phoneNumber + "," + String.join(",", phoneNumbers));
	}

	/**
	 * 发送单条短信(可以多个手机号)
	 *
	 * @param template     短信模板
	 * @param params       模板参数
	 * @param signName     签名
	 * @param phoneNumbers 手机号, 多个逗号隔开
	 * @return 结果
	 * @throws ClientException 发送异常
	 */
	AcsResponse send(final String template, String params, String signName, List<String> phoneNumbers) throws ClientException;

	/**
	 * 发送单条短信(可以多个手机号)
	 *
	 * @param template     短信模板
	 * @param params       模板参数
	 * @param phoneNumbers 手机号, 多个逗号隔开
	 * @return 结果
	 * @throws ClientException 发送异常
	 */
	default AcsResponse send(final String template, String params, String phoneNumbers) throws ClientException {
		return this.send(template, params, getSignName(), phoneNumbers);
	}

	/**
	 * 发送单条短信(可以多个手机号)
	 *
	 * @param template     短信模板
	 * @param params       模板参数
	 * @param phoneNumbers 手机号, 多个逗号隔开
	 * @return 结果
	 * @throws ClientException 发送异常
	 */
	default AcsResponse send(final String template, String params, String phoneNumber, String... phoneNumbers) throws ClientException {
		return this.send(template, params, getSignName(), phoneNumber, phoneNumbers);
	}

	/**
	 * 发送单条短信(可以多个手机号)
	 *
	 * @param template     短信模板
	 * @param params       模板参数
	 * @param phoneNumbers 手机号, 多个逗号隔开
	 * @return 结果
	 * @throws ClientException 发送异常
	 */
	default AcsResponse send(final String template, String params, List<String> phoneNumbers) throws ClientException {
		return this.send(template, params, getSignName(), phoneNumbers);
	}

	/**
	 * 发送单条短信(可以多个手机号)
	 *
	 * @param template     短信模板
	 * @param params       模板参数
	 * @param signName     签名
	 * @param phoneNumbers 手机号, 多个逗号隔开
	 * @return 结果
	 * @throws ClientException 发送异常
	 */
	default AcsResponse send(final String template, Map<String, Object> params, String signName, String phoneNumber, String... phoneNumbers) throws ClientException {
		return this.send(template, JsonUtils.toJson(params), signName, phoneNumber, phoneNumbers);
	}

	/**
	 * 发送单条短信(可以多个手机号)
	 *
	 * @param template     短信模板
	 * @param params       模板参数
	 * @param signName     签名
	 * @param phoneNumbers 手机号, 多个逗号隔开
	 * @return 结果
	 * @throws ClientException 发送异常
	 */
	default AcsResponse send(final String template, Map<String, Object> params, String signName, List<String> phoneNumbers) throws ClientException {
		return this.send(template, JsonUtils.toJson(params), signName, phoneNumbers);
	}

	/**
	 * 发送单条短信(可以多个手机号)
	 *
	 * @param template     短信模板
	 * @param params       模板参数
	 * @param phoneNumbers 手机号, 多个逗号隔开
	 * @return 结果
	 * @throws ClientException 发送异常
	 */
	default AcsResponse send(final String template, Map<String, Object> params, String phoneNumber, String... phoneNumbers) throws ClientException {
		return this.send(template, params, getSignName(), phoneNumber, phoneNumbers);
	}

	/**
	 * 发送单条短信(可以多个手机号)
	 *
	 * @param template     短信模板
	 * @param params       模板参数
	 * @param phoneNumbers 手机号, 多个逗号隔开
	 * @return 结果
	 * @throws ClientException 发送异常
	 */
	default AcsResponse send(final String template, Map<String, Object> params, List<String> phoneNumbers) throws ClientException {
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
	default AcsResponse batchSend(final String template, String params, String signName, List<String> phoneNumbers) throws Exception {
		return this.send(template, params, signName, phoneNumbers);
	}

	/**
	 * 批量发送短信(单个模板,但是不同签名和多个手机号)
	 *
	 * @param template     模板
	 * @param params       模板参数
	 * @param signNames    签名
	 * @param phoneNumbers 手机号
	 * @return 结果
	 * @throws ClientException 发送异常
	 */
	AcsResponse batchSend(final String template, String params, String signNames, String phoneNumbers) throws ClientException;

	/**
	 * 批量发送短信(单个模板,但是不同签名和多个手机号)
	 *
	 * @param template     模板
	 * @param params       模板参数
	 * @param signNames    签名
	 * @param phoneNumbers 手机号
	 * @return 结果
	 * @throws ClientException 发送异常
	 */
	default AcsResponse batchSend(final String template, List<String> params, List<String> signNames, List<String> phoneNumbers) throws ClientException {
		if (!(params.size() == phoneNumbers.size() && params.size() == signNames.size())) {
			throw new ClientException("参数错误：参数长度不一致");
		}
		return this.batchSend(template, String.join(",", params), String.join(",", signNames), String.join(",", phoneNumbers));
	}

	/**
	 * 批量发送短信(单个模板,但是不同签名和多个手机号)
	 *
	 * @param template 模板
	 * @param params   参数
	 * @return 结果
	 * @throws ClientException 发送异常
	 */
	default AcsResponse batchSend(final String template, List<BatchSendParam> params) throws ClientException {
		return this.batchSend(template, params.stream().map(BatchSendParam::getTemplateParamJson).collect(Collectors.toList()),
				params.stream().map(BatchSendParam::getSignName).collect(Collectors.toList()),
				params.stream().map(BatchSendParam::getPhoneNumber).collect(Collectors.toList()));
	}

	/**
	 * 查看短信发送记录和发送状态
	 *
	 * @param phoneNumber 手机号
	 * @param date        日期支持查询最近30天的记录。格式为yyyyMMdd
	 * @param bizId       回执id
	 * @param page        分页查看发送记录，指定发送记录的的当前页码
	 * @param size        分页查看发送记录，指定每页显示的短信记录数量。取值范围为1~50
	 * @return 结果
	 * @throws ClientException 发送异常
	 */
	AcsResponse query(String phoneNumber, LocalDate date, String bizId, Integer page, Integer size) throws ClientException;

	/**
	 * 查看短信发送记录和发送状态
	 *
	 * @param phoneNumber 手机号
	 * @param date        日期支持查询最近30天的记录。格式为yyyyMMdd
	 * @param bizId       回执id
	 * @param pageable    分页查看发送记录
	 * @return 结果
	 * @throws ClientException 发送异常
	 */
	default AcsResponse query(String phoneNumber, LocalDate date, String bizId, Pageable pageable) throws ClientException {
		return query(phoneNumber, date, bizId, pageable.getPage(), pageable.getSize());
	}

	/**
	 * 查看短信发送记录和发送状态
	 *
	 * @param phoneNumber 手机号
	 * @param date        日期支持查询最近30天的记录。格式为yyyyMMdd
	 * @param page        分页查看发送记录，指定发送记录的的当前页码
	 * @param size        分页查看发送记录，指定每页显示的短信记录数量。取值范围为1~50
	 * @return 结果
	 * @throws ClientException 发送异常
	 */
	@Override
	default AcsResponse query(String phoneNumber, LocalDate date, Integer page, Integer size) throws ClientException {
		return this.query(phoneNumber, date, null, page, size);
	}

	/**
	 * 查看短信发送记录和发送状态
	 *
	 * @param phoneNumber 手机号
	 * @param date        日期支持查询最近30天的记录。格式为yyyyMMdd
	 * @param pageable    分页查看发送记录
	 * @return 结果
	 * @throws ClientException 发送异常
	 */
	default AcsResponse query(String phoneNumber, LocalDate date, Pageable pageable) throws ClientException {
		return this.query(phoneNumber, date, null, pageable);
	}

	/**
	 * 获取短信操作
	 *
	 * @return 短信
	 */
	@Override
	default AcsSms getSms() {
		return this;
	}

	/**
	 * 获取短信签名操作
	 *
	 * @return 短信签名
	 */
	@Override
	AcsSmsSign getSmsSign();

	/**
	 * 获取短信模板操作
	 *
	 * @return 短信模板
	 */
	@Override
	AcsSmsTemplate getSmsTemplate();


}
