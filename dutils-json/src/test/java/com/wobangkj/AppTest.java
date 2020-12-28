package com.wobangkj;

import com.wobangkj.impl.JacksonJsonImpl;
import com.wobangkj.jackson.DefaultSerializer;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class AppTest {
	/**
	 * Rigorous Test :-)
	 */
	@Test
	public void shouldAnswerWithTrue() {
		JacksonJsonImpl json = new JacksonJsonImpl();
		json.setObjectMapper(DefaultSerializer.getInstance().objectMapper());
		Map<String, Object> map = new HashMap<>();
		map.put("id", "1");
//		map.put("time", LocalDateTime.now());
		map.put("date", new Date());
		System.out.println(json.toJson(map));
	}
}
