package com.chenxinzhi.endoscopy.vo;

import com.chenxinzhi.endoscopy.pojo.Zljsk;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


@Data
public class ZljskVo implements Serializable {
        @JacksonXmlProperty(localName = "aljsk")
        @JacksonXmlElementWrapper(localName = "zljskList")
        private List<Zljsk> aljsk;



}
