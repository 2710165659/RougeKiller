package com.rouge.rouge_springboot.service;

import com.rouge.rouge_springboot.model.entity.Enterprise;

public interface EnterpriseService {
    /**
     * 通过网站id获取企业详情信息
     * 
     * @param id
     * @return
     */
    Enterprise getEnterpriseByWebsiteId(Long id);
}
