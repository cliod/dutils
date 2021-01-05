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

		File file = QrCodeUtils.encode("123", "1.jpg", "/home/cliod/Documents/work-spaces/self-projects/dutils-util/", "tmp.JPG", true);
		System.out.println(file.getName());
	}
}