package com.wobangkj.api;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * qrCode
 *
 * @author cliod
 * @since 8/22/20 1:23 PM
 */
public class PoolQrCode extends DefaultQrCode {

	private static final AtomicInteger THREAD_NUMBER = new AtomicInteger(1);

	private static final SynchronousQueue<PoolQrCode> POOL = new SynchronousQueue<>();
	private static final ExecutorService FORK = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),
			1000, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), r -> {
		String name = "二维码进程-" + THREAD_NUMBER.getAndIncrement();
		Thread t = new Thread(Thread.currentThread().getThreadGroup(), r, name, 0);
		if (t.isDaemon()) {
			t.setDaemon(false);
		}
		if (t.getPriority() != Thread.NORM_PRIORITY) {
			t.setPriority(Thread.NORM_PRIORITY);
		}
		return t;
	});

	protected PoolQrCode() {
	}

	public static PoolQrCode getInstance() {
		if (POOL.isEmpty()) {
			FORK.execute(PoolQrCode::initPool);
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
