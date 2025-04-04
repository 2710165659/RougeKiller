package com.rouge.rouge_springboot.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface InfoMapper {
    @Select("SELECT " +
            "(SELECT COUNT(*) FROM websites) AS websiteCount, " +
            "(SELECT COUNT(DISTINCT ip) FROM websites) AS ipCount, " +
            "(SELECT COUNT(*) FROM enterprises) AS companyCount, " +
            "(SELECT COUNT(DISTINCT legal_representative) FROM enterprises) AS personCount")
    Map<String, Object> getBaseInfoCounts();

    @Select("SELECT " +
            "(SELECT COUNT(*) FROM websites WHERE is_malicious = false) AS normalWebsiteCount, " +
            "(SELECT COUNT(DISTINCT ip) FROM websites WHERE is_malicious = false) AS normalIpCount, " +
            "(SELECT COUNT(*) FROM websites WHERE is_malicious = true) AS maliciousWebsiteCount, " +
            "(SELECT COUNT(DISTINCT ip) FROM websites WHERE is_malicious = true) AS maliciousIpCount")
    Map<String, Object> getOtherInfoCounts();

    @Select("SELECT YEAR(created_at) AS year, " +
            "COUNT(*) AS totalWebsiteCount, " +
            "COUNT(CASE WHEN is_malicious = false THEN 1 END) AS normalWebsiteCount, " +
            "COUNT(CASE WHEN is_malicious = true THEN 1 END) AS maliciousWebsiteCount " +
            "FROM websites " +
            "GROUP BY YEAR(created_at)")
    List<Map<String, Object>> getWebsiteCountsByYear();
}