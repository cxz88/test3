package com.chenxinzhi.service.business;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chenxinzhi.common.vo.Response;
import com.chenxinzhi.endoscopy.dto.GetzlkDto;
import com.chenxinzhi.endoscopy.pojo.Zljsk;
import com.chenxinzhi.endoscopy.vo.ZljskVo;
import com.chenxinzhi.eums.BusinessType;
import com.chenxinzhi.exception.ArgException;
import com.chenxinzhi.my.Invocation;
import com.chenxinzhi.service.IZljskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

//获取诊疗间刷卡内镜业务代码
@Service
public class HqzljsknjHandler implements BusinessHandler{
    @Autowired
    private IZljskService zljskService;
    @Autowired
    private Check check;


    @Override
    public String getCode() {
        return BusinessType.HQZLJSKNJ.getCode();
    }
    @Override
    public String handler(Invocation invocation) throws Exception {

        //根据需要去数据库进行查询
        Object body = invocation.getBody();
        List<Zljsk> list = null;
        if (body instanceof GetzlkDto) {
            String zljip = ((GetzlkDto) body).getZljip();
            //对相关参数进行校验
            if (!check.checkArgs(invocation.getHeaderDto(),body)) {
                throw new ArgException("你没有权限访问");
            }
            if (StringUtils.isEmpty(zljip)) {
                throw new ArgException("诊疗间id不可以为空");
            }
            //根据id去数据库查询
            list = zljskService.list(new LambdaQueryWrapper<>(Zljsk.class).eq(Zljsk::getZjlip, zljip));
        }
        if (list == null) {
            list = new ArrayList<>();
        }
        ZljskVo zljskVo = new ZljskVo();
        zljskVo.setAljsk(list);
        Response<Object> ok = Response.ok(invocation.getHeaderDto().getFunCode(), zljskVo);

        ObjectMapper objectMapper = new XmlMapper().enable(SerializationFeature.INDENT_OUTPUT)
                .setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        return objectMapper.writeValueAsString(ok);
    }
}
