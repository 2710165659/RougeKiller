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

    @Select("SELECT \n" +
            "    YEAR(icp_entities.approval_date) AS year,\n" +
            "    COUNT(websites.id) AS totalWebsiteCount,\n" +
            "    COUNT(CASE WHEN websites.is_malicious = false THEN 1 END) AS normalWebsiteCount,\n" +
            "    COUNT(CASE WHEN websites.is_malicious = true THEN 1 END) AS maliciousWebsiteCount\n" +
            "FROM websites\n" +
            "JOIN icp_services ON icp_services.website_id = websites.id\n" +
            "JOIN icp_entities ON icp_services.entity_id = icp_entities.id\n" +
            "GROUP BY YEAR(icp_entities.approval_date)")
    List<Map<String, Object>> getWebsiteCountsByYear();

    @Select("SELECT \n" +
            "    CASE LEFT(icp_services.service_code, 1)\n" +
            "        WHEN '京' THEN '北京'\n" +
            "        WHEN '津' THEN '天津'\n" +
            "        WHEN '冀' THEN '河北'\n" +
            "        WHEN '晋' THEN '山西'\n" +
            "        WHEN '蒙' THEN '内蒙古'\n" +
            "        WHEN '辽' THEN '辽宁'\n" +
            "        WHEN '吉' THEN '吉林'\n" +
            "        WHEN '黑' THEN '黑龙江'\n" +
            "        WHEN '沪' THEN '上海'\n" +
            "        WHEN '苏' THEN '江苏'\n" +
            "        WHEN '浙' THEN '浙江'\n" +
            "        WHEN '皖' THEN '安徽'\n" +
            "        WHEN '闽' THEN '福建'\n" +
            "        WHEN '赣' THEN '江西'\n" +
            "        WHEN '鲁' THEN '山东'\n" +
            "        WHEN '豫' THEN '河南'\n" +
            "        WHEN '鄂' THEN '湖北'\n" +
            "        WHEN '湘' THEN '湖南'\n" +
            "        WHEN '粤' THEN '广东'\n" +
            "        WHEN '桂' THEN '广西'\n" +
            "        WHEN '琼' THEN '海南'\n" +
            "        WHEN '渝' THEN '重庆'\n" +
            "        WHEN '川' THEN '四川'\n" +
            "        WHEN '贵' THEN '贵州'\n" +
            "        WHEN '云' THEN '云南'\n" +
            "        WHEN '藏' THEN '西藏'\n" +
            "        WHEN '陕' THEN '陕西'\n" +
            "        WHEN '甘' THEN '甘肃'\n" +
            "        WHEN '青' THEN '青海'\n" +
            "        WHEN '宁' THEN '宁夏'\n" +
            "        WHEN '新' THEN '新疆'\n" +
            "        WHEN '港' THEN '香港'\n" +
            "        WHEN '澳' THEN '澳门'\n" +
            "        WHEN '台' THEN '台湾'\n" +
            "        ELSE '其他'\n" +
            "    END AS province_name,\n" +
            "    SUM(CASE WHEN websites.is_malicious = '0' THEN 1 ELSE 0 END) AS normal_count,\n" +
            "    SUM(CASE WHEN websites.is_malicious = '1' THEN 1 ELSE 0 END) AS malicious_count\n" +
            "FROM \n" +
            "    websites\n" +
            "JOIN \n" +
            "    icp_services ON icp_services.website_id = websites.id\n" +
            "GROUP BY \n" +
            "    province_name\n" +
            "ORDER BY \n" +
            "    malicious_count  DESC;")
    List<Map<String, Object>> getWebsiteCountsByProvince();
}