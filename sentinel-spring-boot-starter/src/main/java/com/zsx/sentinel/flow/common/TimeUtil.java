package com.zsx.sentinel.flow.common;

import java.util.concurrent.TimeUnit;

/**
 * 时间计算工具
 * @author zsx api 经过filter--> interceptor -->aop -->controller 如果某些接口，比如filter
 * from https://github.com/wujiuye/qps-helper
 */
public final class TimeUtil {

    private static volatile long currentTimeMillis;

    static {
        currentTimeMillis = System.currentTimeMillis();
        Thread daemon = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    currentTimeMillis = System.currentTimeMillis();
                    try {
                        TimeUnit.MILLISECONDS.sleep(1);
                    } catch (Throwable e) {

                    }
                }
            }
        });
        daemon.setDaemon(true);
        daemon.setName("qps-helper-time-tick-thread");
        daemon.start();
    }

    public static long currentTimeMillis() {
        return currentTimeMillis;
    }

}
