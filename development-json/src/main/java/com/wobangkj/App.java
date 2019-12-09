package com.wobangkj;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import static com.wobangkj.Test.*;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws JsonProcessingException {

        System.out.println(JSON.parseArray("[{week:2,weekday: \"周二\",time:\"上午\"},{week:4,weekday: \"周四\"," +
                "time:\"下午\"},{week:6,weekday: \"周六\",time:\"下午\"}]", Entity.class));

        System.out.println(get());
        String a = "{\"method\":\"aes-256-cfb\",\"password\":\"E3nbTDcxCHP3\"," +
                "\"remarks\":\"13.231.170.111\",\"server\":\"13.231.170.111\",\"server_port\":26119}";
        System.out.println(JSON.parseObject(a, Bean.class));
        System.out.println(new ObjectMapper().readValue(a, Bean.class));
        System.out.println(new Gson().fromJson(a, Bean.class));
    }
}
