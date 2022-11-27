package com.chenxinzhi.service.business;

import com.chenxinzhi.my.Invocation;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;

public interface BusinessHandler{
    String getCode();
    //判断是否支持该业务的处理
    default boolean support(String code){
        return getCode().equals(code);
    };
    //处理方法
    String handler(Invocation invocation) throws Exception;
}
