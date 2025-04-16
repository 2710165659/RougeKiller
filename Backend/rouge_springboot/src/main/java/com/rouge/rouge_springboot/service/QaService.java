package com.rouge.rouge_springboot.service;


import org.springframework.web.socket.WebSocketSession;

public interface QaService {
  void handleSession(WebSocketSession session, String content, String sessionId);
}
