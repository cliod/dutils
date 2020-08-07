package com.wobangkj.api.sms;

import com.aliyuncs.AcsResponse;

/**
 * 短信模板操作
 *
 * @author cliod
 * @since 8/7/20 3:03 PM
 */
public interface SmsTemplate {
	AcsResponse add();

	AcsResponse delete();

	AcsResponse modify();

	AcsResponse query();
}
