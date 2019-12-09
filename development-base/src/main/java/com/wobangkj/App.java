package com.wobangkj;

import com.wobangkj.api.Session;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        test();
    }

    public static void test() {
        Session session = new Session() {
            @Getter
            private String name = "Lihua";
            @Getter
            private String love = "I love Chinese";

            @NotNull
            @Override
            public String toString() {
                return this.toJson();

            }

            @NotNull
            @Override
            public String toJson() {
                return "{" +
                        "name:\"" + this.getName() + '\"' +
                        ", love:\"" + this.getLove() + '\"' +
                        '}';
            }
        };

        System.out.println(session.toJson());
        System.out.println(session.toObject());
    }
}
