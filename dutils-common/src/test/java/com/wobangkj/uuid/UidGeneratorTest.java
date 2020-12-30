package com.wobangkj.uuid;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author cliod
 * @since 12/30/20 4:08 PM
 */
public class UidGeneratorTest {

	@Test
	public void getUid() {
		UidGenerator generator = DefaultUidGenerator.getInstance();
		long id = generator.getUid();
		System.out.println(id);
		System.out.println(generator.parseUid(id));
	}
}