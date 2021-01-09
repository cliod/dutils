package com.wobangkj.domain;

import org.junit.Test;

/**
 * @author cliod
 * @since 1/9/21 10:30 AM
 */
public class ColumnsTest {

	@Test
	public void of() {
		Object obj = Columns.of(Entity.class);
		System.out.println(obj);
	}

}