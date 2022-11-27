package com.chenxinzhi.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//对webservice入参的拦截
@Slf4j
public class WsInInterceptor extends AbstractPhaseInterceptor<SoapMessage> {

    public WsInInterceptor(String phase) {
        super(phase);
    }

    @Override
    public void handleMessage(SoapMessage message) throws Fault {

        try {

            // 从流中获取请求消息体并以字符串形式输出，注意IOUtils是cxf的包；
            String input = IOUtils.toString(message.getContent(InputStream.class), "UTF-8");
            // 如果内容不为空（第一次连接也会被拦截，此时input为空）
            if (StringUtils.isNotBlank(input)) {
                Pattern compile = Pattern.compile("<Request>[\\s\\S]*</Request>");
                Matcher matcher = compile.matcher(input);
                String s = "";
                if (matcher.find()) {
                    s = matcher.group();
                }
                String s1 = stringToAscii(s);
                input = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://service.chenxinzhi.com/\">\n" +
                        "   <soapenv:Header/>\n" +
                        "   <soapenv:Body>\n" +
                        "      <ser:run>\n" +
                        "         <!--Optional:-->\n" +
                        "         <ser:xmlArgs>" + s1 + "</ser:xmlArgs>\n" +
                        "      </ser:run>\n" +
                        "   </soapenv:Body>\n" +
                        "</soapenv:Envelope>";


            }
            // 重新写入
            message.setContent(InputStream.class, new ByteArrayInputStream(input.getBytes()));
        } catch (Exception e) {
            System.out.println(String.format("解析报文异常: %s", e.getMessage()));
        }
    }
    /**
     * 字符串转换为Ascii
     * @param value
     * @return
     */
    public static String stringToAscii(String value)
    {
        StringBuffer sbu = new StringBuffer();
        char[] chars = value.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if(i != chars.length - 1)
            {
                sbu.append((int)chars[i]).append(",");
            }
            else {
                sbu.append((int)chars[i]);
            }
        }
        return sbu.toString();

    }
}
