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

	/**
	 * 获取默认对象(初始化对象)
	 *
	 * @param regionId    区域id
	 * @param accessKeyId 访问密钥
	 * @param secret      访问密钥
	 * @return Sms对象
	 * @see SmsImpl 默认实现
	 */
	static SmsTemplate getInstance(String regionId, String accessKeyId, String secret) {
		return SmsTemplateImpl.getInstance(regionId, accessKeyId, secret);
	}

	/**
	 * 获取默认对象(初始化对象)
	 *
	 * @param profile 访问参数
	 * @return Sms对象
	 * @see SmsImpl 默认实现
	 */
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
	 * @throws ClientException 发送异常
	 */
	AcsResponse add(Integer type, String name, String content, String remark) throws ClientException;

	/**
	 * 删除短息模板
	 *
	 * @param templateCode 短信模板CODE.
	 * @return 结果
	 * @throws ClientException 发送异常
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
	 * @throws ClientException 发送异常
	 */
	AcsResponse modify(String templateCode, Integer type, String name, String content, String remark) throws ClientException;

	/**
	 * 查询短息模板信息和状态
	 *
	 * @param templateCode 短信模板CODE.
	 * @return 结果
	 * @throws ClientException 发送异常
	 */
	AcsResponse query(String templateCode) throws ClientException;
}
