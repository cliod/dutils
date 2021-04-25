package com.wobangkj.api;

import org.junit.Test;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

/**
 * @author cliod
 * @since 1/5/21 11:51 AM
 */
public class JwtTest {

	@Test
	public void sign() throws NoSuchAlgorithmException {
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

//		jwt = new DBStorageJwt().instance("wobangkj2019");
		jwt = new DBStorageJwt().instance("root", "wobangkj2019", "127.0.0.1", "3306");
		a = jwt.sign("1", 10000);
		System.out.println(a);
		System.out.println(jwt.unsign(a).asString());
	}
}