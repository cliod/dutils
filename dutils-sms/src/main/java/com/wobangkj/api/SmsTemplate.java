package com.wobangkj.api;

/**
 * 短信模板操作
 *
 * @author cliod
 * @since 8/7/20 3:03 PM
 */
public interface SmsTemplate {

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
	 * @throws Exception 发送异常
	 */
	Object add(Integer type, String name, String content, String remark) throws Exception;

	/**
	 * 删除短息模板
	 *
	 * @param templateCode 短信模板CODE.
	 * @return 结果
	 * @throws Exception 发送异常
	 */
	Object delete(String templateCode) throws Exception;

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
	 * @throws Exception 发送异常
	 */
	Object modify(String templateCode, Integer type, String name, String content, String remark) throws Exception;

	/**
	 * 查询短息模板信息和状态
	 *
	 * @param templateCode 短信模板CODE.
	 * @return 结果
	 * @throws Exception 发送异常
	 */
	Object query(String templateCode) throws Exception;
}
