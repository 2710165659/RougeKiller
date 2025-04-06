package com.rouge.rouge_springboot.controller;

import com.rouge.rouge_springboot.model.dto.BaseInfoDTO;
import com.rouge.rouge_springboot.model.dto.OtherInfoDTO;
import com.rouge.rouge_springboot.model.dto.WebsiteDTO;
import com.rouge.rouge_springboot.model.dto.WebsiteQueryDTO;
import com.rouge.rouge_springboot.service.InfoService;
import com.rouge.rouge_springboot.service.WebsiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
/**
 * 网站信息控制器
 */
@RequestMapping("/websites")
public class WebsiteController {

    @Autowired
    private WebsiteService websiteService;

    @Autowired
    private InfoService infoService;

    /**
     * 多条件查询网站信息
     * @param queryDTO 查询条件
     * @return 分页结果
     */
    @GetMapping("/search")
    public ResponseEntity<WebsiteDTO> searchWebsites(WebsiteQueryDTO queryDTO) {
        return ResponseEntity.ok(websiteService.searchWebsites(queryDTO));
    }

    /**
     * 获取所有黑名单url
     * @return 黑名单url列表
     */
    @GetMapping
    public ResponseEntity<List<String>> getMaliciousWebsiteUrls() {
        return ResponseEntity.ok(websiteService.getMaliciousWebsiteUrls());
    }

    /**
     * 获取基础统计信息
     * @return 包含基础统计信息的响应实体
     */
    @GetMapping("/base")
    public ResponseEntity<BaseInfoDTO> getBaseInfo() {
        return ResponseEntity.ok(infoService.getBaseInfo());
    }

    /**
     * 获取其他统计信息
     * @return 包含其他统计信息的响应实体
     */
    @GetMapping("/other")
    public ResponseEntity<OtherInfoDTO> getOtherInfo() {
        return ResponseEntity.ok(infoService.getOtherInfo());
    }

}