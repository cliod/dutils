package com.wobangkj.api.sms;

import com.aliyuncs.AcsResponse;

/**
 * 短信签名
 *
 * @author cliod
 * @since 8/7/20 3:00 PM
 */
public interface SmsSign {

	AcsResponse add();

	AcsResponse delete();

	AcsResponse modify();

	AcsResponse query();
}
