package com.rouge.rouge_springboot.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class OtherInfoDTO {
    private Integer normalWebsiteCount;
    private Integer normalIpCount;
    private Integer maliciousWebsiteCount;
    private Integer maliciousIpCount;
    private List<YearlyWebsiteInfoDTO> yearlyWebsiteInfo;
}