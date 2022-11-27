package com.chenxinzhi.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "my.conf")
@Data
public class MyConfig {
    public Boolean data_enabled = false;
    public String secret = "1234";
    public String signMethod = "MD5";

}
