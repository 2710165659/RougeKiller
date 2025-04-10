package com.rouge.rouge_springboot.service.impl;

import com.rouge.rouge_springboot.mapper.EnterpriseMapper;
import com.rouge.rouge_springboot.model.entity.Enterprise;
import com.rouge.rouge_springboot.service.EnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnterpriseServiceImpl implements EnterpriseService {
    @Autowired
    EnterpriseMapper enterpriseMapper;
    @Override
    public Enterprise getEnterpriseByWebsiteId(Long id) {
        Enterprise enterprise = enterpriseMapper.getEnterpriseByWebsiteId(id);
        if (enterprise == null) {
            enterprise = new Enterprise();
        }
        return enterprise;
    }
}
