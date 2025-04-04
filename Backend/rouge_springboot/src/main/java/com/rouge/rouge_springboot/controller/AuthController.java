package com.rouge.rouge_springboot.controller;

import com.rouge.rouge_springboot.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, Object> loginInfo){
        try {
            String email = (String) loginInfo.get("email");
            String password = (String) loginInfo.get("password");
            return authService.login(email, password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, Object> registerInfo){
        try {
            String email = (String) registerInfo.get("email");
            String password = (String) registerInfo.get("password");
            String confirmPassword = (String) registerInfo.get("confirmPassword");
            if (!password.equals(confirmPassword)) throw new Exception("Password does not match");
            return authService.register(email, password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
