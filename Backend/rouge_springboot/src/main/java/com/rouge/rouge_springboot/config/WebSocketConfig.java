package com.rouge.rouge_springboot.config;

import com.rouge.rouge_springboot.controller.QaController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;


@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer  {
    @Autowired
    private QaController qaController;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(qaController, "/qa/ws") // 自定义处理器
                .setAllowedOrigins("*"); // 允许跨域
    }
}