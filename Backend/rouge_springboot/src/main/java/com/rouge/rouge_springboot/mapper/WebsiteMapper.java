package com.rouge.rouge_springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rouge.rouge_springboot.model.dto.website.WebsiteDTO;
import com.rouge.rouge_springboot.model.entity.Website;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 网站信息Mapper接口
 */
@Mapper
public interface WebsiteMapper extends BaseMapper<Website> {

        /**
         * 多表分页查询网站信息
         * 
         * @return 分页结果
         */
        @Select("<script>" +
                        "SELECT " +
                        "   w.title, w.full_url AS url, w.ip, w.port, w.is_malicious, w.id," +
                        "   e.entity_name AS company, s.service_code ,s.created_at " +
                        "FROM websites w " +
                        "JOIN icp_services s ON s.website_id = w.id " +
                        "JOIN icp_entities e ON s.entity_id = e.id " +
                        "<where>" +
                        "   <if test='query.title != null'> AND w.title LIKE CONCAT('%', #{query.title}, '%') </if>" +
                        "   <if test='query.url != null'> AND w.full_url LIKE CONCAT('%', #{query.url}, '%') </if>" +
                        "   <if test='query.ip != null and query.ip != \"\"'> AND w.ip = #{query.ip} </if>" +
                        "   <if test='query.company != null'> AND e.entity_name LIKE CONCAT('%', #{query.company}, '%') </if>"
                        +
                        "</where>" + " ORDER BY w.created_at DESC " +
                        "</script>")
        IPage<WebsiteDTO.WebsiteDetailDTO> selectByCondition(IPage<Website> page, @Param("query") WebsiteDTO.QueryDTO query);

        @Select("<script>" +
                        "SELECT " +
                        "   SUM(CASE WHEN w.is_malicious = '0' THEN 1 ELSE 0 END) AS normalCount, " +
                        "   SUM(CASE WHEN w.is_malicious = '1' THEN 1 ELSE 0 END) AS maliciousCount " +
                        "FROM websites w " +
                        "JOIN icp_services s ON s.website_id = w.id " +
                        "JOIN icp_entities e ON s.entity_id = e.id " +
                        "<where>" +
                        "   <if test='query.title != null'> AND w.title LIKE CONCAT('%', #{query.title}, '%') </if>" +
                        "   <if test='query.url != null'> AND w.full_url LIKE CONCAT('%', #{query.url}, '%') </if>" +
                        "   <if test='query.ip != null and query.ip != \"\"'> AND w.ip = #{query.ip} </if>" +
                        "   <if test='query.company != null'> AND e.entity_name LIKE CONCAT('%', #{query.company}, '%') </if>"
                        +
                        "</where>" +
                        "</script>")
        List<Map<String, Object>> getCountByCondition(@Param("query") WebsiteDTO.QueryDTO query);

}