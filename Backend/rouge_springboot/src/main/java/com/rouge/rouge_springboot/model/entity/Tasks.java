package com.rouge.rouge_springboot.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import java.sql.Timestamp;

@Data
public class Tasks {
    /**
     * 任务ID，主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 完整URL，不可为空
     */
    private String fullUrl;

    /**
     * 任务状态，枚举值：待定、进行中、失败、完成，默认值为待定
     */
    private String status;

    /**
     * 成功时返回的网站表ID，可为空
     */
    private Long successId;

    /**
     * 错误信息，可为空
     */
    private String errorMessage;

    /**
     * 创建者ID，不可为空
     */
    private Long createdBy;

    /**
     * 创建时间，默认值为当前时间
     */
    private Timestamp createdAt;

    /**
     * 更新时间，默认值为当前时间，更新时自动更新
     */
    private Timestamp updatedAt;
}
