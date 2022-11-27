package com.chenxinzhi.endoscopy.dto;

import lombok.Data;

@Data
public class PacstsbrxxgnjxtDto {
     /*    * 患者门诊或者住院号
     */
    private String hzbm;

    /**
     * 患者姓名
     */
    private String hzxm;

    /**
     * 是否阳性，是或否
     */
    private String yxbs;

    /**
     * 检查医生姓名
     */
    private String jcys;

    /**
     * 内镜卡号
     */
    private String kh;

    /**
     * 手术编码
     */
    private String ssbm;

    /**
     * 手术名称
     */
    private String ssmc;

    /**
     * 医院编码
     */
    private String yybm;

    /**
     * 医院名称
     */
    private String yymc;
}
