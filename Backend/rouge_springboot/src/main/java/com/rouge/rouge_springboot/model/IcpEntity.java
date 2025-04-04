package com.rouge.rouge_springboot.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("icp_entities")
public class IcpEntity {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String icpCode;

    private java.sql.Date approvalDate;

    private String entityName;

    private String entityType;

    @TableField(fill = FieldFill.INSERT)
    private java.sql.Timestamp createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private java.sql.Timestamp updatedAt;
}