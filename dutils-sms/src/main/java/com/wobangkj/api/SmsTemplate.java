package com.wobangkj.api;

import com.aliyuncs.AcsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.IClientProfile;

/**
 * 短信模板操作
 *
 * @author cliod
 * @since 8/7/20 3:03 PM
 */
public interface SmsTemplate {

	static SmsTemplate getInstance(String regionId, String accessKeyId, String accessSecret) {
		return SmsTemplateImpl.getInstance(regionId, accessKeyId, accessSecret);
	}

	static SmsTemplate getInstance(IClientProfile profile) {
		return SmsTemplateImpl.getInstance(profile);
	}

	/**
	 * 添加短信模板
	 *
	 * @param type    短信类型. 其中：
	 *                <p>
	 *                0：验证码.
	 *                1：短信通知.
	 *                2：推广短信.
	 *                3：国际/港澳台消息.
	 *                </p>
	 * @param name    模板名称，长度为1~30个字符.
	 * @param content 模板内容，长度为1~500个字符.
	 * @param remark  短信模板申请说明。请在申请说明中描述您的业务使用场景，长度为1~100个字符
	 * @return 结果
	 */
	AcsResponse add(Integer type, String name, String content, String remark) throws ClientException;

	/**
	 * 删除短息模板
	 *
	 * @param templateCode 短信模板CODE.
	 * @return 结果
	 */
	AcsResponse delete(String templateCode) throws ClientException;

	/**
	 * 修改短信模板
	 *
	 * @param templateCode 短信模板CODE.
	 * @param type         短信类型. 其中：
	 *                     <p>
	 *                     0：验证码.
	 *                     1：短信通知.
	 *                     2：推广短信.
	 *                     3：国际/港澳台消息.
	 *                     </p>
	 * @param name         模板名称，长度为1~30个字符.
	 * @param content      模板内容，长度为1~500个字符.
	 * @param remark       短信模板申请说明。请在申请说明中描述您的业务使用场景，长度为1~100个字符
	 * @return 结果
	 */
	AcsResponse modify(String templateCode, Integer type, String name, String content, String remark) throws ClientException;

	/**
	 * 查询短息模板信息和状态
	 *
	 * @param templateCode 短信模板CODE.
	 * @return 结果
	 */
	AcsResponse query(String templateCode) throws ClientException;
}
