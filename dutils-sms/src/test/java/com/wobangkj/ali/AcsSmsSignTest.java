package com.wobangkj.ali;

import com.aliyuncs.dysmsapi.model.v20170525.ModifySmsSignResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试
 *
 * @author cliod
 * @version 1.0
 * @since 2021-04-22 13:20:56
 */
public class AcsSmsSignTest {
	@Test
	public void modify() throws ClientException {
		AcsSmsSign smsSign = new AcsSmsSignImpl(DefaultProfile.getProfile("ch-hangzhou", "", ""));
		List<AcsSmsSign.SignFile> signFiles = new ArrayList<>();
		ModifySmsSignResponse obj = (ModifySmsSignResponse) smsSign.modify("", 0, "", signFiles);
		System.out.println(obj.getMessage());
	}
}
