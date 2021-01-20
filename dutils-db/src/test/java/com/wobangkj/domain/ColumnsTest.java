package com.wobangkj.domain;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author cliod
 * @since 1/9/21 10:30 AM
 */
public class ColumnsTest {

	@Test
	public void of() {
		Columns obj = Columns.of(Entity.class);
		System.out.println(Arrays.toString(obj.getColumns()));
	}

}