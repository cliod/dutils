package com.wobangkj;

import com.wobangkj.api.Response;
import com.wobangkj.enums.ResultEnum;

import java.util.Date;

/**
 * Hello world!
 *
 * @author cliod
 */
public class App {
    public static void main(String[] args) {

        System.out.println(ResultEnum.ERROR.toObject());

        System.out.println(Response.build().put("id", "1").put("name", "mymy")
                .put("date", new Date()).ok());
    }
}
