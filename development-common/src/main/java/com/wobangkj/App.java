package com.wobangkj;

import com.wobangkj.api.Response;
import com.wobangkj.enums.ResultEnum;
import com.wobangkj.enums.type.GenderType;

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
                .put("date", new Date().toInstant().toString()).ok());

        System.out.println(GenderType.valueOf("FEMALE").toObject());
        System.out.println(GenderType.valueOf("FEMALE").name());

    }
}
