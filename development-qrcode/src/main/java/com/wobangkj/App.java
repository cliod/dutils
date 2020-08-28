package com.wobangkj;

import com.google.zxing.WriterException;
import com.wobangkj.api.QrCode;

import java.io.File;
import java.io.IOException;

/**
 * Hello world!
 */
public class App {
	public static void main(String[] args) throws IOException, WriterException {
		System.out.println("Hello World!");

		QrCode qrCode = QrCode.of("1shtigah3045yurt03ufijsedifjisd");
		qrCode.setLogo(new File("1.jpg"));
		qrCode.createImage(new File("2.jpg"));
		qrCode.setLogo(new File("2.jpg"));
		qrCode.createImage(new File("3.jpg"));
	}
}
