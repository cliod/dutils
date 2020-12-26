package com.wobangkj;

import com.wobangkj.api.DefaultQrCode;
import com.wobangkj.api.QrCode;
import com.wobangkj.utils.QrCodeUtils;
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
    public void shouldAnswerWithTrue() throws Exception {
        assertTrue(true);

        System.out.println("Hello World!");

        QrCode qrCode = DefaultQrCode.of("1shtigah3045yurt03ufijsedifjisd");
        qrCode.createImage(new File("1.jpg"));
        qrCode.setLogo(new File("1.jpg"));
        qrCode.createImage(new File("2.jpg"));
        qrCode.setLogo(new File("2.jpg"));
        qrCode.createImage(new File("3.jpg"));

        File file = QrCodeUtils.encode("123", "1.jpg", "/home/cliod/Documents/work-spaces/self-projects/dutils-util/", "tmp.JPG", true);
        System.out.println(file.getName());
    }
}
