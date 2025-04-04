package com.rouge.rouge_springboot.service;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface AuthService {
    ResponseEntity<String> login(String email, String password);
    ResponseEntity<String> register(String email, String password);
}
