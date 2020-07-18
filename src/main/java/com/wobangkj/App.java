package com.wobangkj;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wobangkj.bean.Single;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        try {
            Single<String> single = Single.of("image", "1");
            System.out.println(new ObjectMapper().writeValueAsString(single));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println("Hello World!");
    }
}
