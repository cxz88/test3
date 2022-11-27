package com.chenxinzhi.service.business;

import com.alibaba.fastjson.JSONObject;
import com.chenxinzhi.config.MyConfig;
import com.chenxinzhi.endoscopy.dto.HeaderDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
@Component
public class Check {
    @Autowired
    private MyConfig myConfig;
    public <T>boolean checkArgs(HeaderDto header,T body) throws IOException {
        String jmCode = header.getJmCode();//取出加密字符串
        header.setJmCode(null);
        Map map1 = JSONObject.parseObject(JSONObject.toJSON(header).toString(), Map.class);//公共参数Header
        Map map2 = JSONObject.parseObject(JSONObject.toJSON(body).toString(), Map.class);//
        HashMap<String, String> map = new HashMap<>();
        map.putAll(map1);
        map.putAll(map2);
        String s = signTopRequest(map);
        System.out.println("加密的sign为："+s);

        return jmCode.equals(s);


    }
    public String signTopRequest(Map<String, String> params) throws IOException {
        System.out.println(params);
        // 第一步：检查参数是否已经排序
        String[] keys = params.keySet().toArray(new String[0]);
        String secret = myConfig.getSecret();
        String signMethod = myConfig.getSignMethod();
        Arrays.sort(keys);

        // 第二步：把所有参数名和参数值串在一起
        StringBuilder query = new StringBuilder();

        if ("MD5".equals(signMethod)) { //签名的摘要算法，可选值为：hmac，md5，hmac-sha256
            query.append(secret);
        }
        for (String key : keys) {

            String value = params.get(key);
            if (StringUtils.isNotEmpty(key)&&StringUtils.isNotEmpty(value)) {
                query.append(key).append(value);
            }
        }

        // 第三步：使用MD5/HMAC加密
        byte[] bytes;
        if ("MD5".equals(signMethod)) {
            bytes = encryptHMAC(query.toString(), secret);
        } else {
            query.append(secret);
            bytes = encryptMD5(query.toString());
        }

        // 第四步：把二进制转化为大写的十六进制(正确签名应该为32大写字符串，此方法需要时使用)
        return byte2hex(bytes);
    }

    public static byte[] encryptHMAC(String data, String secret) throws IOException {
        byte[] bytes;
        try {
            SecretKey secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacMD5");
            Mac mac = Mac.getInstance(secretKey.getAlgorithm());
            mac.init(secretKey);
            bytes = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
        } catch (GeneralSecurityException gse) {
            throw new IOException(gse.toString());
        }
        return bytes;
    }

    public static byte[] encryptMD5(String data) throws IOException {
        return encryptMD5(data.getBytes(StandardCharsets.UTF_8));
    }

    public static String byte2hex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex.toUpperCase());
        }
        return sign.toString();
    }

    /**
     * 对字节流进行MD5摘要。
     */
    public static byte[] encryptMD5(byte[] data) throws IOException {
        byte[] bytes;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            bytes = md.digest(data);
        } catch (GeneralSecurityException gse) {
            throw new IOException(gse.toString());
        }
        return bytes;
    }



}
