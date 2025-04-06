package com.rouge.rouge_springboot.model.dto;

import lombok.Data;

/**
 * 网站查询条件DTO
 */
@Data
public class WebsiteQueryDTO {
    private String title; //标题
    private String url; //网站域名
    private String ip; //网站IP
    private String company; //公司名称
    private Integer pageNum = 1; //页码
    private Integer pageSize = 10; //每页数量
}