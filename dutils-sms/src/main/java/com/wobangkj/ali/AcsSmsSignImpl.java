package com.wobangkj.ali;

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

import java.util.ArrayList;
import java.util.List;

/**
 * 短信签名操作
 *
 * @author cliod
 * @since 8/7/20 4:10 PM
 */
public class AcsSmsSignImpl implements AcsSmsSign {

	private final IAcsClient client;
	private final String regionId;

	private AcsSmsSignImpl(String regionId, IAcsClient client) {
		this.client = client;
		this.regionId = regionId;
	}

	protected AcsSmsSignImpl(IClientProfile profile) {
		this(profile.getRegionId(), new DefaultAcsClient(profile));
	}

	protected AcsSmsSignImpl(String regionId, String accessKeyId, String accessSecret) {
		this(regionId, new DefaultAcsClient(DefaultProfile.getProfile(regionId, accessKeyId, accessSecret)));
	}

	public static AcsSmsSignImpl getInstance(String regionId, String accessKeyId, String accessSecret) {
		return new AcsSmsSignImpl(regionId, accessKeyId, accessSecret);
	}

	public static AcsSmsSignImpl getInstance(IClientProfile profile) {
		return new AcsSmsSignImpl(profile);
	}

	@Override
	public AcsResponse add(String signName, Integer signSource, String remark, List<SignFile> signFiles) throws ClientException {
		AddSmsSignRequest request = new AddSmsSignRequest();
		request.setSignName(signName);
		request.setSignSource(signSource);
		request.setRemark(remark);
		request.setSysRegionId(this.regionId);
		List<AddSmsSignRequest.SignFileList> signFileLists = new ArrayList<>();
		for (SignFile signFile : signFiles) {
			signFileLists.add(new AddSmsSignRequest.SignFileList() {{
				setFileContents(signFile.getFileContents());
				setFileSuffix(signFile.getFileSuffix());
			}});
		}
		request.setSignFileLists(signFileLists);
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
	public AcsResponse modify(String signName, Integer signSource, String remark, List<SignFile> signFiles) throws ClientException {
		ModifySmsSignRequest request = new ModifySmsSignRequest();
		request.setSignName(signName);
		request.setSignSource(signSource);
		request.setRemark(remark);
		request.setSysRegionId(this.regionId);
		List<ModifySmsSignRequest.SignFileList> signFileLists = new ArrayList<>();
		for (SignFile signFile : signFiles) {
			signFileLists.add(new ModifySmsSignRequest.SignFileList() {{
				setFileContents(signFile.getFileContents());
				setFileSuffix(signFile.getFileSuffix());
			}});
		}
		request.setSignFileLists(signFileLists);
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
