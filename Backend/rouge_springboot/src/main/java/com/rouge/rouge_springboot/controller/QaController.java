package com.rouge.rouge_springboot.controller;

import com.rouge.rouge_springboot.service.QaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/qa")
public class QaController {
    @Autowired
    private QaService qaService;

    @GetMapping("/stream")
    public SseEmitter streamChat(@RequestParam String content) {
        Map<String, String> params = new HashMap<>();
        params.put("content", content);
        return qaService.streamChatCompletion(params);
    }
}
