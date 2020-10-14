package com.wobangkj;

import com.wobangkj.api.SessionSerializable;
import com.wobangkj.utils.JsonUtils;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

/**
 * Hello world!
 *
 * @author cliod
 */
public class App {
	public static void main(String[] args) {
		System.out.println("Hello World!");
		test();
	}

	public static void test() {
		SessionSerializable serializable = new SessionSerializable() {
			@Getter
			private final String name = "Lihua";
			@Getter
			private final String love = "I love Chinese";

			@NotNull
			@Override
			public String toString() {
				return this.toJson();
			}

			@NotNull
			@Override
			public String toJson() {
				return JsonUtils.toJson(this);
			}
		};

		System.out.println(serializable.toJson());
		System.out.println(serializable.toObject());
	}
}
