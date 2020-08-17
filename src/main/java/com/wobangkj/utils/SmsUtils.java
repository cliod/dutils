package com.wobangkj.utils;

import com.aliyuncs.AcsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.wobangkj.api.sms.DefaultSms;
import com.wobangkj.api.sms.Sms;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.Objects;

/**
 * 短信发送工具类
 *
 * @author cliod
 * @since 2020/08/02
 */
@Deprecated
public class SmsUtils {

    private static DefaultSms.SmsParam smsParam;
    private static Sms sms;

    private SmsUtils() {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public static DefaultSms.SmsParam getSmsParam() {
        return smsParam;
    }

    @Deprecated
    public static void setSmsParam(DefaultSms.SmsParam smsParam) {
        SmsUtils.smsParam = smsParam;
    }

    /**
     * 根据手机号和模板 发送无参数短信
     *
     * @param phoneNumber  手机号
     * @param templateCode 模板
     * @return 响应
     * @throws ClientException 客户端异常
     */
    @Deprecated
    public static AcsResponse send(final String templateCode, String... phoneNumber) throws ClientException {
        return send(smsParam, "", templateCode, phoneNumber);
    }

    /**
     * 根据手机号和模板 发送指定参数的短信
     *
     * @param phoneNumber  手机号
     * @param templateCode 模板
     * @return 响应
     * @throws ClientException 客户端异常
     */
    @Deprecated
    public static AcsResponse send(final String templateCode, Map<String, Object> params, String... phoneNumber) throws ClientException {
        final String templateParam = JsonUtils.toJson(params);
        return send(smsParam, templateCode, templateParam, phoneNumber);
    }

    @Deprecated
    public static AcsResponse send(String templateCode, String templateParamJson, String... phoneNumber) throws ClientException {
        return send(smsParam, templateCode, templateParamJson, phoneNumber);
    }

    /**
     * 根据手机号和模板 发送无参数短信
     *
     * @param phoneNumber  手机号
     * @param templateCode 模板
     * @return 响应
     * @throws ClientException 客户端异常
     */
    @Deprecated
    public static AcsResponse send(DefaultSms.SmsParam param, final String templateCode, String... phoneNumber) throws ClientException {
        return send(param, "", templateCode, phoneNumber);
    }

    /**
     * 根据手机号和模板 发送指定参数的短信
     *
     * @param phoneNumber  手机号
     * @param param        参数
     * @param templateCode 模板
     * @return 响应
     * @throws ClientException 客户端异常
     */
    @Deprecated
    public static AcsResponse send(DefaultSms.SmsParam param, final String templateCode, Map<String, Object> params, String... phoneNumber) throws ClientException {
        final String templateParam = JsonUtils.toJson(params);
        return send(param, templateCode, templateParam, phoneNumber);
    }

    @Deprecated
    public static AcsResponse send(DefaultSms.SmsParam param, String templateCode, String templateParamJson, String... phoneNumber) throws ClientException {
        if (check(param)) {
            param = smsParam;
            if (check(param)) {
                if (Objects.isNull(sms)) {
                    sms = DefaultSms.getInstance(param);
                }
                return sms.send(templateCode, templateParamJson, phoneNumber);
            }
        }
        throw new ClientException("发送失败: 参数不存在");
    }

    private static boolean check(DefaultSms.SmsParam param) {
        if (Objects.isNull(param)) {
            return false;
        }
        return !(StringUtils.isEmpty(param.getAccessKeyId()) ||
                StringUtils.isEmpty(param.getAccessSecret()) ||
                StringUtils.isEmpty(param.getRegionId()) ||
                StringUtils.isEmpty(param.getSignName()));
    }
}
