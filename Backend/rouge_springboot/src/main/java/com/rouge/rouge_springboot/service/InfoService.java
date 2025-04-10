package com.rouge.rouge_springboot.service;

import com.rouge.rouge_springboot.model.dto.website.BaseInfoDTO;
import com.rouge.rouge_springboot.model.dto.website.OtherInfoDTO;
import org.springframework.transaction.annotation.Transactional;

/**
 * 信息统计服务接口
 */
@Transactional
public interface InfoService {
    /**
     * 获取基础统计信息
     * 
     * @return 基础统计信息DTO
     */
    BaseInfoDTO getBaseInfo();

    /**
     * 获取其他统计信息
     * 
     * @return 其他统计信息DTO
     */
    OtherInfoDTO getOtherInfo();
}