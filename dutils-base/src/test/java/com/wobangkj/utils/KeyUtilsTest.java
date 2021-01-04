package com.wobangkj.utils;

import org.junit.Test;

import java.security.NoSuchAlgorithmException;

/**
 * @author cliod
 * @since 12/29/20 10:07 AM
 */
public class KeyUtilsTest {

	@Test
	public void randNum() {
		System.out.println(KeyUtils.randNum(86));
		System.out.println(KeyUtils.randNum(9));
		System.out.println(KeyUtils.randNum(10));
		System.out.println(KeyUtils.randNum(6));
		System.out.println(KeyUtils.randNum(0));
		System.out.println(KeyUtils.randNum(-1));
	}

	@Test
	public void decode() {
		System.out.println(KeyUtils.decode("MQ=="));
		System.out.println(KeyUtils.decode("MTEyMzQ="));
		System.out.println(KeyUtils.decode(""));
		System.out.println(KeyUtils.decode(new byte[0]));
	}

	@Test
	public void encode() {
		System.out.println(KeyUtils.encode(""));
		System.out.println(KeyUtils.encode("1"));
		System.out.println(KeyUtils.encode("11234"));
		System.out.println(KeyUtils.encode("".getBytes()));
		System.out.println(KeyUtils.encode(new byte[0]));
	}

	@Test
	public void encrypt() throws NoSuchAlgorithmException {
		System.out.println(KeyUtils.encrypt(""));
		System.out.println(KeyUtils.encrypt(null));
		System.out.println(KeyUtils.encrypt("123"));
	}

	@Test
	public void md5() {
		System.out.println(KeyUtils.md5Hex(""));
		System.out.println(KeyUtils.md5Hex("123"));
	}

	@Test
	public void isNumeric() {
		System.out.println(KeyUtils.isNumeric("123"));
		System.out.println(KeyUtils.isNumeric(""));
		System.out.println(KeyUtils.isNumeric("a"));
		System.out.println(KeyUtils.isNumeric(" "));
		System.out.println(KeyUtils.isNumeric(" 1"));
		System.out.println(KeyUtils.isNumeric("1 "));
	}

	@Test
	public void getByteSize() {
		System.out.println(KeyUtils.getByteSize(""));
		System.out.println(KeyUtils.getByteSize("1"));
		System.out.println(KeyUtils.getByteSize(null));
	}

	@Test
	public void getInParam() {
		System.out.println(KeyUtils.getInParam("1,1,2"));
		System.out.println(KeyUtils.getInParam("1"));
	}
}