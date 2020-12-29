package com.wobangkj.uuid;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author cliod
 * @since 12/29/20 11:00 AM
 */
public class SnowflakeIdWorkerTest {

	@Test
	public void getNextId() {
		System.out.println(SnowflakeIdWorker.getNextId());
	}

	@Test
	public void nextID() {
		System.out.println(SnowflakeIdWorker.nextID().toString());
	}
}