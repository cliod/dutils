package com.wobangkj.api.sms;

import com.aliyuncs.AcsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.wobangkj.bean.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * 发送短信
 *
 * @author cliod
 * @since 8/7/20 4:10 PM
 */
public class SmsImpl implements Sms {
	@Override
	public String getSignName() {
		return null;
	}

	@Override
	public void setSignName(String signName) {

	}

	@Override
	public AcsResponse send(String template, String params, String signName, String phoneNumbers) throws ClientException {
		return null;
	}

	@Override
	public AcsResponse batchSend(String template, List<Map<String, Object>> params, String signNames, String phoneNumbers) throws ClientException {
		return null;
	}

	@Override
	public AcsResponse querySendDetails(String phoneNumber, LocalDate date, String bizId, Pageable pageable) throws ClientException {
		return null;
	}

	@Override
	public SmsSign getSmsSign() {
		return null;
	}

	@Override
	public SmsSign getSmsTemplate() {
		return null;
	}
}
