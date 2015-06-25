package com.wenin819.easyweb.core.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;

/**
 * 系统调度统一管理类
 * @author wenin819@gmail.com
 */
public final class ExecutorUtils {

    private static final ScheduledExecutorService systemDefualtScheduledExecutorService =
            createScheduledExecutorService();

    private ExecutorUtils() {}

    /**
     * 得到系统默认的调度服务
     * @return
     */
    public static ScheduledExecutorService getSystemScheduledExecutorService() {
        return systemDefualtScheduledExecutorService;
    }

    /**
     * 创建系统调度服务
     * @return
     */
    public static ScheduledExecutorService createScheduledExecutorService() {
        return Executors.newSingleThreadScheduledExecutor(new DaemonThreadFactory());
    }

    /**
     * 守护线程创建工厂
     */
    public static class DaemonThreadFactory implements ThreadFactory {
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setDaemon(true);
            return thread;
        }
    }

}
