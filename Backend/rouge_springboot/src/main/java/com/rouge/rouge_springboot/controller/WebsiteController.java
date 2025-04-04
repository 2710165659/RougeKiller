package com.rouge.rouge_springboot.controller;

import com.rouge.rouge_springboot.model.dto.WebsiteDTO;
import com.rouge.rouge_springboot.model.dto.WebsiteQueryDTO;
import com.rouge.rouge_springboot.service.WebsiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
/**
 * 网站信息控制器
 */
@RequestMapping("/websites")
public class WebsiteController {
    @Autowired
    private WebsiteService websiteService;

    /**
     * 多条件查询网站信息
     * @param queryDTO 查询条件
     * @return 分页结果
     */
    @GetMapping("/search")
    public ResponseEntity<WebsiteDTO> searchWebsites(WebsiteQueryDTO queryDTO) {
        return ResponseEntity.ok(websiteService.searchWebsites(queryDTO));
    }
}