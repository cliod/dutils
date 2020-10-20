package com.wobangkj.controller;

import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.wobangkj.ali.AcsSms;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

import static com.wobangkj.api.Response.SUCCESS_CODE;
import static com.wobangkj.api.Response.ok;

/**
 * ali短信API
 *
 * @author cliod
 * @since 10/20/20 10:22 AM
 */
@Slf4j
@RestController
@ConditionalOnClass(value = DefaultAcsClient.class)
public class SmsController {

	private final AcsSms sms;

	public SmsController(AcsSms sms) {
		this.sms = sms;
	}

	/**
	 * 发送短信
	 *
	 * @return 短信
	 */
	@PostMapping("/sms/send")
	public Object send(@RequestParam String mobile) {
		try {
			CommonResponse response = this.sms.commonSend("", "", "", mobile);
			if (Objects.equals(response.getHttpStatus(), SUCCESS_CODE)) {
				return ok();
			} else {
				log.error(response.getData());
			}
		} catch (ClientException e) {
			log.error(e.getMessage());
		}
		return ok();
	}

	@GetMapping("/verify")
	public Object verify(@RequestParam String mobile, @RequestParam String code, @RequestParam Long id) {
		//todo: 验证短信验证码和下一个处理(建议返回api_token)
		return ok();
	}
}
