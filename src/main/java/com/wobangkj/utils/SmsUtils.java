package com.wobangkj.utils;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 短信发送工具类
 *
 * @author cliod
 * @since 2020/08/02
 */
public class SmsUtils {
    private static SmsParam smsParam;

    static {
        //设置超时时间-可自行调整
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
    }

    private SmsUtils() {
        throw new UnsupportedOperationException();
    }

    /**
     * 根据手机号和模板 发送无参数短信
     *
     * @param phoneNumber  手机号
     * @param templateCode 模板
     * @return 响应
     * @throws ClientException 客户端异常
     */
    public static CommonResponse send(String phoneNumber, final String templateCode) throws ClientException {
        return send(smsParam, phoneNumber, templateCode, "");
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
    public static CommonResponse send(String phoneNumber, final String templateCode, Map<String, Object> param) throws ClientException {
        final String templateParam = JsonUtils.toJson(param);
        return send(smsParam, phoneNumber, templateCode, templateParam);
    }

    private static CommonResponse send(SmsParam param, String phoneNumber, String templateCode, String templateParamJson) throws ClientException {
        final String accessKeyId = param.accessKeyId;
        final String accessSecret = param.accessSecret;
        final String signName = param.signName;
        final String regionId = param.regionId;

        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessSecret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", regionId);
        request.putQueryParameter("PhoneNumbers", phoneNumber);
        request.putQueryParameter("SignName", signName);
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParam", templateParamJson);
        return client.getCommonResponse(request);
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SmsParam {
        private String accessKeyId;
        private String accessSecret;
        private String signName;
        private String regionId;
    }
}
