package com.rouge.rouge_springboot.service.impl;

import com.alibaba.dashscope.app.Application;
import com.alibaba.dashscope.app.ApplicationParam;
import com.alibaba.dashscope.app.ApplicationResult;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.rouge.rouge_springboot.service.QaService;
import io.reactivex.Flowable;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

@Service
public class QaServiceImpl implements QaService {

    @Override
    public void handleSession(WebSocketSession session, String content, String sessionId) {
        ApplicationParam param = ApplicationParam.builder()
                .apiKey("sk-c518b4cb469f49c6a9852625848b4d3c")
                .appId("6124e78be439410d9207f89f10239931")
                .prompt(content)
                .sessionId(sessionId)
                .incrementalOutput(true) // 增量输出
                .build();

        Application application = new Application();
        try {  Flowable<ApplicationResult> result = application.streamCall(param); // 流式输出

            result.blockingForEach(data -> {
                // 每次接收到数据时，发送给前端，增量输出
                session.sendMessage(new TextMessage("text-" + data.getOutput().getText()));
            });
            ApplicationResult lastResult = result
                    .reduce((first, second) -> second)
                    .blockingGet();
            session.sendMessage(new TextMessage("sessionId-" + lastResult.getOutput().getSessionId()));
            session.sendMessage(new TextMessage("end-stopped"));
        } catch (NoApiKeyException | IOException | InputRequiredException e) {
            safeClose(session, new CloseStatus(4000, "Error"));
        }
    }


    private void safeClose(WebSocketSession session, CloseStatus status) {
        try {
            if (session.isOpen()) {
                session.close(status);
            }
        } catch (IOException ignored) {
        }
    }

}
