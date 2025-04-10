package com.rouge.rouge_springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rouge.rouge_springboot.mapper.WebsiteMapper;
import com.rouge.rouge_springboot.model.dto.website.WebsiteDTO;
import com.rouge.rouge_springboot.model.entity.Website;
import com.rouge.rouge_springboot.service.WebsiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 网站信息服务实现类
 */
@Service
public class WebsiteServiceImpl extends ServiceImpl<WebsiteMapper, Website>  implements WebsiteService {

    @Autowired
    private WebsiteMapper websiteMapper;

    @Override
    public WebsiteDTO searchWebsites(WebsiteDTO.QueryDTO queryDTO) {
         // 1. 创建MP分页对象（页码和页数从DTO获取）
        Page<Website> page = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());

        // 2. 调用Mapper方法，MP会自动处理分页逻辑
        IPage<WebsiteDTO.WebsiteDetailDTO> resultPage = baseMapper.selectByCondition(page, queryDTO);

        // 构建返回结果
        WebsiteDTO websiteDTO = new WebsiteDTO();
        websiteDTO.setPageNum(queryDTO.getPageNum());
        websiteDTO.setPageSize(queryDTO.getPageSize());
        websiteDTO.setTotal(resultPage.getTotal());
        websiteDTO.setData(resultPage.getRecords());
        // 统计不同网站数量
        List<Map<String, Object>> countByCondition = baseMapper.getCountByCondition(queryDTO);
        Map<String, Object> countMap = countByCondition.isEmpty() ? new HashMap<>() : countByCondition.get(0);
        if(countMap == null) {
            websiteDTO.setNormalCount(0);
            websiteDTO.setMaliciousCount(0);
        } else {
            websiteDTO.setNormalCount(countMap.get("normalCount") == null ? 0 : ((BigDecimal) countMap.get("normalCount")).intValue());
            websiteDTO.setMaliciousCount(countMap.get("maliciousCount") == null ? 0 : ((BigDecimal) countMap.get("maliciousCount")).intValue());
        }
        return websiteDTO;
    }

    @Override
    public List<String> getMaliciousWebsiteUrls() {
        LambdaQueryWrapper<Website> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Website::getIsMalicious, true).select(Website::getFullUrl);
        return websiteMapper.selectObjs(queryWrapper).stream().map(obj -> (String) obj).collect(Collectors.toList());
    }
}