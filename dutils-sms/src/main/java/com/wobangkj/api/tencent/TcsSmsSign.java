package com.wobangkj.api.tencent;

import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.AddSmsSignResponse;
import com.tencentcloudapi.sms.v20190711.models.DeleteSmsSignResponse;
import com.tencentcloudapi.sms.v20190711.models.DescribeSmsSignListResponse;
import com.tencentcloudapi.sms.v20190711.models.ModifySmsSignResponse;
import com.wobangkj.api.SmsSign;
import com.wobangkj.api.ali.AcsSmsImpl;

/**
 * 短信签名
 *
 * @author cliod
 * @since 8/7/20 3:00 PM
 */
public interface TcsSmsSign extends SmsSign {

	/**
	 * 获取默认对象(初始化对象)
	 *
	 * @param regionId  区域id
	 * @param secretId  访问密钥
	 * @param secretKey 访问密钥
	 * @return Sms对象
	 * @see com.wobangkj.api.ali.AcsSmsImpl 默认实现
	 */
	static TcsSmsSign getInstance(String regionId, String secretId, String secretKey) {
		return TcsSmsSignImpl.getInstance(regionId, secretId, secretKey);
	}

	/**
	 * 获取默认对象(初始化对象)
	 *
	 * @param client 访问参数
	 * @return Sms对象
	 * @see AcsSmsImpl 默认实现
	 */
	static TcsSmsSign getInstance(SmsClient client) {
		return TcsSmsSignImpl.getInstance(client);
	}

	/**
	 * 添加短信签名
	 *
	 * @param signName 签名名称
	 * @param signType 签名来源。其中：
	 *                 <p>
	 *                 0：企事业单位的全称或简称
	 *                 1：工信部备案网站的全称或简称
	 *                 2：APP应用的全称或简称
	 *                 3：公众号或小程序的全称或简称
	 *                 4：电商平台店铺名的全称或简称
	 *                 5：商标名的全称或简称
	 *                 </p>
	 * @param remark   短信签名申请说明。请在申请说明中详细描述您的业务使用场景，申请工信部备案网站的全称或简称请在此处填写域名，长度不超过200个字符
	 * @return 结果
	 * @throws TencentCloudSDKException 发送异常
	 */
	@Override
	AddSmsSignResponse add(String signName, Integer signType, String remark) throws TencentCloudSDKException;

	/**
	 * 添加短信签名
	 *
	 * @param signId 签名名称
	 * @return 结果
	 * @throws TencentCloudSDKException 发送异常
	 */
	@Override
	DeleteSmsSignResponse delete(String signId) throws TencentCloudSDKException;

	/**
	 * 添加短信签名
	 *
	 * @param signName 签名名称
	 * @param signType 签名来源。其中：
	 *                 <p>
	 *                 0：企事业单位的全称或简称
	 *                 1：工信部备案网站的全称或简称
	 *                 2：APP应用的全称或简称
	 *                 3：公众号或小程序的全称或简称
	 *                 4：电商平台店铺名的全称或简称
	 *                 5：商标名的全称或简称
	 *                 </p>
	 * @param remark   短信签名申请说明。请在申请说明中详细描述您的业务使用场景，申请工信部备案网站的全称或简称请在此处填写域名，长度不超过200个字符
	 * @return 结果
	 * @throws TencentCloudSDKException 发送异常
	 */
	@Override
	ModifySmsSignResponse modify(String signName, Integer signType, String remark) throws TencentCloudSDKException;

	/**
	 * 添加短信签名
	 *
	 * @param signName 签名名称
	 * @return 结果
	 * @throws TencentCloudSDKException 发送异常
	 */
	@Override
	DescribeSmsSignListResponse query(String signName) throws TencentCloudSDKException;
}
