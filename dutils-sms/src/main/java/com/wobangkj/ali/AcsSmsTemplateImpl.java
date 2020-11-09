package com.wobangkj.ali;

import com.aliyuncs.AcsResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.AddSmsTemplateRequest;
import com.aliyuncs.dysmsapi.model.v20170525.DeleteSmsTemplateRequest;
import com.aliyuncs.dysmsapi.model.v20170525.ModifySmsTemplateRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySmsTemplateRequest;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

/**
 * 短信签名操作
 *
 * @author cliod
 * @since 8/7/20 4:10 PM
 */
public class AcsSmsTemplateImpl implements AcsSmsTemplate {

	private final IAcsClient client;
	private final String regionId;

	private AcsSmsTemplateImpl(String regionId, IAcsClient client) {
		this.client = client;
		this.regionId = regionId;
	}

	protected AcsSmsTemplateImpl(IClientProfile profile) {
		this(profile.getRegionId(), new DefaultAcsClient(profile));
	}

	protected AcsSmsTemplateImpl(String regionId, String accessKeyId, String accessSecret) {
		this(regionId, new DefaultAcsClient(DefaultProfile.getProfile(regionId, accessKeyId, accessSecret)));
	}

	public static AcsSmsTemplateImpl getInstance(String regionId, String accessKeyId, String accessSecret) {
		return new AcsSmsTemplateImpl(regionId, accessKeyId, accessSecret);
	}

	public static AcsSmsTemplateImpl getInstance(IClientProfile profile) {
		return new AcsSmsTemplateImpl(profile);
	}

	@Override
	public AcsResponse add(Integer type, String name, String content, String remark) throws ClientException {
		AddSmsTemplateRequest request = new AddSmsTemplateRequest();
		request.setTemplateType(type);
		request.setTemplateName(name);
		request.setTemplateContent(content);
		request.setRemark(remark);
		request.setSysRegionId(this.regionId);
		return this.client.getAcsResponse(request);
	}

	@Override
	public AcsResponse delete(String templateCode) throws ClientException {
		DeleteSmsTemplateRequest request = new DeleteSmsTemplateRequest();
		request.setTemplateCode(templateCode);
		request.setSysRegionId(this.regionId);
		return this.client.getAcsResponse(request);
	}

	@Override
	public AcsResponse modify(String templateCode, Integer type, String name, String content, String remark) throws ClientException {
		ModifySmsTemplateRequest request = new ModifySmsTemplateRequest();
		request.setTemplateCode(templateCode);
		request.setTemplateType(type);
		request.setTemplateName(name);
		request.setTemplateContent(content);
		request.setRemark(remark);
		request.setSysRegionId(this.regionId);
		return this.client.getAcsResponse(request);
	}

	@Override
	public AcsResponse query(String templateCode) throws ClientException {
		QuerySmsTemplateRequest request = new QuerySmsTemplateRequest();
		request.setTemplateCode(templateCode);
		request.setSysRegionId(this.regionId);
		return this.client.getAcsResponse(request);
	}
}
