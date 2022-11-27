package com.chenxinzhi.service.handler;

import com.chenxinzhi.eums.BusinessType;
import com.chenxinzhi.my.Invocation;
import com.chenxinzhi.service.business.WlcsHandler;
import org.springframework.stereotype.Service;

@Service
public class WlcsBodyHandler implements BodyBusinessHandler{
    @Override
    public String getCode() {
        return BusinessType.WLCS.getCode();
    }

    @Override
    public Object handler(Invocation invocation) throws Exception {
        invocation.setMethod(WlcsHandler.class.getMethod("handler", Invocation.class));
        return new Object();
    }
}
