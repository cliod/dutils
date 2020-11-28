package com.wobangkj.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wobangkj.api.EnumTextMsg;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

/**
 * return msg
 *
 * @author cliod
 * @since 19-6-9
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ResEnum implements EnumTextMsg {
	/**
	 * 返回给用户的提示
	 */
	SUCCESS_NULL("暂无资源"),
	ILLEGAL("非法请求"),

	NOT_AUTH("非法操作，未登录授权"),
	AUTH_FAIL("授权失败"),
	AUTH_NULL("用户无权参与"),

	FIND_FAIL("查找失败"),
	DELETE_FAIL("删除失败"),
	ADD_FAIL("添加失败"),
	EDIT_FAIL("修改失败"),
	OPERATE_FAIL("操作失败"),

	BAD_REQUEST("请求失败"),
	FORBIDDEN("禁止访问"),
	NOT_FOUND("无法找到请求"),
	UPLOAD_FAIL("上传失败"),
	LACK_PARAM("缺少参数"),
	BLANK_PARAM("参数为空"),
	;

	/**
	 * 消息
	 */
	@Getter
	private final String msg;

	ResEnum(String msg) {
		this.msg = msg;
	}

	@Override
	public @NotNull String toString() {
		return this.toJson();
	}
}