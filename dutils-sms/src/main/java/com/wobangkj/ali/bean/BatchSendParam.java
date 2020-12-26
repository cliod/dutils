package com.wobangkj.ali.bean;

import lombok.Data;

/**
 * 批量发送短信参数
 *
 * @author cliod
 * @since 10/16/20 10:56 AM
 */
@Data
public class BatchSendParam {

	private String signName;
	private String templateParamJson;
	private String phoneNumber;
}
