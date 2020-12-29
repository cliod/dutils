package com.wobangkj.utils;

import com.google.gson.Gson;
import com.wobangkj.impl.FastJsonImpl;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author cliod
 * @since 12/29/20 10:33 AM
 */
public class JsonUtilsTest {

	@Test
	public void fromJson() {
		Map<?, ?> map = JsonUtils.fromJson("{\"id\":10}", Map.class);
		System.out.println(map);
	}

	@Test
	public void toObject() {
		System.out.println(JsonUtils.toObject("{\"id\":10}"));
	}

	@Test
	public void toJson() {
		JsonUtils.setGson(new Gson());
//		JsonUtils.setJSON(new FastJsonImpl());
		System.out.println(JsonUtils.toJson(""));
		System.out.println(JsonUtils.toJson(new byte[0]));
		System.out.println(JsonUtils.toJson(new HashMap<>(0)));
	}
}