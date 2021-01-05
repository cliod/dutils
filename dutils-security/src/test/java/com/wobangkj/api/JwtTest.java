package com.wobangkj.api;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * @author cliod
 * @since 1/5/21 11:51 AM
 */
public class JwtTest {

	@Test
	public void sign() {
		Jwt jwt = SimpleJwt.getInstance();
		String a = jwt.sign("1", 10000);
		System.out.println(a);
		System.out.println(jwt.unsign(a).asString());

		String b = jwt.sign(1, 10000);
		System.out.println(b);
		System.out.println(jwt.unsign(b).asInt());

		String d = jwt.sign(new HashMap<String, Object>(4) {{put("value", 1);}}, 10000);
		System.out.println(d);
		System.out.println(jwt.unsignToMap(d));
	}
}