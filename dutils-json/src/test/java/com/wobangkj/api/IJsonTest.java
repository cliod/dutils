package com.wobangkj.api;

import com.wobangkj.impl.FastJsonImpl;
import com.wobangkj.impl.GsonJsonImpl;
import com.wobangkj.impl.JacksonJsonImpl;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

/**
 * @author cliod
 * @since 12/26/20 1:40 PM
 */
public class IJsonTest {

	@Test
	public void toJson() {
		JacksonJsonImpl j = new JacksonJsonImpl();
		String l = j.toJson(new HashMap<String, Object>(){{put("code",1);}});
		System.out.println(l);
	}

	@Test
	public void toObject() {
		JacksonJsonImpl j = new JacksonJsonImpl();
		String str = "[{\"id\":1}]";
		Object obj = j.toObject(str, List.class);
		System.out.println(obj);
		List<?> l = j.toList(str);
		System.out.println(l);
	}
}