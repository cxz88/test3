package com.chenxinzhi.service.Impl;

import com.chenxinzhi.my.Invocation;
import com.chenxinzhi.service.EndoscopyService;
import com.chenxinzhi.service.processor.ParameterProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 入口处理方法
 */
@Service
@WebService(targetNamespace = "http://service.chenxinzhi.com/")
public class EndoscopyServiceImpl implements EndoscopyService {
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private List<ParameterProcessor> parameterProcessors;
    @WebMethod
    public String run(@WebParam(name = "xmlArgs",targetNamespace = "http://service.chenxinzhi.com/") String args) {
        String s = asciiToString(args);
        if (parameterProcessors == null||parameterProcessors.size()==0) {
            throw new RuntimeException("获取参数解析器时发生错误");
        }
        List<ParameterProcessor> list = parameterProcessors
                .stream().sorted(Comparator.comparingInt(ParameterProcessor::getOrder)).collect(Collectors.toList());

        //全局异常拦截
        Object proceed = null;
        try {
            Invocation invocation = new Invocation(null, null, new String[]{s}, list);
            proceed = invocation.proceed();
        } catch (Exception ignored) {
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        return (String) proceed;

    }
    /**
     * Ascii转换为字符串
     * @param value
     * @return
     */
    public static String asciiToString(String value)
    {
        StringBuffer sbu = new StringBuffer();
        String[] chars = value.split(",");
        for (int i = 0; i < chars.length; i++) {
            sbu.append((char) Integer.parseInt(chars[i]));
        }
        return sbu.toString();
    }



}
