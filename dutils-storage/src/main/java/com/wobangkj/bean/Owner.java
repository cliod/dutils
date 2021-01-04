package com.wobangkj.bean;

import com.wobangkj.utils.RefUtils;
import lombok.Data;
import lombok.SneakyThrows;

import java.util.Map;

/**
 * 所有者
 * 兼容对象
 *
 * @author cliod
 * @version 1.0
 * @since 2021-01-04 14:02:54
 */
@Data
public class Owner {
	private static final long serialVersionUID = -627349170629705388L;
	private String displayName;
	private String id;

	@SneakyThrows
	public static Owner of(Object obj) {
		Owner owner = new Owner();
		Map<String, Object> o = RefUtils.getFieldValues(obj);
		owner.setDisplayName((String) o.get("displayName"));
		owner.setId((String) o.get("id"));
		return owner;
	}
}
