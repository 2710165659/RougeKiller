package com.rouge.rouge_springboot.model.dto.website;

import lombok.Data;

import java.sql.Date;
import java.util.List;

/**
 * 网站信息DTO
 */
@Data
public class WebsiteDTO {
  @Data
  public static class QueryDTO {
    private String title; // 标题
    private String url; // 网站域名
    private String ip; // 网站IP
    private String company; // 公司名称
    private Integer pageNum = 1; // 页码
    private Integer pageSize = 10; // 每页数量
  }

  @Data
  public static class WebsiteDetailDTO {
    private String id;
    private String title; // 网站标题
    private String url; // 网站url
    private String ip;; // 服务器IP
    private Integer port; // 端口号
    private Boolean isMalicious; // 是否是恶意站点
    private String company; // 所属企业或个人名称
    private String serviceCode; // 服务代码
    private Date createdAt; // 创建时间
  }

  private Integer pageNum; // 当前页码
  private Integer pageSize; // 每页数量
  private Long total; // 总网站数
  private Integer maliciousCount; // 恶意网站数量
  private Integer normalCount; // 正常网站数量
  private List<WebsiteDetailDTO> data; // 详情分页数据
}