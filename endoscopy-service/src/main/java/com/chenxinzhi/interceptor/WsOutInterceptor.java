package com.chenxinzhi.interceptor;

import com.chenxinzhi.config.MyConfig;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.phase.AbstractPhaseInterceptor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//对webservice的响应格式数据的拦截

public class WsOutInterceptor extends AbstractPhaseInterceptor<SoapMessage> {

    private MyConfig myConfig;

    public MyConfig getMyConfig() {
        return myConfig;
    }

    public void setMyConfig(MyConfig myConfig) {
        this.myConfig = myConfig;
    }

    public WsOutInterceptor(String phase) {
        super(phase);
    }

    @Override
    public void handleMessage(SoapMessage message) throws Fault {
        try {
            // 从流中获取返回内容
            OutputStream os = message.getContent(OutputStream.class);
            CachedStream cs = new CachedStream();
            message.setContent(OutputStream.class, cs);
            message.getInterceptorChain().doIntercept(message);
            CachedOutputStream cachedOutputStream = (CachedOutputStream) message.getContent(OutputStream.class);
            InputStream in = cachedOutputStream.getInputStream();
            String output = IOUtils.toString(in, "UTF-8");
            // 修改内容为集成平台要求的格式
            if (getMyConfig().getData_enabled()) {
                Pattern compile = Pattern.compile("&lt;Response&gt;[\\s\\S]*&lt;/Response&gt;");
                Matcher matcher = compile.matcher(output);
                if (matcher.find()) {
                    String group = matcher.group();
                    String s = group.replaceAll("&lt;", "<").replaceAll("&gt;", ">");
                    output = output.replaceAll("&lt;Response&gt;[\\s\\S]*&lt;/Response&gt;", s);
                }
            }
            // 处理完后写回流中
            IOUtils.copy(new ByteArrayInputStream(output.getBytes()), os);
            cs.close();
            os.flush();
            message.setContent(OutputStream.class, os);
        } catch (Exception e) {
            System.out.println(String.format("解析报文异常: %s", e.getMessage()));
        }
    }

    private static class CachedStream extends CachedOutputStream {
        public CachedStream() {
            super();
        }

        @Override
        protected void doFlush() throws IOException {
            currentStream.flush();
        }

        @Override
        protected void doClose() throws IOException {
        }

        @Override
        protected void onWrite() throws IOException {
        }
    }
}
