package com.wobangkj.auth;

import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wobangkj.api.SessionSerializable;

import java.time.Instant;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

/**
 * 授权者
 *
 * @author cliod
 * @version 1.0
 * @since 2021-04-21 13:36:43
 */
public abstract class Authorized extends HashMap<String, Object> implements SessionSerializable {
	private Date expireAt;

	public Date getExpireAt() {
		return Optional.ofNullable(expireAt).orElse((Date) this.get("expireAt"));
	}

	@JsonIgnore
	public void setExpireAt(Date expireAt) {
		this.expireAt = expireAt;
		this.put("expireAt", expireAt);
	}

	@JsonIgnore
	public void setExpireAt(TemporalAccessor expireAt) {
		this.setExpireAt(DateUtil.date(expireAt));
	}

	@JsonIgnore
	public void setExpireAt(Instant expireAt) {
		this.setExpireAt(Date.from(expireAt));
	}

	/**
	 * 获取授权数据
	 *
	 * @return 结果数据
	 */
	public abstract Object getData();
}
