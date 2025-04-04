package com.rouge.rouge_springboot.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("icp_services")
public class IcpService {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long entityId;

    private String serviceCode;

    private Long websiteId;

    @TableField(fill = FieldFill.INSERT)
    private java.sql.Timestamp createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private java.sql.Timestamp updatedAt;
}