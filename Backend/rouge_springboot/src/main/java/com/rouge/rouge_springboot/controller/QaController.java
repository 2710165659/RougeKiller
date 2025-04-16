package com.rouge.rouge_springboot.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rouge.rouge_springboot.service.QaService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.Map;

@Component
public class QaController extends TextWebSocketHandler {
    @Autowired
    QaService qaService;

    @Override
    protected void handleTextMessage(@NotNull WebSocketSession session, @NotNull TextMessage message) {
        try{
            String payload = message.getPayload(); // {"content":"123\n","sessionId":""}
            // 提取content和sessionId
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> map = objectMapper.readValue(
                    payload,
                    new TypeReference<Map<String, String>>() {}
            );
            String content = map.get("content");
            String sessionId = map.get("sessionId");
            // 处理请求
            qaService.handleSession(session, content, sessionId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}



