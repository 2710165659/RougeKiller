package com.rouge.rouge_springboot.controller;

import com.rouge.rouge_springboot.model.entity.User;
import com.rouge.rouge_springboot.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/info")
    public ResponseEntity<?> getUserInfo(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUserInfo(@RequestBody User user, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return userService.updateUserById(userId, user);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return userService.deleteUserById(userId);
    }
}
