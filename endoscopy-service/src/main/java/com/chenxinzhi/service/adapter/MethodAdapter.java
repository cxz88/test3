package com.chenxinzhi.service.adapter;

import com.chenxinzhi.my.Invocation;
import com.chenxinzhi.service.business.BusinessHandler;
import com.chenxinzhi.service.processor.ParameterProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//方式调用适配器，用于调用方法
@Service
public class MethodAdapter implements ParameterProcessor {
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private List<BusinessHandler> businessHandlers;
    private Map<String,BusinessHandler> cache= new HashMap<>();
    @Override
    public Object handler(Invocation invocation) throws Throwable {
        String funCode = invocation.getHeaderDto().getFunCode();
        try {

            BusinessHandler businessHandler = cache.get(funCode);
            if (businessHandler == null) {
                if (businessHandlers==null||businessHandlers.size()==0) {
                    throw new RuntimeException();
                }
                for (BusinessHandler businessHandler_ : businessHandlers) {
                    if (businessHandler_.support(funCode)) {
                        businessHandler = businessHandler_;
                        cache.put(funCode,businessHandler_);
                        break;


                    }
                }
                if (businessHandler == null) {
                    throw new RuntimeException("无法获取对应的方法处理器");
                }

            }
            invocation.setTarget(businessHandler);

        } catch (Exception e) {
            throw new RuntimeException("获取方法处理器时发生错误");
        }
        return invocation.proceed();
    }

    @Override
    public Integer getOrder() {
        return 3;
    }
}
