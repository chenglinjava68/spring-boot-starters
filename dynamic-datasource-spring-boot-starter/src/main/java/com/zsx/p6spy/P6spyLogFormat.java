package com.zsx.p6spy;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;

/**
 * @Description TODO
 * @Date 2021/3/9 18:14
 * @Created by zhushuxian
 */
public class P6spyLogFormat implements MessageFormattingStrategy {

    @Override
    public String formatMessage(final int connectionId, final String now, final long elapsed, final String category, final String prepared, final String sql, final String url) {
        return StringUtils.isNotBlank(sql) ? new StringBuilder().append( now+" Consume Time：" + elapsed + " ms ").append(" Execute SQL：").append(sql.replaceAll("[\\s]+", StringPool.SPACE)).toString() : null;
    }
}
