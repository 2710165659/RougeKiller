package com.rouge.rouge_springboot.service.impl;

import com.rouge.rouge_springboot.mapper.UserMapper;
import com.rouge.rouge_springboot.model.User;
import com.rouge.rouge_springboot.service.AuthService;
import com.rouge.rouge_springboot.util.PasswordUtil;
import com.rouge.rouge_springboot.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    UserMapper userMapper;


    @Override
    public ResponseEntity<?> login(String email, String password) {
        User user = userMapper.selectByUserEmail(email);
        if (user == null){
            return new ResponseEntity<String>("用户不存在", HttpStatus.UNAUTHORIZED);
        }
        if (!PasswordUtil.checkPassword(password, user.getSaltedPassword())){
            return new ResponseEntity<String>("密码错误", HttpStatus.UNAUTHORIZED);
        }
        // 返回用户信息和token
        String token = TokenUtil.generateToken(user.getId());

        Map<String,Object> returnUser = new HashMap<>();
        returnUser.put("name",user.getName());
        returnUser.put("email",user.getEmail());
        returnUser.put("role",user.getRole());
        returnUser.put("createdAt",user.getCreatedAt());
        returnUser.put("updatedAt",user.getUpdatedAt());

        Map<String,Object> data = new HashMap<>();
        data.put("token",token);
        data.put("user",returnUser);

        return ResponseEntity.ok(data);
    }

    @Override
    public ResponseEntity<?> register(String email, String password) {
        if (userMapper.selectByUserEmail(email) != null){
            return new ResponseEntity<String>("用户已存在", HttpStatus.CONFLICT);
        }
        User user = new User();
        user.setEmail(email);
        user.setSaltedPassword(PasswordUtil.encryptPassword(password));
        user.setName("默认用户");
        user.setRole("游客");

        userMapper.insert(user);
        return ResponseEntity.ok("注册成功");
    }
}
