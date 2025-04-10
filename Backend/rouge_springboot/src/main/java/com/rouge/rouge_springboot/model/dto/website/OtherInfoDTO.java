package com.rouge.rouge_springboot.model.dto.website;

import lombok.Data;

import java.util.List;

@Data
public class OtherInfoDTO {
    @Data
    public static class AreaWebsiteInfoDTO {
        private String provinceName;
        private Integer normalCount;
        private Integer maliciousCount;
    }
    @Data
    public static class YearlyWebsiteInfoDTO {
        private Integer year;
        private Integer totalWebsiteCount;
        private Integer normalWebsiteCount;
        private Integer maliciousWebsiteCount;
    }
    private Integer normalWebsiteCount;
    private Integer normalIpCount;
    private Integer maliciousWebsiteCount;
    private Integer maliciousIpCount;
    private List<YearlyWebsiteInfoDTO> yearlyWebsiteInfo;
    private List<AreaWebsiteInfoDTO> areaWebsiteInfo;
}