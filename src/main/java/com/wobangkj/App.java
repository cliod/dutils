package com.wobangkj;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wobangkj.bean.Pair;
import com.wobangkj.bean.Single;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        try {
            Single<String> single = Single.of("1");
            System.out.println(new ObjectMapper().writeValueAsString(single));
            Pair<String, Integer> pair = Pair.of("1", 100);
            System.out.println(new ObjectMapper().writeValueAsString(pair));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println("Hello World!");
    }
}
