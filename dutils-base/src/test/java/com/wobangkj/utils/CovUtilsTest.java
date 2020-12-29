package com.wobangkj.utils;

import lombok.Data;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author cliod
 * @since 12/29/20 10:20 AM
 */
public class CovUtilsTest {

	@Test
	public void convert() throws NoSuchFieldException, IllegalAccessException {
		List<Demo> demos = new ArrayList<>();
		demos.add(new Demo(1L, "1", "1"));
		demos.add(new Demo(3L, "3", new byte[10]));
		demos.add(new Demo(2L, "2", 10.0));
		Map<Long, Demo> map = CovUtils.convert(demos);
		Map<String, Demo> map1 = CovUtils.convert(demos, "key");
		Map<String, Object> map2 = CovUtils.convert(demos, "key", "value");
		System.out.println(JsonUtils.toJson(map));
		System.out.println(JsonUtils.toJson(map1));
		System.out.println(JsonUtils.toJson(map2));
	}

	@Test
	public void statistics() throws NoSuchFieldException, IllegalAccessException {
		List<Demo> demos = new ArrayList<>();
		demos.add(new Demo(1L, "1", "1"));
		demos.add(new Demo(3L, "2", new byte[10]));
		demos.add(new Demo(2L, "2", 10.0));
		Map<Long, Long> map = CovUtils.statistics(demos, "key");
		System.out.println(JsonUtils.toJson(map));
	}

	@Data
	private static class Demo {
		private Long id;
		private String key;
		private Object value;

		public Demo(Long id, String key, Object value) {
			this.id = id;
			this.key = key;
			this.value = value;
		}
	}
}