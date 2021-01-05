package com.wobangkj.api;

import com.google.zxing.WriterException;
import com.wobangkj.utils.QrCodeUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * @author cliod
 * @since 1/5/21 11:48 AM
 */
public class QrCodeTest {

	@Test
	public void createImage() throws IOException, WriterException {
		QrCode qrCode = DefaultQrCode.of("1shtigah3045yurt03ufijsedifjisd");
		qrCode.createImage(new File("1.jpg"));
		qrCode.setLogo(new File("1.jpg"));
		qrCode.createImage(new File("2.jpg"));
		qrCode.setLogo(new File("2.jpg"));
		qrCode.createImage(new File("3.jpg"));
	}
}