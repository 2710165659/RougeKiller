package com.rouge.rouge_springboot.service;

import com.rouge.rouge_springboot.model.dto.website.WebsiteDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 网站管理服务接口
 */
@Transactional
public interface WebsiteService {
    /**
     * 多条件分页查询网站信息
     * 
     * @param queryDTO 查询条件
     * @return 分页结果
     */
    WebsiteDTO searchWebsites(WebsiteDTO.QueryDTO queryDTO);

    /**
     * 获取所有恶意网站URL
     * 
     * @return 恶意网站URL列表
     */
    List<String> getMaliciousWebsiteUrls();

}