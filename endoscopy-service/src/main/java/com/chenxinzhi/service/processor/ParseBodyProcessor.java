package com.chenxinzhi.service.processor;

import com.chenxinzhi.exception.ArgException;
import com.chenxinzhi.my.Invocation;
import com.chenxinzhi.service.handler.BodyBusinessHandler;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//解析body参数
@Service
@Data
public class ParseBodyProcessor implements ParameterProcessor{
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private List<BodyBusinessHandler> bodyBusinessHandlers;
    private Map<String,BodyBusinessHandler> cache = new HashMap<>();

    @Override
    public Object handler(Invocation invocation) throws Throwable {
        //根据header中的参数去进行匹配
        String funCode = invocation.getHeaderDto().getFunCode();
        Object handler;
        try {
            //优先从缓存中获取，加快速度
            BodyBusinessHandler bodyBusinessHandler = cache.get(funCode);
            if (bodyBusinessHandler == null) {
                if (bodyBusinessHandlers==null||bodyBusinessHandlers.size()==0) {
                    throw new RuntimeException("获取不到body解析器");
                }
                for (BodyBusinessHandler bodyBusinessHandler_ : bodyBusinessHandlers) {
                    if (bodyBusinessHandler_.support(funCode)) {
                        cache.put(funCode,bodyBusinessHandler_);
                        bodyBusinessHandler=bodyBusinessHandler_;
                        break;
                    }
                }
                if (bodyBusinessHandler == null) {
                    throw new RuntimeException("无法获取对应Body处理器");
                }
            }
            handler = bodyBusinessHandler.handler(invocation);

            invocation.setTarget(bodyBusinessHandler);
        } catch (Exception e) {
            throw new ArgException("Body参数错误，请检查你的参数");
        }
        invocation.setBody(handler);


        return invocation.proceed();
    }

    @Override
    public Integer getOrder() {//责任链顺序控制
        return 2;
    }
}
