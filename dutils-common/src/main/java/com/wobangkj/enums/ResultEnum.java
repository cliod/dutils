package com.wobangkj.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wobangkj.api.EnumMsg;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

/**
 * return msg
 *
 * @author cliod
 * @since 19-6-9
 */
@Deprecated
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ResultEnum implements EnumMsg {
	/**
	 * HTTP 状态码
	 * #1000～1999 区间表示参数错误
	 * #2000～2999 区间表示用户错误
	 * #3000～3999 区间表示接口异常
	 */
	SUCCESS(200, "请求成功"),
	SUCCESS_ADD(201, "创建成功"),
	SUCCESS_EDIT(206, "修改成功"),
	SUCCESS_DELETE(209, "删除成功"),
	SUCCESS_NULL(204, "暂无资源"),
	ILLEGAL(203, "非法请求"),

	NOT_AUTH(212, "非法操作，未登录授权"),
	AUTH_FAIL(214, "授权失败"),
	AUTH_NULL(213, "用户无权参与"),

	FIND_FAIL(221, "查找失败"),
	DELETE_FAIL(222, "删除失败"),
	ADD_FAIL(223, "添加失败"),
	EDIT_FAIL(224, "修改失败"),
	OPERATE_FAIL(215, "操作失败"),

	BAD_REQUEST(400, "请求失败"),
	FORBIDDEN(403, "禁止访问"),
	NOT_FOUND(404, "无法找到请求"),
	UPLOAD_FAIL(444, "上传失败"),
	LACK_PARAM(450, "缺少参数"),
	BLANK_PARAM(451, "参数为空"),

	ERROR(500, "未知异常"),

	USER_TEXT(271, ""),
	;

	/**
	 * 状态码
	 */
	@Getter
	private final Integer code;
	/**
	 * 消息
	 */
	@Getter
	private final String msg;

	ResultEnum(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	@Override
	public @NotNull String toString() {
		return this.toJson();
	}

}