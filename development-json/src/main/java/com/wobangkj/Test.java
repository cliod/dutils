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
            "13.231.170.111 26119 aes-256-cfb E3nbTDcxCHP3 09:07:07 JP \n",
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
//        return JSON.toJSONString(list);
//        try {
//            return new ObjectMapper().writeValueAsString(list);
//        } catch (JsonProcessingException e) {
//            return bean.toString();
//        }
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
