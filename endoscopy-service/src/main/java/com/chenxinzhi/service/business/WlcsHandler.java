package com.chenxinzhi.service.business;

import com.chenxinzhi.common.vo.Response;
import com.chenxinzhi.eums.BusinessType;
import com.chenxinzhi.my.Invocation;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
@Service
public class WlcsHandler implements BusinessHandler{
    @Override
    public String getCode() {
        return BusinessType.WLCS.getCode();
    }

    @Override
    public String handler(Invocation invocation) throws Exception {
        Response<Object> ok = Response.ok(invocation.getHeaderDto().getFunCode(), null,"网络正常");

        ObjectMapper objectMapper = new XmlMapper().enable(SerializationFeature.INDENT_OUTPUT)
                .setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        return objectMapper.writeValueAsString(ok);
    }
}
