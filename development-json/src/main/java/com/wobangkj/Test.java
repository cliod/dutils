package com.wobangkj;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cliod
 * @date 2019/11/21
 * package : com.wobangkj
 */
class Test {

    private static final String[] VALUES = {
            "29.215.104.181 54927 rc4-md5 Wj5K7S 10:27:18 RU \n",
            "183.212.130.162 43616 chacha20 7rCLuH6fU 10:27:06 RU \n",
            "135.86.88.94 65329 aes-256-cfb BwxuJXc 10:27:11 JP \n",
            "11.243.199.245 61259 chacha20 6sCemS9rjP 10:27:24 US \n",
            "193.82.92.86 43857 aes-256-cfb S0HbCTPxtz 10:27:24 RU \n",
            "29.93.168.124 41328 rc4-md5 BrHneDukic 10:27:18 RU \n",
            "147.86.250.91 24990 aes-256-cfb y53f04 10:27:07 SG \n",
            "24.53.215.150 2408 rc4-md5 BQ8Kq5auNqgx 10:27:04 SG \n",
            "208.66.176.5 48810 chacha20 sfJAV43 10:27:12 JP \n",
            "158.69.13.142 39413 aes-256-cfb AlsKu8qg 10:27:14 US",
    };

    @NotNull
    static String get() {
        List<Bean> list = new ArrayList<>();
        Bean bean;
        String[] keys;
        for (String value : VALUES) {
            bean = new Bean();
            keys = value.split(" ");
            bean.setServer(keys[0]);
            bean.setServerPort(Integer.parseInt(keys[1]));
            bean.setMethod(keys[2]);
            bean.setPassword(keys[3]);
            bean.setRemarks(keys[0]);
            list.add(bean);
        }
        return new Gson().toJson(list);
//    return JSON.toJSONString(list);
//    try {
//      return new ObjectMapper().writeValueAsString(list);
//    } catch (JsonProcessingException e) {
//      return bean.toString();
//    }
    }

    @Data
    public static class Bean {

        /**
         * method : aes-256-cfb
         * password : 123456
         * remarks : aliyun
         * server : 47.100.237.123
         * server_port : 8388
         */

        private String method;
        private String password;
        private String remarks;
        private String server;
        //gson 序列化和反序列化公用(别名)
        @SerializedName("server_port")
        //com.alibaba.fastjson 序列化和反序列化公用(别名)
        @JSONField(name = "server_port")
        //jackson 反序列化用(别名)
        @JsonAlias("server_port")
        //jackson 序列化用(别名)
        @JsonProperty("server_port")
        private int serverPort;
    }

    @Data
    public static class Entity {
        private int week;
        private String weekday;
        private String time;
    }
}
