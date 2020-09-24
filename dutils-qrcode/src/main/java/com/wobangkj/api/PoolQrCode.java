package com.wobangkj.api;

import java.util.concurrent.*;

/**
 * qrCode
 *
 * @author cliod
 * @since 8/22/20 1:23 PM
 */
public class PoolQrCode extends DefaultQrCode {

	private static final SynchronousQueue<PoolQrCode> POOL = new SynchronousQueue<>();
	private static final ExecutorService fork =
			new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), 1000, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());

	protected PoolQrCode() {
	}

	public static PoolQrCode getInstance() {
		if (POOL.isEmpty()) {
			fork.execute(PoolQrCode::initPool);
			return new PoolQrCode();
		}
		return POOL.poll();
	}

	private static void initPool() {
		int i = 100;
		for (int j = 0; j < i; j++) {
			POOL.offer(new PoolQrCode());
		}
	}
}
