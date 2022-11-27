package com.chenxinzhi.eums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BusinessType {
    //获取诊疗间刷卡内镜
    HQZLJSKNJ("010202"),
    //PACS推送病人信息给内镜系统
    PACSTSBRXXGNJXT("010203"),
    //网络测试
    WLCS("010200")

    ;
    private final String code;

}
