package com.rouge.rouge_springboot.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    /**
     * 处理成功返回
     * @return 200，body里放数据
     */
    @GetMapping("/ok")
    public ResponseEntity<?> test() {
        // 返回数据
        return ResponseEntity.ok("test");
    }

    /**
     * 处理失败返回示例1，手动返回响应体
     * @return 错误码400，body里放错误信息
     */
    @GetMapping("/error1")
    public ResponseEntity<?> testError1() {
        return new ResponseEntity<>("test error", HttpStatus.NOT_FOUND);
    }

    /**
     * 处理失败返回示例2，抛出异常，由全局异常处理进行返回
     * @return 错误码500，body里放错误信息
     */
    @GetMapping("/error2")
    public ResponseEntity<?> testError2() {
        throw new RuntimeException("test error");
    }
}
