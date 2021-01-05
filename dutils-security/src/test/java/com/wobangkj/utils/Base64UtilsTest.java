package com.wobangkj.utils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author cliod
 * @since 1/5/21 11:58 AM
 */
public class Base64UtilsTest {

	@Test
	public void encode() {
		System.out.println(Base64Utils.encodeToString("123456"));
	}

	@Test
	public void decode() {
		System.out.println(Base64Utils.decodeToString("MTIzNDU2"));

	}
}