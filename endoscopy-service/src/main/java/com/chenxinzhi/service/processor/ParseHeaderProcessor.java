package com.chenxinzhi.service.processor;

import cn.hutool.core.util.XmlUtil;
import com.chenxinzhi.endoscopy.dto.HeaderDto;
import com.chenxinzhi.exception.ArgException;
import com.chenxinzhi.my.Invocation;
import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

//解析header参数
@Service
public class ParseHeaderProcessor implements ParameterProcessor{

    @Override
    public Object handler(Invocation invocation) throws Throwable {
        //获取请求的数据字符串
        Object argument = invocation.getArguments()[0];
        if (argument instanceof String) {
            argument = XmlUtil.toStr(XmlUtil.parseXml(argument.toString()));
            //设置xpath
            SAXReader saxReader = new SAXReader();
            Node node;
            String funCode;
            String reqTraceNo;
            String jmCode;
            Date reqTime;
            try {
                //读取字符串到xpath中
                Document document = saxReader.read(new ByteArrayInputStream(((String) argument).getBytes()));
                //进行解析Header参数
                node = document.selectSingleNode("//Request");
                Node headerNode = node.selectSingleNode("./Header");
                System.out.println(argument);
                funCode = headerNode.selectSingleNode("./FunCode").getText().trim();
                reqTraceNo = headerNode.selectSingleNode("./ReqTraceNo").getText().trim();
                jmCode = headerNode.selectSingleNode("./JmCode").getText().trim();
                reqTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(headerNode.selectSingleNode("./ReqTime").getText().trim());
                //新建Header对象
                HeaderDto headerDto = new HeaderDto();
                headerDto.setFunCode(funCode);
                headerDto.setJmCode(jmCode);
                headerDto.setReqTime(reqTime);
                headerDto.setReqTraceNo(reqTraceNo);
                invocation.setHeaderDto(headerDto);
                invocation.setNode(node);
                long abs = Math.abs(reqTime.getTime() - new Date().getTime());
                if (abs>1000*60*5) {//时差必须控制在5分钟以内，否则校验失败
                    throw  new RuntimeException("您的时间错误");

                }

            } catch (RuntimeException e) {
                throw new ArgException(e.getMessage());

            } catch (Exception e) {

                throw new ArgException("Header参数错误，请检查你的参数");
            }



        }
        return invocation.proceed();
    }

    @Override
    public Integer getOrder() {//责任链顺序控制
        return 1;
    }
}
