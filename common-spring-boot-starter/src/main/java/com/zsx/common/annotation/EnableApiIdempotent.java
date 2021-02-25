package com.zsx.common.annotation;

import com.zsx.common.selector.ApiIdempotentImportSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启动幂等拦截器
 * @author zsx
 * @create 2019年9月5日
 * 自动装配starter
 * 选择器
 * blog: https://blog.51cto.com/13005375 
 * code: https://gitee.com/owenwangwen/open-capacity-platform
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented

@Import(ApiIdempotentImportSelector.class)
public @interface EnableApiIdempotent {
}
