package com.wobangkj;

import cn.hutool.captcha.AbstractCaptcha;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.ShearCaptcha;
import com.wobangkj.api.MultiStyleCaptcha;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);

        AbstractCaptcha captcha;

        captcha = new MultiStyleCaptcha(100, 20, 4, 20);
        captcha.write(new File("./1.png"));

        captcha = new LineCaptcha(100, 20, 4, 20);
        captcha.write(new File("./2.png"));

        captcha = new CircleCaptcha(100, 20, 4, 10);
        captcha.write(new File("./3.png"));

        captcha = new ShearCaptcha(100, 20, 4, 5);
        captcha.write(new File("./4.png"));

        System.out.println("Hello World!");
    }
}
