package com.rouge.rouge_springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rouge.rouge_springboot.mapper.WebsiteMapper;
import com.rouge.rouge_springboot.model.dto.WebsiteDTO;
import com.rouge.rouge_springboot.model.dto.WebsiteDetailDTO;
import com.rouge.rouge_springboot.model.dto.WebsiteQueryDTO;
import com.rouge.rouge_springboot.model.entity.Website;
import com.rouge.rouge_springboot.service.WebsiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 网站信息服务实现类
 */
@Service
public class WebsiteServiceImpl extends ServiceImpl<WebsiteMapper, Website>  implements WebsiteService {

    @Autowired
    private WebsiteMapper websiteMapper;

    @Override
    public WebsiteDTO searchWebsites(WebsiteQueryDTO queryDTO) {
         // 1. 创建MP分页对象（页码和页数从DTO获取）
        Page<Website> page = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());

        // 2. 调用Mapper方法，MP会自动处理分页逻辑
        IPage<WebsiteDetailDTO> resultPage = baseMapper.selectByCondition(page, queryDTO);

        // 构建返回结果
        WebsiteDTO websiteDTO = new WebsiteDTO();
        websiteDTO.setPageNum(queryDTO.getPageNum());
        websiteDTO.setPageSize(queryDTO.getPageSize());
        websiteDTO.setTotal(resultPage.getTotal());
        websiteDTO.setData(resultPage.getRecords());
        // 统计不同网站数量
        websiteDTO.setNormalCount((int) websiteDTO.getData().stream().filter(dto-> dto.getIsMalicious() != null && !dto.getIsMalicious()).count());
        websiteDTO.setMaliciousCount((int) (websiteDTO.getTotal() - websiteDTO.getNormalCount()));

        return websiteDTO;
    }
}