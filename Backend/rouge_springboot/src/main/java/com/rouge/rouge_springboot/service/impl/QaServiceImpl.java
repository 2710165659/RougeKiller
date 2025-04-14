package com.rouge.rouge_springboot.service.impl;

import com.alibaba.dashscope.app.Application;
import com.alibaba.dashscope.app.ApplicationParam;
import com.alibaba.dashscope.app.ApplicationResult;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.rouge.rouge_springboot.service.QaService;
import io.reactivex.Flowable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Service
public class QaServiceImpl implements QaService {

    @Override
    public void handleEmitter(SseEmitter sseEmitter, String content, String sessionId) {
        ApplicationParam param = ApplicationParam.builder()
                .apiKey("sk-c518b4cb469f49c6a9852625848b4d3c")
                .appId("6124e78be439410d9207f89f10239931")
                .prompt(content)
                .sessionId(sessionId)
                .build();

        Application application = new Application();
        try {
            Flowable<ApplicationResult> result = application.streamCall(param); // 流式输出
            result.blockingForEach(data -> {
                // 每次接收到数据时，发送给前端，不使用增量输出
                sendText(sseEmitter, data.getOutput().getText());
            });
            // 积累最后的结果
            ApplicationResult lastResult = result
                    .reduce((first, second) -> second)
                    .blockingGet();
            // 发送sessionId
            sendSessionId(sseEmitter, lastResult.getOutput().getSessionId());

            // 发送结束标志
            sseEmitter.complete();
        } catch (InputRequiredException | NoApiKeyException | IOException e) {
            String errorMessage = e.getMessage();
            sseEmitter.completeWithError(new RuntimeException(errorMessage));
        }

    }

    private void sendSessionId(SseEmitter sseEmitter, String sessionId) throws IOException {
        sseEmitter.send(SseEmitter.event()
                .name("sessionId")
                .data(sessionId)
                .reconnectTime(1000));
    }

    private void sendText(SseEmitter sseEmitter, String text) throws IOException {
        sseEmitter.send(SseEmitter.event()
                .name("text")
                .data(text)
                .reconnectTime(1000));
    }

}
