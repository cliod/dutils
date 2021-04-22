package com.wobangkj;

import com.wobangkj.api.Serializer;
import lombok.Data;

/**
 * 测试的实体类型
 *
 * @author cliod
 * @version 1.0
 * @since 2021-04-22 15:45:09
 */
@Data
public class TestBean implements Serializer {
	private String name;
	private Long id;

	/**
	 * 序列化
	 *
	 * @return json字符串
	 */
	@Override
	public String toJson() {
		return name + "," + id;
	}
}
