package com.rouge.rouge_springboot.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("enterprises")
public class Enterprise {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String socialCreditCode;

    private String companyName;

    private String legalRepresentative;

    private java.sql.Date establishmentDate;

    private java.sql.Date approvalDate;

    private String registrationStatus;

    private String registrationNumber;

    private String companyType;

    private BigDecimal registeredCapital;

    private String registrationAuthority;

    private String address;

    private String businessScope;

    private java.sql.Date businessTermStart;

    private java.sql.Date businessTermEnd;

    private Long entityId;

    @TableField(fill = FieldFill.INSERT)
    private java.sql.Timestamp createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private java.sql.Timestamp updatedAt;
}