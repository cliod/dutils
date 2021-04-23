package com.wobangkj;

import com.wobangkj.api.Serializer;
import com.wobangkj.auth.Authenticate;
import com.wobangkj.auth.Author;
import org.junit.Test;

import java.util.Map;

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

		class User extends TestBean {
		}

		token = Authenticate.authorize(Author.builder().data(new User() {{
			setId(10L);
			setName("ces");
		}}).build());
		System.out.println(token);
		serializer = Authenticate.authenticate(token);
		assert serializer != null;
		System.out.println(serializer.toJson());
		author = Authenticate.authenticate(token);
		System.out.println(author);
		assert author != null;
		System.out.println(author.getData());
		System.out.println(author.getData(TestBean.class));
		System.out.println(author.getData(Map.class));
	}
}
