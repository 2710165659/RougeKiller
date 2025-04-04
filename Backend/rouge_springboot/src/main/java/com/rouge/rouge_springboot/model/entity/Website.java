package com.rouge.rouge_springboot.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

/**
 * 网站信息实体类
 */
@Data
@TableName("websites")
public class Website {
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 域名
     */
    private String domain;

    /**
     * 子域名
     */
    private String subdomain;

    /**
     * 完整URL
     */
    private String fullUrl;

    /**
     * 端口号
     */
    private Integer port;

    /**
     * 网站标题
     */
    private String title;

    /**
     * 服务器IP
     */
    private String ip;

    /**
     * 是否是恶意站点
     */
    private Boolean isMalicious;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private java.sql.Timestamp createdAt;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private java.sql.Timestamp updatedAt;
}