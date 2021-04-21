package com.wobangkj.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 授权者
 *
 * @author cliod
 * @version 1.0
 * @since 2021-01-04 11:42:08
 */
public class Author extends Authorized {
	/**
	 * id
	 */
	private Object id;
	/**
	 * 用户ip
	 */
	private Object key;
	/**
	 * 角色，数值越小，权限越大
	 */
	private Object role;
	/**
	 * json格式自定义数据
	 */
	private Object data;
	/**
	 * 有效时长
	 */
	private Long time;

	public static Builder builder() {
		return new Builder();
	}

	@JsonIgnore
	public Duration getDuration() {
		if (this.getTime() == null) {
			this.setTime(0L);
		}
		return Duration.ofMinutes(this.getTime());
	}

	@JsonIgnore
	public void setDuration(Duration duration) {
		this.setTime(duration.toMillis());
		this.setExpireAt(Instant.now().plus(duration));
	}

	public Object getId() {
		return Optional.ofNullable(id).orElse(this.get("id"));
	}

	public void setId(Object id) {
		this.id = id;
		this.put("id", id);
	}

	public Object getKey() {
		return Optional.ofNullable(key).orElse(this.get("key"));
	}

	public void setKey(Object key) {
		this.key = key;
		this.put("key", key);
	}

	public Object getRole() {
		return Optional.ofNullable(role).orElse(this.get("tole"));
	}

	public void setRole(Object role) {
		this.role = role;
		this.put("role", role);
	}

	public Object getData() {
		return Optional.ofNullable(data).orElse(this.get("data"));
	}

	public void setData(Object data) {
		this.data = data;
		this.put("data", data);
	}

	public Long getTime() {
		return Optional.ofNullable(time).orElse((Long) this.get("time"));
	}

	public void setTime(Long time) {
		this.time = time;
		this.put("time", time);
	}

	public static class Builder {
		private final Map<String, Object> data;

		public Builder() {
			this.data = new HashMap<>();
		}

		public Builder key(Object key) {
			this.data.put("key", key);
			return this;
		}

		public Builder role(Object role) {
			this.data.put("role", role);
			return this;
		}

		public Builder data(Object data) {
			this.data.put("data", data);
			return this;
		}

		public Builder id(Object id) {
			this.data.put("id", id);
			return this;
		}

		public Author build() {
			Author author = new Author();
			author.putAll(this.data);
			return author;
		}
	}
}
