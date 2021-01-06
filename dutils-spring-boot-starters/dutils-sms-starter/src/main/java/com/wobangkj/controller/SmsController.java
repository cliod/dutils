package com.wobangkj.controller;

import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.wobangkj.ali.AcsSms;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

import static com.wobangkj.api.Response.*;

/**
 * ali短信API
 *
 * @author cliod
 * @since 10/20/20 10:22 AM
 */
@Slf4j
@RestController("sms")
@ConditionalOnClass(value = DefaultAcsClient.class)
public class SmsController {

	@Setter
	private AcsSms sms;

	public SmsController(@Qualifier("acsSms") AcsSms sms) {
		this.sms = sms;
	}

	/**
	 * 发送短信
	 *
	 * @return 短信
	 */
	@PostMapping("/sms/send")
	public Object send(@RequestParam String mobile, @RequestParam String template,
	                   @RequestParam String param, @RequestParam String signName) {
		try {
			CommonResponse response = this.sms.commonSend(template, param, signName, mobile);
			if (Objects.equals(response.getHttpStatus(), SUCCESS_CODE)) {
				return ok();
			} else {
				log.warn(response.getData());
				return fail("短信发送失败");
			}
		} catch (ClientException e) {
			log.error(e.getMessage());
			return fail("短信发送失败", e);
		}
	}
}
