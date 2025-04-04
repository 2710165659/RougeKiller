package com.rouge.rouge_springboot.interceptor;

import com.rouge.rouge_springboot.util.TokenUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

// Springboot 3.x+：jakarta.servlet 替代 javax.servlet
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Objects;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
        // 从请求头中获取token
        String token = request.getHeader("Authorization");

        // 检查是否有Bearer token
        if (token == null || !token.startsWith("Bearer ")) {
            ResponseEntity<?> responseEntity = ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Unauthorized: Missing or invalid token");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write(Objects.requireNonNull(responseEntity.getBody()).toString());
            return false;
        }

        // 提取实际的token部分
        String jwtToken = token.substring(7);

        // 验证token
        if (!TokenUtil.verifyToken(jwtToken)) {
            ResponseEntity<?> responseEntity = ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Unauthorized: Invalid or expired token");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write(Objects.requireNonNull(responseEntity.getBody()).toString());
            return false;
        }

        // 从token中获取用户ID并添加到请求属性中
        String userId = TokenUtil.getUserIdFromToken(jwtToken);
        request.setAttribute("userId", userId);

        return true;
    }
}