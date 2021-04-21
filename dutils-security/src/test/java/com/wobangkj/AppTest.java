package com.wobangkj;

import com.wobangkj.api.Serializer;
import com.wobangkj.auth.Authenticate;
import com.wobangkj.auth.Author;
import lombok.Data;
import org.junit.Test;

import java.util.Date;

/**
 * Unit test for simple App.
 */
public class AppTest {
	/**
	 * Rigorous Test :-)
	 */
	@Test
	public void shouldAnswerWithTrue() {
		String token = Authenticate.authorize("", 1, 1);
		System.out.println(token);

		Author author = Authenticate.authenticate(token);
		System.out.println(author);
		Serializer serializer = Authenticate.authenticate(token);
		assert serializer != null;
		System.out.println(serializer.toJson());

		@Data
		class User implements Serializer {
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

		token = Authenticate.authorize(new User() {{
			setId(10L);
			setName("ces");
		}}, new Date(System.currentTimeMillis() + 100000000));
		System.out.println(token);
		serializer = Authenticate.authenticate(token);
		assert serializer != null;
		System.out.println(serializer.toJson());
		author = Authenticate.authenticate(token);
		System.out.println(author);

	}
}
