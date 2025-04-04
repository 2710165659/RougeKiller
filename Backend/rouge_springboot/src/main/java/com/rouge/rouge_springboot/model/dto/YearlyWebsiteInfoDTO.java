package com.rouge.rouge_springboot.model.dto;

import lombok.Data;

@Data
public class YearlyWebsiteInfoDTO {
    private Integer year;
    private Integer totalWebsiteCount;
    private Integer normalWebsiteCount;
    private Integer maliciousWebsiteCount;
}