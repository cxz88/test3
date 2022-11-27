package com.chenxinzhi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.chenxinzhi")
@MapperScan("com.chenxinzhi.mapper")
public class EndoscopyApplication {
    public static void main(String[] args) {
        SpringApplication.run(EndoscopyApplication.class,args);
    }
}
