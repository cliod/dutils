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
public class TcsSmsSignImpl implements TcsSmsSign {

	private final SmsClient client;

	private TcsSmsSignImpl(SmsClient client) {
		this.client = client;
	}

	protected TcsSmsSignImpl(Credential cred, String region) {
		this(new SmsClient(cred, region, Profile.getDefaultProfile()));
	}

	protected TcsSmsSignImpl(String region, String secretId, String secretKey) {
		this(new Credential(secretId, secretKey), region);
	}

	public static TcsSmsSignImpl getInstance(String regionId, String secretId, String secretKey) {
		return new TcsSmsSignImpl(regionId, secretId, secretKey);
	}

	public static TcsSmsSignImpl getInstance(SmsClient client) {
		return new TcsSmsSignImpl(client);
	}

	@Override
	public AddSmsSignResponse add(String signName, Integer signType, Long documentType, Long international, Long usedMethod, String remark, String image) throws TencentCloudSDKException {
		AddSmsSignRequest request = new AddSmsSignRequest();
		request.setSignName(signName);
		request.setSignType(signType.longValue());
		request.setDocumentType(documentType);
		request.setInternational(international);
		request.setUsedMethod(usedMethod);
		request.setProofImage(image);
		request.setCommissionImage("");
		request.setRemark(remark);
		return this.client.AddSmsSign(request);
	}

	@Override
	public DeleteSmsSignResponse delete(String signId) throws TencentCloudSDKException {
		DeleteSmsSignRequest request = new DeleteSmsSignRequest();
		request.setSignId(Long.parseLong(signId));
		return this.client.DeleteSmsSign(request);
	}

	@Override
	public ModifySmsSignResponse modify(Long id, String signName, Integer signType, Long documentType, Long international, Long usedMethod, String remark, String image) throws TencentCloudSDKException {
		ModifySmsSignRequest request = new ModifySmsSignRequest();
		request.setSignId(id);
		request.setSignName(signName);
		request.setSignType(signType.longValue());
		request.setDocumentType(documentType);
		request.setInternational(international);
		request.setUsedMethod(usedMethod);
		request.setProofImage(image);
		request.setCommissionImage("");
		request.setRemark(remark);
		return this.client.ModifySmsSign(request);
	}

	@Override
	public DescribeSmsSignListResponse query(String signName) throws TencentCloudSDKException {
		DescribeSmsSignListRequest request = new DescribeSmsSignListRequest();
		request.setSignIdSet(new Long[0]);
		request.setInternational(0L);
		return this.client.DescribeSmsSignList(request);
	}
}
