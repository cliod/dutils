package com.wobangkj;

import com.wobangkj.util.EncryptUtils;
import com.wobangkj.util.KeyUtils;
import com.wobangkj.util.Md5Utils;

/**
 * Hello world!
 *
 * @author cliod
 */
public class App {

    public static void main(String[] args) {
        String pwd = "123456";

        System.out.println("encrypt");
        System.out.println(EncryptUtils.encodeBase64String(pwd));
        System.out.println(EncryptUtils.encodeMd5String(pwd));
        System.out.println(EncryptUtils.encodeSha1String(pwd));
        System.out.println(EncryptUtils.encodeSha256String(pwd));

        System.out.println("md5");
        System.out.println(Md5Utils.encode32(pwd));
        System.out.println(Md5Utils.encode32ToUpperCase(pwd));
        System.out.println(Md5Utils.encode16(pwd));
        System.out.println(Md5Utils.encode16ToUpperCase(pwd));

        System.out.println("key");
        System.out.println(KeyUtils.encode(pwd));
        System.out.println(KeyUtils.encrypt(pwd));
        System.out.println(KeyUtils.md5(pwd));
    }

}
