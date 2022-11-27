package com.chenxinzhi.service.processor;

import com.chenxinzhi.my.Invocation;

/**
 * 对参数流程的解析处理接口
 */
public interface ParameterProcessor {
    Object handler(Invocation invocation) throws Throwable;
    default Integer getOrder(){
        return Integer.MAX_VALUE;
    };
}
