package com.rouge.rouge_springboot.model.dto;

import lombok.Data;

@Data
public class AreaWebsiteInfoDTO {
    private String provinceName;
    private Integer normalCount;
    private Integer maliciousCount;
}
