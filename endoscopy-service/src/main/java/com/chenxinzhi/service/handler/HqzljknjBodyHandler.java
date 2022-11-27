package com.chenxinzhi.service.handler;

import com.chenxinzhi.endoscopy.dto.GetzlkDto;
import com.chenxinzhi.eums.BusinessType;
import com.chenxinzhi.my.Invocation;
import com.chenxinzhi.service.business.HqzljsknjHandler;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Node;
import org.springframework.stereotype.Service;

/**
 * 【010202】获取诊疗间刷卡内镜Body参数处理
 */
@Service
public class HqzljknjBodyHandler implements BodyBusinessHandler<GetzlkDto> {
    @Override
    public String getCode() {
        return BusinessType.HQZLJSKNJ.getCode();
    }

    @Override
    public GetzlkDto handler(Invocation invocation) throws Exception {
        Node node = invocation.getNode();
        String zljip;
        GetzlkDto getzlkDto;
        try {
            Node bodyNode = node.selectSingleNode("./Body");
            zljip = bodyNode.selectSingleNode("./zljip").getText().trim();
            getzlkDto = new GetzlkDto();
            if (StringUtils.isEmpty(zljip)) {
                throw new Exception();
            }
            //设置将要被调用的方法
            invocation.setMethod(HqzljsknjHandler.class.getMethod("handler", Invocation.class));
        } catch (Exception e) {
            throw new Exception();
        }
        getzlkDto.setZljip(zljip);
        return getzlkDto;
    }


}
