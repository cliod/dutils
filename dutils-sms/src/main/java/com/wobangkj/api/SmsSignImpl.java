package com.wobangkj.api;

import com.aliyuncs.AcsResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.AddSmsSignRequest;
import com.aliyuncs.dysmsapi.model.v20170525.DeleteSmsSignRequest;
import com.aliyuncs.dysmsapi.model.v20170525.ModifySmsSignRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySmsSignRequest;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

/**
 * 短信签名操作
 *
 * @author cliod
 * @since 8/7/20 4:10 PM
 */
public class SmsSignImpl implements SmsSign {

	private final IAcsClient client;
	private final String regionId;

	private SmsSignImpl(String regionId, IAcsClient client) {
		this.client = client;
		this.regionId = regionId;
	}

	protected SmsSignImpl(IClientProfile profile) {
		this(profile.getRegionId(), new DefaultAcsClient(profile));
	}

	protected SmsSignImpl(String regionId, String accessKeyId, String accessSecret) {
		this(regionId, new DefaultAcsClient(DefaultProfile.getProfile(regionId, accessKeyId, accessSecret)));
	}

	public static SmsSignImpl getInstance(String regionId, String accessKeyId, String accessSecret) {
		return new SmsSignImpl(regionId, accessKeyId, accessSecret);
	}

	public static SmsSignImpl getInstance(IClientProfile profile) {
		return new SmsSignImpl(profile);
	}

	@Override
	public AcsResponse add(String signName, Integer signSource, String remark) throws ClientException {
		AddSmsSignRequest request = new AddSmsSignRequest();
		request.setSignName(signName);
		request.setSignSource(signSource);
		request.setRemark(remark);
		request.setSysRegionId(this.regionId);
		return this.client.getAcsResponse(request);
	}

	@Override
	public AcsResponse delete(String signName) throws ClientException {
		DeleteSmsSignRequest request = new DeleteSmsSignRequest();
		request.setSignName(signName);
		request.setSysRegionId(this.regionId);
		return this.client.getAcsResponse(request);
	}

	@Override
	public AcsResponse modify(String signName, Integer signSource, String remark) throws ClientException {
		ModifySmsSignRequest request = new ModifySmsSignRequest();
		request.setSignName(signName);
		request.setSignSource(signSource);
		request.setRemark(remark);
		request.setSysRegionId(this.regionId);
		return this.client.getAcsResponse(request);
	}

	@Override
	public AcsResponse query(String signName) throws ClientException {
		QuerySmsSignRequest request = new QuerySmsSignRequest();
		request.setSignName(signName);
		request.setSysRegionId(this.regionId);
		return this.client.getAcsResponse(request);
	}
}
