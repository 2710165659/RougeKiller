package com.rouge.rouge_springboot.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

@Aspect
@Component
public class ControllerLoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(ControllerLoggingAspect.class);

    // 定义切点：拦截所有控制器方法
    @Pointcut("execution(* com.rouge..controller..*.*(..))")
    public void controllerPointcut() {}

    @Before("controllerPointcut()")
    public void beforeControllerMethod(JoinPoint joinPoint) {
        // 获取当前请求对象
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            // 记录请求信息
            logger.info("收到请求: {} {}", request.getMethod(), request.getRequestURI());
        } else {
            logger.info("收到请求: 非HTTP请求 - {}", joinPoint.getSignature().toShortString());
        }
    }

    @AfterReturning(pointcut = "controllerPointcut()", returning = "result")
    public void afterControllerMethod(JoinPoint joinPoint, Object result) {
        // 记录响应结果
        logger.info("响应结果: {}", result != null ? result.toString() : "null");
    }
}