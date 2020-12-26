package com.wobangkj.tencent;

import com.tencentcloudapi.common.AbstractModel;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.AddSmsTemplateResponse;
import com.tencentcloudapi.sms.v20190711.models.DeleteSmsTemplateResponse;
import com.tencentcloudapi.sms.v20190711.models.DescribeSmsTemplateListResponse;
import com.tencentcloudapi.sms.v20190711.models.ModifySmsTemplateResponse;
import com.wobangkj.ali.AcsSmsImpl;
import com.wobangkj.api.SmsTemplate;

/**
 * 短信模板操作
 *
 * @author cliod
 * @since 8/7/20 3:03 PM
 */
public interface TcsSmsTemplate extends SmsTemplate {

	/**
	 * 获取默认对象(初始化对象)
	 *
	 * @param regionId  区域id
	 * @param secretId  访问密钥
	 * @param secretKey 访问密钥
	 * @return Sms对象
	 * @see AcsSmsImpl 默认实现
	 */
	static TcsSmsTemplate getInstance(String regionId, String secretId, String secretKey) {
		return TcsSmsTemplateImpl.getInstance(regionId, secretId, secretKey);
	}

	/**
	 * 获取默认对象(初始化对象)
	 *
	 * @param client 访问参数
	 * @return Sms对象
	 * @see AcsSmsImpl 默认实现
	 */
	static TcsSmsTemplate getInstance(SmsClient client) {
		return TcsSmsTemplateImpl.getInstance(client);
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
	 * @throws TencentCloudSDKException 发送异常
	 */
	@Override
	default AddSmsTemplateResponse add(Integer type, String name, String content, String remark) throws TencentCloudSDKException {
		return this.add(type, 0L, name, content, remark);
	}

	/**
	 * 添加短信模板
	 *
	 * @param type          短信类型. 其中：
	 *                      <p>
	 *                      0：验证码.
	 *                      1：短信通知.
	 *                      2：推广短信.
	 *                      3：国际/港澳台消息.
	 *                      </p>
	 * @param international 是否国际/港澳台短信：
	 *                      0：表示国内短信。
	 *                      1：表示国际/港澳台短信。
	 * @param name          模板名称，长度为1~30个字符.
	 * @param content       模板内容，长度为1~500个字符.
	 * @param remark        短信模板申请说明。请在申请说明中描述您的业务使用场景，长度为1~100个字符
	 * @return 结果
	 * @throws TencentCloudSDKException 发送异常
	 */
	AddSmsTemplateResponse add(Integer type, Long international, String name, String content, String remark) throws TencentCloudSDKException;

	/**
	 * 删除短息模板
	 *
	 * @param templateCode 短信模板CODE.
	 * @return 结果
	 * @throws TencentCloudSDKException 发送异常
	 */
	@Override
	default DeleteSmsTemplateResponse delete(String templateCode) throws TencentCloudSDKException {
		return this.delete(Long.parseLong(templateCode));
	}

	/**
	 * 删除短息模板
	 *
	 * @param templateId 短信模板ID.
	 * @return 结果
	 * @throws TencentCloudSDKException 发送异常
	 */
	DeleteSmsTemplateResponse delete(Long templateId) throws TencentCloudSDKException;

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
	 * @throws TencentCloudSDKException 发送异常
	 */
	@Override
	default ModifySmsTemplateResponse modify(String templateCode, Integer type, String name, String content, String remark) throws TencentCloudSDKException {
		return this.modify(Long.parseLong(templateCode), type, 0L, name, content, remark);
	}

	/**
	 * 修改短信模板
	 *
	 * @param templateId    短信模板ID.
	 * @param type          短信类型. 其中：
	 *                      <p>
	 *                      0：验证码.
	 *                      1：短信通知.
	 *                      2：推广短信.
	 *                      3：国际/港澳台消息.
	 *                      </p>
	 * @param international 是否国际/港澳台短信：
	 *                      0：表示国内短信。
	 *                      1：表示国际/港澳台短信。
	 * @param name          模板名称，长度为1~30个字符.
	 * @param content       模板内容，长度为1~500个字符.
	 * @param remark        短信模板申请说明。请在申请说明中描述您的业务使用场景，长度为1~100个字符
	 * @return 结果
	 * @throws TencentCloudSDKException 发送异常
	 */
	ModifySmsTemplateResponse modify(Long templateId, Integer type, Long international, String name, String content, String remark) throws TencentCloudSDKException;

	/**
	 * 查询短息模板信息和状态
	 *
	 * @param templateCode 短信模板CODE.
	 * @return 结果
	 * @throws TencentCloudSDKException 发送异常
	 */
	@Override
	default AbstractModel query(String templateCode) throws TencentCloudSDKException {
		return this.query(new Long[]{Long.parseLong(templateCode)}, 0L);
	}

	/**
	 * 查询短息模板信息和状态
	 *
	 * @param templateIds   短信模板ID.
	 * @param international 是否国际/港澳台短信：
	 *                      0：表示国内短信。
	 *                      1：表示国际/港澳台短信。
	 * @return 结果
	 * @throws TencentCloudSDKException 发送异常
	 */
	DescribeSmsTemplateListResponse query(Long[] templateIds, Long international) throws TencentCloudSDKException;
}
