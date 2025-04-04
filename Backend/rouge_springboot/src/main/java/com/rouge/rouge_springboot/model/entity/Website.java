package com.rouge.rouge_springboot.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("websites")
public class Website {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String domain;

    private String subdomain;

    private String fullUrl;

    private Integer port;

    private String title;

    private String ip;

    private Boolean isMalicious;

    @TableField(fill = FieldFill.INSERT)
    private java.sql.Timestamp createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private java.sql.Timestamp updatedAt;
}