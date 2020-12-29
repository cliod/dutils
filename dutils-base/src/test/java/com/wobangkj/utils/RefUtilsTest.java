package com.wobangkj.utils;

import org.junit.Test;

import java.util.HashMap;

/**
 * @author cliod
 * @since 12/29/20 10:51 AM
 */
public class RefUtilsTest {

	@Test
	public void getFieldValue() throws NoSuchFieldException, IllegalAccessException {
		System.out.println(RefUtils.getFieldValue(new HashMap<String, Object>(), "loadFactor"));
	}
}