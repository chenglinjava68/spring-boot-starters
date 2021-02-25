package com.zsx.sentinel.flow.common;

/**
 * 度量的事件类型
 * @author zsx
 * from https://github.com/wujiuye/qps-helper
 */
public enum MetricEvent {

    /**
     * 异常
     */
    EXCEPTION,
    /**
     * 成功
     */
    SUCCESS,
    /**
     * 耗时
     */
    RT

}
