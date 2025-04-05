package com.rouge.rouge_springboot.service;

import com.rouge.rouge_springboot.model.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Transactional
public interface UserService {
    ResponseEntity<?> getUserById(Long userId);
    ResponseEntity<?> updateUserById(Long userId, Map<String,String> userInfo);
    ResponseEntity<?> deleteUserById(Long userId);
}
