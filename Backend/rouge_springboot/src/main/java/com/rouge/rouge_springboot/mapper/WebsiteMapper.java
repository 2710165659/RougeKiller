package com.rouge.rouge_springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rouge.rouge_springboot.model.dto.WebsiteDetailDTO;
import com.rouge.rouge_springboot.model.dto.WebsiteQueryDTO;
import com.rouge.rouge_springboot.model.entity.Website;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 网站信息Mapper接口
 */
@Mapper
public interface WebsiteMapper extends BaseMapper<Website> {

    /**
     * 多表分页查询网站信息
     * @return 分页结果
     */
    @Select("<script>" +
            "SELECT " +
            "   w.title, w.full_url, w.ip, w.port, w.is_malicious, " +
            "   e.entity_name AS company, s.service_code ,s.created_at " +
            "FROM websites w " +
            "JOIN icp_services s ON s.website_id = w.id " +
            "JOIN icp_entities e ON s.entity_id = e.id " +
            "<where>" +
            "   <if test='query.url != null'> AND w.full_url LIKE CONCAT('%', #{query.url}, '%') </if>" +
            "   <if test='query.ip != null'> AND w.ip = #{query.ip} </if>" +
            "   <if test='query.company != null'> AND e.entity_name LIKE CONCAT('%', #{query.company}, '%') </if>" +
            "</where>" +
            "</script>")
    IPage<WebsiteDetailDTO> selectByCondition(IPage<Website> page, @Param("query") WebsiteQueryDTO query);
    // @Select("SELECT \n" +
    //         "\t\twebsites.title, \n" +
    //         "    websites.full_url, \n" +
    //         "    websites.ip, \n" +
    //         "    websites.`port`,\n" +
    //         "    websites.is_malicious, \n" +
    //         "    icp_entities.entity_name,\n" +
    //         "\t\ticp_services.service_code,\n" +
    //         "\t\ticp_services.created_at\n" +
    //         "\t\t\n" +
    //         "FROM websites\n" +
    //         "JOIN icp_services ON icp_services.website_id = websites.id\n" +
    //         "JOIN icp_entities ON icp_services.entity_id = icp_entities.id"
    // )
    // Page<WebsiteDetailDTO> searchWebsite(@Param("queryWrapper") WebsiteQueryDTO queryDTO);

}