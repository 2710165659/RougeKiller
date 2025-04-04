package com.rouge.rouge_springboot.controller;

import com.rouge.rouge_springboot.model.dto.BaseInfoDTO;
import com.rouge.rouge_springboot.model.dto.OtherInfoDTO;
import com.rouge.rouge_springboot.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 信息统计控制器
 */
@RestController
@RequestMapping("/infos")
public class InfoController {
    @Autowired
    private InfoService infoService;

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