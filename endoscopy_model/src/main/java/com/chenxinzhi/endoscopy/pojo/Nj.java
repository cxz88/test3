package com.chenxinzhi.endoscopy.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author chenxinzhi
 * @since 2022-11-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_nj")
public class Nj implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 患者门诊或者住院号
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

    private String id;


}
