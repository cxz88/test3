package com.chenxinzhi.service.business;

import com.chenxinzhi.common.vo.Response;
import com.chenxinzhi.eums.BusinessType;
import com.chenxinzhi.my.Invocation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

//PACS推送病人信息给内镜系统业务代码
@Service
public class PacsTsbrxxgnjxtHandler implements BusinessHandler{


    @Override
    public String getCode() {
        return BusinessType.PACSTSBRXXGNJXT.getCode();
    }
    @Override
    public String handler(Invocation invocation) throws JsonProcessingException {
        //TODO 由于看了是推送系统，所以不知道咋如何推送，这里暂时搁置不知道如何做
        //
        Response<Object> ok = Response.ok(invocation.getHeaderDto().getFunCode(), null,"返回成功");

        ObjectMapper objectMapper = new XmlMapper().enable(SerializationFeature.INDENT_OUTPUT)
                .setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        return objectMapper.writeValueAsString(ok);
    }
}
