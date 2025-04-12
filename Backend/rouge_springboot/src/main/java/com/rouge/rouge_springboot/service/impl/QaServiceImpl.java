package com.rouge.rouge_springboot.service.impl;

import com.rouge.rouge_springboot.service.QaService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class QaServiceImpl implements QaService {
    private final ExecutorService executor = Executors.newCachedThreadPool();

    @Override
    public SseEmitter streamChatCompletion(Map<String, String> params) {
        SseEmitter emitter = new SseEmitter(60 * 1000L);

        executor.execute(() -> {
            try {
                String[] responses = {
                        "这是",
                        "模拟的",
                        "AI流式",
                        "回复内容"
                };

                for (String part : responses) {
                    try {
                        Thread.sleep(300);
                        emitter.send(SseEmitter.event()
                                .data(part)
                                .id(String.valueOf(System.currentTimeMillis())));
                    } catch (Exception e) {
                        emitter.completeWithError(e);
                        return;
                    }
                }
                emitter.complete();
            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        });

        return emitter;
    }
}
