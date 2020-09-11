package com.wobangkj.api;

import com.aliyuncs.AcsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.IClientProfile;

/**
 * 短信签名
 *
 * @author cliod
 * @since 8/7/20 3:00 PM
 */
public interface SmsSign {

	static SmsSign getInstance(String regionId, String accessKeyId, String accessSecret) {
		return SmsSignImpl.getInstance(regionId, accessKeyId, accessSecret);
	}

	static SmsSign getInstance(IClientProfile profile) {
		return SmsSignImpl.getInstance(profile);
	}

	/**
	 * 添加短信签名
	 *
	 * @param signName   签名名称
	 * @param signSource 签名来源。其中：
	 *                   <p>
	 *                   0：企事业单位的全称或简称
	 *                   1：工信部备案网站的全称或简称
	 *                   2：APP应用的全称或简称
	 *                   3：公众号或小程序的全称或简称
	 *                   4：电商平台店铺名的全称或简称
	 *                   5：商标名的全称或简称
	 *                   </p>
	 * @param remark     短信签名申请说明。请在申请说明中详细描述您的业务使用场景，申请工信部备案网站的全称或简称请在此处填写域名，长度不超过200个字符
	 * @return 结果
	 */
	AcsResponse add(String signName, Integer signSource, String remark) throws ClientException;

	/**
	 * 添加短信签名
	 *
	 * @param signName 签名名称
	 * @return 结果
	 */
	AcsResponse delete(String signName) throws ClientException;

	/**
	 * 添加短信签名
	 *
	 * @param signName   签名名称
	 * @param signSource 签名来源。其中：
	 *                   <p>
	 *                   0：企事业单位的全称或简称
	 *                   1：工信部备案网站的全称或简称
	 *                   2：APP应用的全称或简称
	 *                   3：公众号或小程序的全称或简称
	 *                   4：电商平台店铺名的全称或简称
	 *                   5：商标名的全称或简称
	 *                   </p>
	 * @param remark     短信签名申请说明。请在申请说明中详细描述您的业务使用场景，申请工信部备案网站的全称或简称请在此处填写域名，长度不超过200个字符
	 * @return 结果
	 */
	AcsResponse modify(String signName, Integer signSource, String remark) throws ClientException;

	/**
	 * 添加短信签名
	 *
	 * @param signName 签名名称
	 * @return 结果
	 */
	AcsResponse query(String signName) throws ClientException;
}
