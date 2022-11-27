package com.chenxinzhi.service.handler;

import com.chenxinzhi.endoscopy.dto.PacstsbrxxgnjxtDto;
import com.chenxinzhi.eums.BusinessType;
import com.chenxinzhi.my.Invocation;
import com.chenxinzhi.service.business.PacsTsbrxxgnjxtHandler;
import org.dom4j.Node;
import org.springframework.stereotype.Service;
//【010203】PACS推送病人信息给内镜系统Body参数处理
@Service
public class PacsTsbrxxgnjxtBodyHandler implements BodyBusinessHandler {
    @Override
    public String getCode() {
        return BusinessType.PACSTSBRXXGNJXT.getCode();
    }

    @Override
    public Object handler(Invocation invocation) throws Exception {
        Node node = invocation.getNode();
        PacstsbrxxgnjxtDto pacstsbrxxgnjxtDto = new PacstsbrxxgnjxtDto();
        try {
            Node bodyNode = node.selectSingleNode("./Body");
            String hzbm = bodyNode.selectSingleNode("./hzbm").getText().trim();
            String hzxm = bodyNode.selectSingleNode("./hzxm").getText().trim();
            String yxbs = bodyNode.selectSingleNode("./yxbs").getText().trim();
            String jcys = bodyNode.selectSingleNode("./jcys").getText().trim();
            String kh = bodyNode.selectSingleNode("./kh").getText().trim();
            String ssbm = bodyNode.selectSingleNode("./ssbm").getText().trim();
            String ssmc = bodyNode.selectSingleNode("./ssmc").getText().trim();
            String yybm = bodyNode.selectSingleNode("./yybm").getText().trim();
            String yymc = bodyNode.selectSingleNode("./yymc").getText().trim();
           pacstsbrxxgnjxtDto.setHzbm(hzbm);
           pacstsbrxxgnjxtDto.setHzbm(hzxm);
           pacstsbrxxgnjxtDto.setYxbs(yxbs);
           pacstsbrxxgnjxtDto.setJcys(jcys);
           pacstsbrxxgnjxtDto.setKh(kh);
           pacstsbrxxgnjxtDto.setSsbm(ssbm);
           pacstsbrxxgnjxtDto.setSsmc(ssmc);
           pacstsbrxxgnjxtDto.setYybm(yybm);
           pacstsbrxxgnjxtDto.setYymc(yymc);
            //设置将要被调用的方法
            invocation.setMethod(PacsTsbrxxgnjxtHandler.class.getMethod("handler", Invocation.class));
        } catch (Exception e) {
            throw new Exception();
        }

        return pacstsbrxxgnjxtDto;
    }



}
