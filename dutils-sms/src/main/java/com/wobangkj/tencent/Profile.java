package com.wobangkj.tencent;

import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;

/**
 * 配置
 *
 * @author cliod
 * @since 10/15/20 2:34 PM
 */
public class Profile {

	public static ClientProfile getDefaultProfile() {
		// 实例化一个 http 选项，可选，无特殊需求时可以跳过
		HttpProfile httpProfile = new HttpProfile();
		/* SDK 默认使用 POST 方法
		 * 如需使用 GET 方法，可以在此处设置，但 GET 方法无法处理较大的请求 */
		httpProfile.setReqMethod("POST");
		/* SDK 有默认的超时时间，非必要请不要进行调整
		 * 如有需要请在代码中查阅以获取最新的默认值 */
		httpProfile.setConnTimeout(60);
		/* SDK 会自动指定域名，通常无需指定域名，但访问金融区的服务时必须手动指定域名
		 * 例如 SMS 的上海金融区域名为 sms.ap-shanghai-fsi.tencentcloudapi.com      */
		httpProfile.setEndpoint("sms.tencentcloudapi.com");

		/* 非必要步骤:
		 * 实例化一个客户端配置对象，可以指定超时时间等配置 */
		ClientProfile clientProfile = new ClientProfile();
		/* SDK 默认使用 TC3-HMAC-SHA256 进行签名
		 * 非必要请不要修改该字段 */
		clientProfile.setSignMethod("HmacSHA256");
		clientProfile.setHttpProfile(httpProfile);
		return clientProfile;
	}
}
