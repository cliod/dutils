package com.wobangkj.tencent;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.*;

/**
 * 短信签名操作
 *
 * @author cliod
 * @since 8/7/20 4:10 PM
 */
public class TcsSmsTemplateImpl implements TcsSmsTemplate {

	private final SmsClient client;

	private TcsSmsTemplateImpl(SmsClient client) {
		this.client = client;
	}

	protected TcsSmsTemplateImpl(Credential cred, String region) {
		this(new SmsClient(cred, region, Profile.getDefaultProfile()));
	}

	protected TcsSmsTemplateImpl(String region, String secretId, String secretKey) {
		this(new Credential(secretId, secretKey), region);
	}

	public static TcsSmsTemplateImpl getInstance(String regionId, String secretId, String secretKey) {
		return new TcsSmsTemplateImpl(regionId, secretId, secretKey);
	}

	public static TcsSmsTemplateImpl getInstance(SmsClient client) {
		return new TcsSmsTemplateImpl(client);
	}

	@Override
	public AddSmsTemplateResponse add(Integer type, Long international, String name, String content, String remark) throws TencentCloudSDKException {
		AddSmsTemplateRequest request = new AddSmsTemplateRequest();
		request.setSmsType(type.longValue());
		request.setTemplateName(name);
		request.setTemplateContent(content);
		request.setRemark(remark);
		request.setInternational(international);
		return this.client.AddSmsTemplate(request);
	}

	@Override
	public DeleteSmsTemplateResponse delete(Long templateCode) throws TencentCloudSDKException {
		DeleteSmsTemplateRequest request = new DeleteSmsTemplateRequest();
		request.setTemplateId(templateCode);
		return this.client.DeleteSmsTemplate(request);
	}

	@Override
	public ModifySmsTemplateResponse modify(Long templateId, Integer type, Long international, String name, String content,
	                                        String remark) throws TencentCloudSDKException {
		ModifySmsTemplateRequest request = new ModifySmsTemplateRequest();
		request.setTemplateId(templateId);
		request.setTemplateName(name);
		request.setTemplateContent(content);
		request.setSmsType(type.longValue());
		request.setInternational(international);
		request.setRemark(remark);
		return this.client.ModifySmsTemplate(request);
	}

	@Override
	public DescribeSmsTemplateListResponse query(Long[] templateCode, Long international) throws TencentCloudSDKException {
		DescribeSmsTemplateListRequest request = new DescribeSmsTemplateListRequest();
		request.setTemplateIdSet(templateCode);
		request.setInternational(international);
		return this.client.DescribeSmsTemplateList(request);
	}
}
