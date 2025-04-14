package com.rouge.rouge_springboot.service;


import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface QaService {
  void handleEmitter(SseEmitter sseEmitter,String content,String sessionId);
}
