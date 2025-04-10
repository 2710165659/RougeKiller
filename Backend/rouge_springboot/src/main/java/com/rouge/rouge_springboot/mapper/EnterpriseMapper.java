package com.rouge.rouge_springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rouge.rouge_springboot.model.entity.Enterprise;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EnterpriseMapper extends BaseMapper<Enterprise> {
    /**
     * 根据网站id获取企业信息
     * 
     * @param websiteId
     * @return
     */
    @Select("SELECT enterprises.*\n" +
            "FROM enterprises\n" +
            "JOIN icp_services ON enterprises.entity_id = icp_services.entity_id\n" +
            "JOIN websites ON websites.id = icp_services.website_id\n" +
            "WHERE website_id = #{websiteId}\n " +
            "LIMIT 1")
    Enterprise getEnterpriseByWebsiteId(@Param("websiteId") Long websiteId);
}