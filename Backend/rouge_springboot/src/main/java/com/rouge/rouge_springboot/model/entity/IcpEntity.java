package com.rouge.rouge_springboot.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

/**
 * ICP备案主体实体类
 */
@Data
@TableName("icp_entities")
public class IcpEntity {
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * ICP备案/许可证号
     */
    private String icpCode;

    /**
     * 审核通过日期
     */
    private java.sql.Date approvalDate;

    /**
     * 主办单位名称
     */
    private String entityName;

    /**
     * 主办单位性质
     */
    private String entityType;

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