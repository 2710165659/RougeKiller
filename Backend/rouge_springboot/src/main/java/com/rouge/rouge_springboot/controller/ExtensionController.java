package com.rouge.rouge_springboot.controller;

import com.rouge.rouge_springboot.model.entity.Tasks;
import com.rouge.rouge_springboot.service.TasksService;
import com.rouge.rouge_springboot.service.WebsiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/extension")
public class ExtensionController {

    @Autowired
    private WebsiteService websiteService;

    @Autowired
    private TasksService tasksService;

    /**
     * 浏览器扩展api:获取所有黑名单url
     *
     * @return 黑名单url列表
     */
    @GetMapping
    public ResponseEntity<List<String>> getMaliciousWebsiteUrls() {
        return ResponseEntity.ok(websiteService.getMaliciousWebsiteUrls());
    }

    @GetMapping("/check/{url}")
    public ResponseEntity<String> addMaliciousWebsiteUrl(@PathVariable("url") String url) {
        Tasks task = new Tasks();
        task.setFullUrl(url);
        task.setCreatedBy(999L); // extension用户
        tasksService.addTask(task);
        return ResponseEntity.ok("ok");
    }
}
