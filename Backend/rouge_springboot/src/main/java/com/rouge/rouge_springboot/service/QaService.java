package com.rouge.rouge_springboot.service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;

@Transactional
public interface QaService {
  SseEmitter streamChatCompletion(Map<String, String> params);
}
