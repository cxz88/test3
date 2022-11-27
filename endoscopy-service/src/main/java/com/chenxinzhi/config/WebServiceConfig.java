package com.chenxinzhi.config;

import com.chenxinzhi.interceptor.WsInInterceptor;
import com.chenxinzhi.interceptor.WsOutInterceptor;
import com.chenxinzhi.service.EndoscopyService;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.ext.logging.LoggingInInterceptor;
import org.apache.cxf.ext.logging.LoggingOutInterceptor;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;
import java.util.TimeZone;

/**
 * webService的配置类
 */
@Configuration
@ComponentScan("com.chenxinzhi")
@EnableConfigurationProperties(MyConfig.class)
public class WebServiceConfig {
    @Autowired
    private MyConfig myConfig;
    @Bean
    public LoggingInInterceptor loggingInInterceptor() {
        return new LoggingInInterceptor();
    }

    @Bean
    public LoggingOutInterceptor loggingOutInterceptor() {
        return new LoggingOutInterceptor();
    }
    @Autowired
    private EndoscopyService endoscopyService;
    @Bean(name = "cxfServlet")
    public ServletRegistrationBean cxfServlet() {
        return new ServletRegistrationBean(new CXFServlet(), "/ws/*");
    }
    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        return new SpringBus();
    }
    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(springBus(), endoscopyService);
        endpoint.publish("/service");
//        endpoint.getInInterceptors()
//                .add(loggingInInterceptor());
//        endpoint.getOutInterceptors()
//                .add(loggingOutInterceptor());
        endpoint.getInInterceptors().add(new WsInInterceptor(Phase.RECEIVE));
        WsOutInterceptor wsOutInterceptor = new WsOutInterceptor(Phase.PRE_STREAM);
        wsOutInterceptor.setMyConfig(myConfig);
        endpoint.getOutInterceptors().add(wsOutInterceptor);

        return endpoint;

    }
    @Autowired
    public void setTimeZone(){
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));//将时区设置为上海时区
    }



}
