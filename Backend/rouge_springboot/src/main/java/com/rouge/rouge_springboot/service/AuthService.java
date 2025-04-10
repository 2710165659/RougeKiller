package com.rouge.rouge_springboot.service;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface AuthService {
    ResponseEntity<?> login(String email, String password);

    ResponseEntity<?> register(String email, String password);
}
