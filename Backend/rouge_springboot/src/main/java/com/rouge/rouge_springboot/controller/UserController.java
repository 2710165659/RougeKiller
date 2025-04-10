package com.rouge.rouge_springboot.controller;

import com.rouge.rouge_springboot.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/info")
    public ResponseEntity<?> getUserInfo(HttpServletRequest request) {
        Long userId = Long.parseLong((String) request.getAttribute("userId"));
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUserInfo(@RequestBody Map<String, String> userInfo, HttpServletRequest request) {
        Long userId = Long.parseLong((String) request.getAttribute("userId"));
        return userService.updateUserById(userId, userInfo);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(HttpServletRequest request) {
        Long userId = Long.parseLong((String) request.getAttribute("userId"));
        return userService.deleteUserById(userId);
    }
}
