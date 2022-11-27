package com.chenxinzhi.service.handler;

import com.chenxinzhi.my.Invocation;

public interface BodyBusinessHandler<T> {
    String getCode();
    //判断是否支持该业务的处理
    default boolean support(String code){
        return getCode().equals(code);
    };
    //处理方法
    T handler(Invocation invocation) throws Exception;
}
