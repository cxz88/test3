package com.chenxinzhi.endoscopy.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class HeaderDto implements Serializable {
    private String funCode;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date reqTime;
    private String reqTraceNo;
    private String JmCode;
}
