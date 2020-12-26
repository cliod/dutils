package com.wobangkj;

import com.wobangkj.thread.WorkThreadFactory;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程管理
 *
 * @author cliod
 * @since 2020/06/22
 */
public final class ThreadExecutor {

	public static final ThreadPoolExecutor FORK;

	private static final int CORE_POLL_SIZE = Runtime.getRuntime().availableProcessors();
	private static final int MAX_POLL_SIZE = 100;
	private static final int KEEP_ALIVE_TIME = 0;

	static {
		FORK = new ThreadPoolExecutor(CORE_POLL_SIZE, MAX_POLL_SIZE, KEEP_ALIVE_TIME, TimeUnit.NANOSECONDS,
				new LinkedBlockingDeque<>(), new WorkThreadFactory("程序子线程处理"), new ThreadPoolExecutor.DiscardPolicy());
	}
}