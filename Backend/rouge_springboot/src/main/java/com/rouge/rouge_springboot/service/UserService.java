package com.rouge.rouge_springboot.service;

import com.rouge.rouge_springboot.model.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserService {
    ResponseEntity<?> getUserById(Long userId);
    ResponseEntity<?> updateUserById(Long userId, User user);
    ResponseEntity<?> deleteUserById(Long userId);
}
