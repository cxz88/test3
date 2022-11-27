package com.chenxinzhi.common.vo;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@JacksonXmlRootElement(localName = "Response")
@Data
public class Response<T> implements Serializable {
    @JacksonXmlProperty(localName = "Header")
    private Header header;
    @JacksonXmlProperty(localName = "Body")
    private T body;

    @Data
    public static class Header implements Serializable {
        @JacksonXmlProperty(localName = "FunCode")
        private String funCode;
        @JacksonXmlProperty(localName ="ResultCode")
        private String resultCode;
        @JacksonXmlProperty(localName ="ResultMsg")
        private String resultMsg;
        @JacksonXmlProperty(localName ="OpTime")
        private Date opTime;

    }
    public static <T> Response<T> ok(String funCode,T body){
        return ok(funCode,body,"请求成功");


    }
    public static <T> Response<T> ok(String funCode,T body,String resultMsg){
        Response<T> tResponse = new Response<>();
        Header header_ = new Header();
        header_.setFunCode(funCode);
        header_.setOpTime(new Date());
        header_.setResultMsg(resultMsg);
        header_.setResultCode(String.valueOf(0));
        tResponse.setHeader(header_);
        tResponse.setBody(body);
        return tResponse;


    }
    public static <T> Response<T> err(String funCode,String msg){
        Response<T> tResponse = new Response<>();
        Header header_ = new Header();
        header_.setFunCode(funCode);
        header_.setOpTime(new Date());
        header_.setResultMsg(msg);
        header_.setResultCode(String.valueOf(-1));
        tResponse.setHeader(header_);
        return tResponse;


    }
}



