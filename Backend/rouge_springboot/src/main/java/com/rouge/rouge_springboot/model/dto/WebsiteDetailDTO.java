package com.rouge.rouge_springboot.model.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class WebsiteDetailDTO {
    private String id;
    private String title; //网站标题
    private String url; //网站url
    private String ip;; //服务器IP
    private Integer port; //端口号
    private Boolean isMalicious; //是否是恶意站点
    private String company; //所属企业或个人名称
    private String serviceCode; //服务代码
    private Date createdAt; //创建时间
}
