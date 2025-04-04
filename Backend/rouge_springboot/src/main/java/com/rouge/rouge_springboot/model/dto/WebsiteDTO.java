package com.rouge.rouge_springboot.model.dto;

import lombok.Data;

import java.util.List;

/**
 * 网站信息DTO
 */
@Data
public class WebsiteDTO {
    /*
      查询结果统计信息
     */
    private Integer pageNum; //当前页码
    private Integer pageSize; //每页数量
    private Long total; //总网站数
    private Integer maliciousCount; //恶意网站数量
    private Integer normalCount; //正常网站数量
    private List<WebsiteDetailDTO> data; //详情分页数据
}