package com.chenxinzhi.endoscopy.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 诊疗间
 * </p>
 *
 * @author chenxinzhi
 * @since 2022-11-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_zljsk")
public class Zljsk implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 创建时间
     */
    private Date cjsj;

    /**
     * 读卡器IP
     */
    private String dkqip;

    /**
     * 读卡器序列号
     */
    private String dkqxlh;

    /**
     * 主键
     */
    private String id;

    /**
     * 内镜编号
     */
    private String rfidbh;

    /**
     * 内镜名称
     */
    private String rfidmc;

    /**
     * 内镜卡号
     */
    private String rfidxlh;

    /**
     * 诊疗间IP
     */
    private String zjlip;


}
