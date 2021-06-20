package com.wobangkj.api;

import java.util.concurrent.SynchronousQueue;

/**
 * 快速创建QrCode
 *
 * @author cliod
 * @version 1.0
 * @since 2021-06-18 23:40:42
 */
public class FastQrCode extends BaseQrCode {
	private static final SynchronousQueue<QrCode> POOL = new SynchronousQueue<>();

	public static QrCode getInstance() {
		if (POOL.isEmpty()) {
			new Thread(FastQrCode::initPool, "初始化QrCode").start();
			return DefaultQrCode.getInstance();
		}
		return POOL.poll();
	}

	private static void initPool() {
		int i = 100;
		for (int j = 0; j < i; j++) {
			POOL.offer(DefaultQrCode.getInstance());
		}
	}
}
