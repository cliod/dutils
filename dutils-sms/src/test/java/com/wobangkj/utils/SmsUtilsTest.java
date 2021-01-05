package com.wobangkj.utils;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author cliod
 * @since 1/5/21 11:11 AM
 */
public class SmsUtilsTest {

	@Test
	public void send() throws ClientException {
		Map<String, String> map = new HashMap<>();
		map.put("code", "123456");
		SendSmsResponse resp = (SendSmsResponse) SmsUtils.send(DefaultProfile.getProfile("ch-hangzhou", "", ""),
				"", "", JsonUtils.toJson(map), "");
		System.out.println(JsonUtils.toJson(resp));
	}
}