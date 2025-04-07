package com.rouge.rouge_springboot.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    @Pointcut("execution(* com.rouge.rouge_springboot.controller.AuthController.*(..))")
    public void authControllerMethods() {
    }

    @Before("authControllerMethods()")
    public void logRequest(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        Object[] args = joinPoint.getArgs();

        System.out.println("Received request in " + className + "." + methodName);
        System.out.println("Request URL: /auth/" + methodName);
        System.out.println("Request Data: " + Arrays.toString(args));
    }
}