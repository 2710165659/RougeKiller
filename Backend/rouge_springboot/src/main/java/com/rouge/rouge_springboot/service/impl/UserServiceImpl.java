package com.rouge.rouge_springboot.service.impl;

import com.rouge.rouge_springboot.mapper.UserMapper;
import com.rouge.rouge_springboot.model.entity.User;
import com.rouge.rouge_springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public ResponseEntity<?> getUserById(Long userId) {
        User user = userMapper.selectById(userId);
        if (user == null) throw new RuntimeException("User not found");
        user.setSaltedPassword(null);// 不返回密码
        return ResponseEntity.ok(user);
    }

    @Override
    public ResponseEntity<?> updateUserById(Long userId, User user) {
        User existingUser = userMapper.selectById(userId);
        if (existingUser == null) throw new RuntimeException("User not found");
        // 这里可以添加更多的字段更新逻辑
//        existingUser.setName(user.getName());
//        existingUser.setEmail(user.getEmail());
//        existingUser.setRole(user.getRole());

        return ResponseEntity.ok("User updated successfully");
    }

    @Override
    public ResponseEntity<?> deleteUserById(Long userId) {
        userMapper.deleteById(userId);
        return ResponseEntity.ok("User deleted successfully");
    }
}
