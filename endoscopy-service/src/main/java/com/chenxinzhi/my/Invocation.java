package com.chenxinzhi.my;

import com.chenxinzhi.common.vo.Response;
import com.chenxinzhi.endoscopy.dto.HeaderDto;
import com.chenxinzhi.service.processor.ParameterProcessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.Data;
import org.aopalliance.intercept.MethodInvocation;
import org.dom4j.Node;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.List;

@Data
public class Invocation implements MethodInvocation {
    private HeaderDto headerDto;
    private Node node;
    private Object body;
    private Object target;  // 调用的源对象
    private Method method; //最终调用的方法
    private final Object[] args; //参数
    List<ParameterProcessor> methodInterceptorList; // 调用链方法的集合
    private int count = 1; // 调用次数

    public Invocation(Object target, Method method, Object[] args, List<ParameterProcessor> methodInterceptorList) {
        this.target = target;
        this.method = method;
        this.args = args;
        this.methodInterceptorList = methodInterceptorList;
    }

    @Override
    public Method getMethod() {
        return method;
    }

    @Override
    public Object[] getArguments() {
        return args;
    }

    @Override
    public Object proceed() throws Throwable { // 调用每一个环绕通知, 调用目标
        try {
            if (count > methodInterceptorList.size()) {
                // 调用目标， 返回并结束递归

                    return method.invoke(target, this);



            }
            // 逐一调用通知, count + 1
            ParameterProcessor parameterProcessor = methodInterceptorList.get(count++ - 1);
            return parameterProcessor.handler(this);
        } catch (Exception e) {
            String message = "";
            if (e instanceof InvocationTargetException) {
                message=e.getCause().getMessage();
            }else {
                message=e.getMessage();
            }
            Response<Object> error = Response.err(getHeaderDto().getFunCode(), message);

            ObjectMapper objectMapper = new XmlMapper().enable(SerializationFeature.INDENT_OUTPUT)
                    .setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

            return objectMapper.writeValueAsString(error);
        }
    }

    @Override
    public Object getThis() {
        return target;
    }

    @Override
    public AccessibleObject getStaticPart() {
        return method;
    }
}