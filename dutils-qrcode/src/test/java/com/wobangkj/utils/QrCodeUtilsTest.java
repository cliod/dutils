package com.wobangkj.utils;

import com.google.zxing.WriterException;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * @author cliod
 * @since 1/5/21 11:49 AM
 */
public class QrCodeUtilsTest {

	@Test
	public void encode() throws IOException, WriterException {

		File file = new File("/home/cliod/Documents/work-spaces/self-projects/dutils/tmp.JPG");
		File logo = new File("1.jpg");
		QrCodeUtils.encode("123", logo, file);
		System.out.println(file.getName());
	}
}