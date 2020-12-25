package com.wobangkj.api;

/**
 * 可发送的信息
 *
 * @author cliod
 * @since 11/28/20 1:29 PM
 */
public interface Sender {

	/**
	 * 发送单条短信(可以多个手机号)
	 *
	 * @param template    短信模板ID
	 * @param params      短信模板变量对应的实际值，JSON格式
	 * @param signName    签名名称
	 * @param phoneNumber 接收短信的手机号码。
	 *                    <p>
	 *                    格式：
	 *                    国内短信：11位手机号码，例如15951955195。
	 *                    国际/港澳台消息：国际区号+号码，例如85200000000。
	 *                    </p>
	 * @return 结果
	 * @throws Exception 发送异常
	 */
	Object send(final String template, String params, String signName, String phoneNumber) throws Exception;

}
