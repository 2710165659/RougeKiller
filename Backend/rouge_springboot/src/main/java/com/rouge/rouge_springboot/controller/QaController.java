package com.rouge.rouge_springboot.controller;

import com.rouge.rouge_springboot.service.QaService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/qa")
public class QaController {
    // 可能导致内存泄漏
    private static final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    @Autowired
    private QaService qaService;

    @PostMapping("/ask")
    private ResponseEntity<String> ask(HttpServletRequest request, @RequestBody Map<String, String> body) {
        String userId = (String) request.getAttribute("userId");
        String content = body.get("content");
        String sessionId = body.get("sessionId");

        SseEmitter emitter = new SseEmitter(60_000L); // 60s超时

        // 存储当前连接
        emitters.put(userId, emitter);
        // 回调函数：移除完成的emitter
        emitter.onCompletion(() -> emitters.remove(userId));
        emitter.onTimeout(() -> emitters.remove(userId));
        emitter.onError((e) -> emitters.remove(userId));

        qaService.handleEmitter(emitter, content ,sessionId);
        return ResponseEntity.ok(userId);
    }

    @GetMapping("/sse/{id}")
    public SseEmitter streamChat(@PathVariable String id) {
        return emitters.get(id);
    }
}
