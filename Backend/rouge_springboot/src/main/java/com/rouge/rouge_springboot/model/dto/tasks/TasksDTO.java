package com.rouge.rouge_springboot.model.dto.tasks;

import lombok.Data;
import java.sql.Timestamp;

public class TasksDTO {
    @Data
    public static class TasksQueryDTO {
        private String url;
        private String status;
        private Integer page;
        private Integer size;
    }


    @Data
    public static class TasksResponseDTO {
        private Long id;
        private String fullUrl;
        private String status;
        private Long successId;
        private String errorMessage;
        private Long createdBy;
        private Timestamp createdAt;
        private Timestamp updatedAt;
        private String name; //关联user表的name字段
    }

}
