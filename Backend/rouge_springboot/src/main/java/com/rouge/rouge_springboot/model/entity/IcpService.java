package com.rouge.rouge_springboot.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

/**
 * ICP备案服务信息实体类
 */
@Data
@TableName("icp_services")
public class IcpService {
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 关联的ICP主体ID
     */
    private Long entityId;

    /**
     * 服务备案号
     */
    private String serviceCode;

    /**
     * 关联的网站ID
     */
    private Long websiteId;

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