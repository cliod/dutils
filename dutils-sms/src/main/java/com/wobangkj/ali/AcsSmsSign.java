package com.wobangkj.ali;

import com.aliyuncs.AcsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.IClientProfile;
import com.wobangkj.api.SmsSign;
import com.wobangkj.utils.JsonUtils;
import lombok.Data;

import java.util.List;

/**
 * 短信签名
 *
 * @author cliod
 * @since 8/7/20 3:00 PM
 */
public interface AcsSmsSign extends SmsSign {

	/**
	 * 获取默认对象(初始化对象)
	 *
	 * @param regionId    区域id
	 * @param accessKeyId 访问密钥
	 * @param secret      访问密钥
	 * @return Sms对象
	 * @see AcsSmsImpl 默认实现
	 */
	static AcsSmsSign getInstance(String regionId, String accessKeyId, String secret) {
		return AcsSmsSignImpl.getInstance(regionId, accessKeyId, secret);
	}

	/**
	 * 获取默认对象(初始化对象)
	 *
	 * @param profile 访问参数
	 * @return Sms对象
	 * @see AcsSmsImpl 默认实现
	 */
	static AcsSmsSign getInstance(IClientProfile profile) {
		return AcsSmsSignImpl.getInstance(profile);
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
	 * @param image      证明
	 * @return 结果
	 * @throws ClientException 发送异常
	 */
	@Override
	default AcsResponse add(String signName, Integer signSource, String remark, String image) throws ClientException {
		return this.add(signName, signSource, remark, JsonUtils.toList(image));
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
	 * @param signFiles  证明
	 * @return 结果
	 * @throws ClientException 发送异常
	 */
	AcsResponse add(String signName, Integer signSource, String remark, List<SignFile> signFiles) throws ClientException;

	/**
	 * 添加短信签名
	 *
	 * @param signName 签名名称
	 * @return 结果
	 * @throws ClientException 发送异常
	 */
	@Override
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
	 * @param image      证明
	 * @return 结果
	 * @throws ClientException 发送异常
	 */
	@Override
	default AcsResponse modify(String signName, Integer signSource, String remark, String image) throws ClientException {

		return this.modify(signName, signSource, remark, JsonUtils.toList(image));
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
	 * @param signFiles  证明
	 * @return 结果
	 * @throws ClientException 发送异常
	 */
	AcsResponse modify(String signName, Integer signSource, String remark, List<SignFile> signFiles) throws ClientException;

	/**
	 * 添加短信签名
	 *
	 * @param signName 签名名称
	 * @return 结果
	 * @throws ClientException 发送异常
	 */
	@Override
	AcsResponse query(String signName) throws ClientException;

	/**
	 * 签名证明
	 */
	@Data
	class SignFile {
		/**
		 * 文件Base64编码
		 */
		private String fileContents;
		/**
		 * 文件后缀名
		 */
		private String fileSuffix;
	}
}
