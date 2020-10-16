package com.wobangkj.bean;

import com.wobangkj.utils.RefUtils;
import lombok.Data;
import lombok.SneakyThrows;

import java.io.Serializable;
import java.util.Date;

/**
 * 文件信息
 *
 * @author cliod
 * @since 10/14/20 5:13 PM
 */
@Data
public class Summary {
	private long size;
	private String url;
	private String storage;
	private Owner owner;
	private Date lastModified;

	protected Summary() {
	}

	public static Summary of(long size, String url, String storage, Owner owner, Date lastModified) {
		Summary summary = new Summary();
		summary.set(size, url, storage, owner, lastModified);
		return summary;
	}

	protected void set(long size, String url, String storage, Owner owner, Date lastModified) {
		this.size = size;
		this.url = url;
		this.storage = storage;
		this.owner = owner;
		this.lastModified = lastModified;
	}

	/**
	 * owner兼容对象
	 */
	@Data
	public static class Owner implements Serializable {
		private static final long serialVersionUID = -627349170629705388L;
		private String displayName;
		private String id;

		@SneakyThrows
		public static Owner of(Object obj) {
			Owner owner = new Owner();
			owner.setDisplayName(RefUtils.getFieldValue(obj, "displayName", String.class));
			owner.setId(RefUtils.getFieldValue(obj, "id", String.class));
			return owner;
		}
	}
}
