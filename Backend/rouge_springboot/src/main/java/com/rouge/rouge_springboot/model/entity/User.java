package com.rouge.rouge_springboot.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

/**
 * 用户信息实体类
 */
@Data
@TableName("users")
public class User {
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    private String name;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 加盐密码
     */
    private String saltedPassword;

    /**
     * 用户角色
     */
    private String role;

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