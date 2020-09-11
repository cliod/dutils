package com.wobangkj;

import org.jetbrains.annotations.NotNull;

import java.util.Timer;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程管理
 *
 * @author cliod
 * @since 2020/06/22
 */
public final class ThreadExecutor {

	public static final Timer delay;
	public static final ThreadPoolExecutor delete;
	public static final ThreadPoolExecutor pay;
	public static final ThreadPoolExecutor fork;
	public static final ScheduledThreadPoolExecutor timer;
	public static final ScheduledThreadPoolExecutor timing;

	private static final int corePollSize = 10;
	private static final int maxPollSize = 100;
	private static final int keepAliveTime = 0;

	static {
		delete = new ThreadPoolExecutor(corePollSize, maxPollSize, keepAliveTime, TimeUnit.NANOSECONDS,
				new LinkedBlockingDeque<>(), new WorkThreadFactory("用户删除"), new ThreadPoolExecutor.DiscardPolicy());
		pay = new ThreadPoolExecutor(corePollSize, maxPollSize, keepAliveTime, TimeUnit.NANOSECONDS,
				new LinkedBlockingDeque<>(), new WorkThreadFactory("支付回调"), new ThreadPoolExecutor.DiscardPolicy());
		fork = new ThreadPoolExecutor(corePollSize, maxPollSize, keepAliveTime, TimeUnit.NANOSECONDS,
				new LinkedBlockingDeque<>(), new WorkThreadFactory("程序子线程处理"), new ThreadPoolExecutor.DiscardPolicy());
		timer = new ScheduledThreadPoolExecutor(corePollSize, new WorkThreadFactory("自动任务"), new ThreadPoolExecutor.DiscardPolicy());
		timing = new ScheduledThreadPoolExecutor(corePollSize, new WorkThreadFactory("缓存任务"), new ThreadPoolExecutor.DiscardPolicy());
		delay = new Timer("延时任务");
	}

	/**
	 * 自定义工厂
	 */
	private static class WorkThreadFactory implements ThreadFactory {
		private static final AtomicInteger poolNumber = new AtomicInteger(1);
		private final ThreadGroup group;
		private final AtomicInteger threadNumber = new AtomicInteger(1);
		private final String name;

		WorkThreadFactory(String name) {
			this.name = name + "-pool-" + poolNumber.getAndIncrement();
			SecurityManager s = System.getSecurityManager();
			group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
		}

		@Override
		public Thread newThread(@NotNull Runnable r) {
			String name = this.name + "-thread-" + threadNumber.getAndIncrement();
			Thread t = new Thread(group, r, name, 0);
			if (t.isDaemon()) {
				t.setDaemon(false);
			}
			if (t.getPriority() != Thread.NORM_PRIORITY) {
				t.setPriority(Thread.NORM_PRIORITY);
			}
			return t;
		}

	}
}