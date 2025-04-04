package com.rouge.rouge_springboot.model.dto;

import lombok.Data;

@Data
public class BaseInfoDTO {
    private Integer websiteCount;
    private Integer ipCount;
    private Integer companyCount;
    private Integer personCount;
}