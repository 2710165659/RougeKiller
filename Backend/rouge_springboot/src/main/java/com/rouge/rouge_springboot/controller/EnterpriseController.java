package com.rouge.rouge_springboot.controller;

import com.rouge.rouge_springboot.model.entity.Enterprise;
import com.rouge.rouge_springboot.service.EnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enterprise")
public class EnterpriseController {
    @Autowired
    EnterpriseService enterpriseService;

    /**
     * 通过网站id查询企业信息
     * @param id 网站id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<Enterprise> getEnterpriseByWebsiteId(@PathVariable Long id) {
        return ResponseEntity.ok(enterpriseService.getEnterpriseByWebsiteId(id));
    }

}
