package com.rouge.rouge_springboot.service.impl;

import com.rouge.rouge_springboot.mapper.InfoMapper;
import com.rouge.rouge_springboot.model.dto.website.BaseInfoDTO;
import com.rouge.rouge_springboot.model.dto.website.OtherInfoDTO;
import com.rouge.rouge_springboot.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class InfoServiceImpl implements InfoService {
    @Autowired
    private InfoMapper infoMapper;

    @Override
    public BaseInfoDTO getBaseInfo() {
        Map<String, Object> result = infoMapper.getBaseInfoCounts();
        BaseInfoDTO dto = new BaseInfoDTO();
        dto.setWebsiteCount(((Number) result.get("websiteCount")).intValue());
        dto.setIpCount(((Number) result.get("ipCount")).intValue());
        dto.setCompanyCount(((Number) result.get("companyCount")).intValue());
        dto.setPersonCount(((Number) result.get("personCount")).intValue());
        return dto;
    }

    @Override
    public OtherInfoDTO getOtherInfo() {
        Map<String, Object> counts = infoMapper.getOtherInfoCounts();
        List<Map<String, Object>> yearlyData = infoMapper.getWebsiteCountsByYear();
        List<Map<String, Object>> areaData = infoMapper.getWebsiteCountsByProvince();

        OtherInfoDTO dto = new OtherInfoDTO();
        dto.setNormalWebsiteCount(((Number) counts.get("normalWebsiteCount")).intValue());
        dto.setNormalIpCount(((Number) counts.get("normalIpCount")).intValue());
        dto.setMaliciousWebsiteCount(((Number) counts.get("maliciousWebsiteCount")).intValue());
        dto.setMaliciousIpCount(((Number) counts.get("maliciousIpCount")).intValue());

        List<OtherInfoDTO.YearlyWebsiteInfoDTO> yearlyInfo = yearlyData.stream().map(data -> {
            OtherInfoDTO.YearlyWebsiteInfoDTO yearlyDto = new OtherInfoDTO.YearlyWebsiteInfoDTO();
            yearlyDto.setYear(((Number) data.get("year")).intValue());
            yearlyDto.setTotalWebsiteCount(((Number) data.get("totalWebsiteCount")).intValue());
            yearlyDto.setNormalWebsiteCount(((Number) data.get("normalWebsiteCount")).intValue());
            yearlyDto.setMaliciousWebsiteCount(((Number) data.get("maliciousWebsiteCount")).intValue());
            return yearlyDto;
        }).collect(Collectors.toList());
        dto.setYearlyWebsiteInfo(yearlyInfo);

        List<OtherInfoDTO.AreaWebsiteInfoDTO> areaInfo = areaData.stream().map(data -> {
            OtherInfoDTO.AreaWebsiteInfoDTO areaDto = new OtherInfoDTO.AreaWebsiteInfoDTO();
            areaDto.setProvinceName((String) data.get("province_name"));
            areaDto.setNormalCount(((Number) data.get("normal_count")).intValue());
            areaDto.setMaliciousCount(((Number) data.get("malicious_count")).intValue());
            return areaDto;
        }).collect(Collectors.toList());
        dto.setAreaWebsiteInfo(areaInfo);
        return dto;
    }
}