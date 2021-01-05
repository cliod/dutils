package com.wobangkj.utils;

import org.junit.Test;

import java.security.GeneralSecurityException;

import static org.junit.Assert.*;

/**
 * @author cliod
 * @since 1/5/21 11:57 AM
 */
public class AesUtilsTest {

	@Test
	public void encode() throws GeneralSecurityException {
		System.out.println(AesUtils.encode("123456"));
	}

	@Test
	public void decode() throws GeneralSecurityException {
		System.out.println(AesUtils.decode("Jm2ApONeSIb5V4Fvk1Ss5Q=="));
	}
}