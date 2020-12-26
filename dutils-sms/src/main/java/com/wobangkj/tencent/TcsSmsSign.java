package com.wobangkj.tencent;

import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.AddSmsSignResponse;
import com.tencentcloudapi.sms.v20190711.models.DeleteSmsSignResponse;
import com.tencentcloudapi.sms.v20190711.models.DescribeSmsSignListResponse;
import com.tencentcloudapi.sms.v20190711.models.ModifySmsSignResponse;
import com.wobangkj.ali.AcsSmsImpl;
import com.wobangkj.api.SmsSign;

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
	 * @see AcsSmsImpl 默认实现
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
	 * @param image    营业执照：签名对应的资质证明图片需先进行 base64 编码格式转换，将转换后的字符串去掉前缀data:image/jpeg;base64,再赋值给该参数
	 * @return 结果
	 * @throws TencentCloudSDKException 发送异常
	 */
	@Override
	default AddSmsSignResponse add(String signName, Integer signType, String remark, String image) throws TencentCloudSDKException {
		return this.add(signName, signType, 0L, 0L, 0L, remark, image);
	}

	/**
	 * 添加短信签名
	 *
	 * @param signName      签名名称
	 * @param signType      签名来源。其中：
	 *                      <p>
	 *                      0：企事业单位的全称或简称
	 *                      1：工信部备案网站的全称或简称
	 *                      2：APP应用的全称或简称
	 *                      3：公众号或小程序的全称或简称
	 *                      4：电商平台店铺名的全称或简称
	 *                      5：商标名的全称或简称
	 *                      </p>
	 * @param remark        短信签名申请说明。请在申请说明中详细描述您的业务使用场景，申请工信部备案网站的全称或简称请在此处填写域名，长度不超过200个字符
	 * @param image         营业执照：签名对应的资质证明图片需先进行 base64 编码格式转换，将转换后的字符串去掉前缀data:image/jpeg;base64,再赋值给该参数
	 * @param documentType  证明类型：
	 *                      0：三证合一。
	 *                      1：企业营业执照。
	 *                      2：组织机构代码证书。
	 *                      3：社会信用代码证书。
	 *                      4：应用后台管理截图(个人开发APP)。
	 *                      5：网站备案后台截图(个人开发网站)。
	 *                      6：小程序设置页面截图(个人认证小程序)。
	 *                      7：商标注册书。
	 * @param international 是否国际/港澳台短信：
	 *                      0：表示国内短信。
	 *                      1：表示国际/港澳台短信。
	 * @param usedMethod    签名用途：
	 *                      0：自用。
	 *                      1：他用。
	 * @return 结果
	 * @throws TencentCloudSDKException 发送异常
	 */
	AddSmsSignResponse add(String signName, Integer signType, Long documentType, Long international, Long usedMethod, String remark, String image) throws TencentCloudSDKException;

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
	 * @param image    营业执照：签名对应的资质证明图片需先进行 base64 编码格式转换，将转换后的字符串去掉前缀data:image/jpeg;base64,再赋值给该参数
	 * @return 结果
	 * @throws TencentCloudSDKException 发送异常
	 */
	@Override
	default ModifySmsSignResponse modify(String signName, Integer signType, String remark, String image) throws TencentCloudSDKException {
		throw new TencentCloudSDKException("无法修改，id为空");
	}

	/**
	 * 添加短信签名
	 *
	 * @param id       id
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
	 * @param image    营业执照：签名对应的资质证明图片需先进行 base64 编码格式转换，将转换后的字符串去掉前缀data:image/jpeg;base64,再赋值给该参数
	 * @return 结果
	 * @throws TencentCloudSDKException 发送异常
	 */
	default ModifySmsSignResponse modify(Long id, String signName, Integer signType, String remark, String image) throws TencentCloudSDKException {
		return this.modify(id, signName, signType, 0L, 0L, 0L, remark, image);
	}

	/**
	 * 添加短信签名
	 *
	 * @param id            id
	 * @param signName      签名名称
	 * @param signType      签名来源。其中：
	 *                      <p>
	 *                      0：企事业单位的全称或简称
	 *                      1：工信部备案网站的全称或简称
	 *                      2：APP应用的全称或简称
	 *                      3：公众号或小程序的全称或简称
	 *                      4：电商平台店铺名的全称或简称
	 *                      5：商标名的全称或简称
	 *                      </p>
	 * @param remark        短信签名申请说明。请在申请说明中详细描述您的业务使用场景，申请工信部备案网站的全称或简称请在此处填写域名，长度不超过200个字符
	 * @param image         营业执照：签名对应的资质证明图片需先进行 base64 编码格式转换，将转换后的字符串去掉前缀data:image/jpeg;base64,再赋值给该参数
	 * @param documentType  证明类型：
	 *                      0：三证合一。
	 *                      1：企业营业执照。
	 *                      2：组织机构代码证书。
	 *                      3：社会信用代码证书。
	 *                      4：应用后台管理截图(个人开发APP)。
	 *                      5：网站备案后台截图(个人开发网站)。
	 *                      6：小程序设置页面截图(个人认证小程序)。
	 *                      7：商标注册书。
	 * @param international 是否国际/港澳台短信：
	 *                      0：表示国内短信。
	 *                      1：表示国际/港澳台短信。
	 * @param usedMethod    签名用途：
	 *                      0：自用。
	 *                      1：他用。
	 * @return 结果
	 * @throws TencentCloudSDKException 发送异常
	 */
	ModifySmsSignResponse modify(Long id, String signName, Integer signType, Long documentType, Long international, Long usedMethod,
	                             String remark, String image) throws TencentCloudSDKException;

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
