package com.wobangkj.ali;

import com.aliyuncs.*;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendBatchSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.wobangkj.utils.KeyUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 发送短信
 *
 * @author cliod
 * @since 8/7/20 4:10 PM
 */
public class AcsSmsImpl implements AcsSms, Cloneable {

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

	private AcsSmsImpl(IClientProfile profile, IAcsClient client) {
		this.profile = profile;
		this.client = client;
		String regionId = profile.getRegionId();
		sms = new SendSmsRequest();
		sms.setSysRegionId(regionId);
		batch = new SendBatchSmsRequest();
		batch.setSysRegionId(regionId);
	}

	protected AcsSmsImpl(IClientProfile profile) {
		this(profile, new DefaultAcsClient(profile));
	}

	protected AcsSmsImpl(String regionId, String accessKeyId, String accessSecret) {
		this(DefaultProfile.getProfile(regionId, accessKeyId, accessSecret));
	}

	public static AcsSmsImpl getInstance(String regionId, String accessKeyId, String secret) {
		return new AcsSmsImpl(regionId, accessKeyId, secret);
	}

	public static AcsSmsImpl getInstance(IClientProfile profile) {
		return new AcsSmsImpl(profile);
	}

	/**
	 * 发送短信
	 *
	 * @param templateCode      短信模板ID
	 * @param templateParamJson 短信模板参数
	 * @param phoneNumbers      手机号. 支持对多个手机号码发送短信，手机号码之间以英文逗号（,）分隔。上限为1000个手机号码。
	 * @return 发送结果封装
	 * @throws ClientException 短信发送异常
	 */
	@Override
	public CommonResponse commonSend(String templateCode, String templateParamJson, String signName, String phoneNumbers) throws ClientException {
		final CommonRequest request = new CommonRequest();
		request.setSysMethod(MethodType.POST);
		request.setSysDomain("dysmsapi.aliyuncs.com");
		request.setSysVersion("2017-05-25");
		request.setSysAction("SendSms");
		request.putQueryParameter("RegionId", this.profile.getRegionId());
		request.putQueryParameter("PhoneNumbers", phoneNumbers);
		request.putQueryParameter("SignName", signName);
		request.putQueryParameter("TemplateCode", templateCode);
		request.putQueryParameter("TemplateParam", templateParamJson);
		return client.getCommonResponse(request);
	}

	@Override
	public AcsResponse send(String template, String params, String signName, List<String> phoneNumbers) throws ClientException {
		// 支持对多个手机号码发送短信，手机号码之间以英文逗号（,）分隔。上限为1000个手机号码。
		this.sms.setPhoneNumbers(String.join(",", phoneNumbers));
		this.sms.setSignName(signName);
		this.sms.setTemplateCode(template);
		this.sms.setTemplateParam(params);
		this.outId = KeyUtils.get32uuid();
		this.sms.setOutId(this.outId);
		return this.client.getAcsResponse(this.sms);
	}

	/**
	 * 批量发送短信(单个模板,但是不同签名和多个手机号)
	 *
	 * @param template     模板
	 * @param params       模板参数
	 * @param signNames    签名
	 * @param phoneNumbers 手机号
	 * @return 结果
	 */
	@Override
	public AcsResponse batchSend(String template, String params, String signNames, String phoneNumbers) throws ClientException {
		this.batch.setTemplateParamJson(params);
		this.batch.setSignNameJson(signNames);
		this.batch.setTemplateCode(template);
		this.batch.setPhoneNumberJson(phoneNumbers);
		this.outId = KeyUtils.get32uuid();
		return this.client.getAcsResponse(this.batch);
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
	 */
	@Override
	public AcsResponse query(String phoneNumber, LocalDate date, String bizId, Integer page, Integer size) throws ClientException {
		QuerySendDetailsRequest query = new QuerySendDetailsRequest();
		query.setPhoneNumber(phoneNumber);
		query.setSendDate(date.format(this.formatter));
		query.setPageSize(page.longValue());
		query.setCurrentPage(size.longValue());
		query.setBizId(bizId);
		return this.client.getAcsResponse(query);
	}

	/**
	 * 获取短信操作
	 *
	 * @return 短信
	 */
	@Override
	@SneakyThrows
	public AcsSmsImpl getSms() {
		return this.clone();
	}

	@Override
	public AcsSmsSign getSmsSign() {
		return AcsSmsSign.getInstance(this.profile);
	}

	@Override
	public AcsSmsTemplate getSmsTemplate() {
		return AcsSmsTemplate.getInstance(this.profile);
	}

	/**
	 * 克隆, 返回一个新Sms对象
	 *
	 * @return a clone of this instance.
	 * @throws CloneNotSupportedException 不会发生的异常.
	 * @see Cloneable 需要实现Cloneable接口
	 */
	@Override
	protected AcsSmsImpl clone() throws CloneNotSupportedException {
		return (AcsSmsImpl) super.clone();
	}
}
