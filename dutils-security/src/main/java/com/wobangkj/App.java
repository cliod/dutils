package com.wobangkj;

import com.wobangkj.api.Jwt;
import com.wobangkj.api.SimpleFluxJwt;

import java.util.HashMap;

/**
 * Hello world!
 *
 * @author cliod
 */
public class App {

	public static void main(String[] args) {
		Jwt jwt = SimpleFluxJwt.getInstance();
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
