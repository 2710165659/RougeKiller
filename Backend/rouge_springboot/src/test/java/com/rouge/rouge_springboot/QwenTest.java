package com.rouge.rouge_springboot;

import com.alibaba.dashscope.app.Application;
import com.alibaba.dashscope.app.ApplicationParam;
import com.alibaba.dashscope.app.ApplicationResult;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.System;

import io.reactivex.Flowable;

@SpringBootTest
public class QwenTest {

    @Test
    public void ask() throws NoApiKeyException, InputRequiredException {
        String content = "请介绍一下你自己";
        String sessionId = "";

        ApplicationParam param = ApplicationParam.builder()
                .apiKey("sk-c518b4cb469f49c6a9852625848b4d3c")
                .appId("6124e78be439410d9207f89f10239931")
                .prompt(content)
                .sessionId(sessionId)
                .build();

        Application application = new Application();
        Flowable<ApplicationResult> result = application.streamCall(param); // 流式输出

        result.blockingForEach(data -> {
            System.out.printf("%s\n",
                    data.getOutput().getText());
        });

        ApplicationResult lastResult = result
                .reduce((first, second) -> second)
                .blockingGet();
        System.out.println("SessionId: " + lastResult.getOutput().getSessionId());



    }

}
