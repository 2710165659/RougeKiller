package com.rouge.rouge_springboot.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 企业信息实体类
 */
@Data
@TableName("enterprises")
public class Enterprise {
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 统一社会信用代码
     */
    private String socialCreditCode;

    /**
     * 企业名称
     */
    private String companyName;

    /**
     * 法定代表人
     */
    private String legalRepresentative;

    /**
     * 成立日期
     */
    private java.sql.Date establishmentDate;

    /**
     * 核准日期
     */
    private java.sql.Date approvalDate;

    /**
     * 登记状态
     */
    private String registrationStatus;

    /**
     * 注册号
     */
    private String registrationNumber;

    /**
     * 企业类型
     */
    private String companyType;

    /**
     * 注册资本(万元)
     */
    private BigDecimal registeredCapital;

    /**
     * 登记机关
     */
    private String registrationAuthority;

    /**
     * 企业地址
     */
    private String address;

    /**
     * 经营范围
     */
    private String businessScope;

    /**
     * 营业期限开始日期
     */
    private java.sql.Date businessTermStart;

    /**
     * 营业期限结束日期
     */
    private java.sql.Date businessTermEnd;

    /**
     * 关联的ICP主体ID
     */
    private Long entityId;

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