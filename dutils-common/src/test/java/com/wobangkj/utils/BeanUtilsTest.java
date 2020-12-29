package com.wobangkj.utils;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * @author cliod
 * @since 12/29/20 11:07 AM
 */
public class BeanUtilsTest {

	@Test
	public void isEmpty() {
		System.out.println(BeanUtils.isEmpty(""));
		System.out.println(BeanUtils.isEmpty(new HashMap<>()));
		System.out.println(BeanUtils.isEmpty(new byte[0]));
	}
}