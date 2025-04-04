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

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    UserMapper userMapper;


    @Override
    public ResponseEntity<String> login(String email, String password) {
        User user = userMapper.selectByUserEmail(email);
        if (user == null){
            return new ResponseEntity<String>("用户不存在", HttpStatus.UNAUTHORIZED);
        }
        if (!PasswordUtil.checkPassword(password, user.getSaltedPassword())){
            return new ResponseEntity<String>("密码错误", HttpStatus.UNAUTHORIZED);
        }
        // 生成token并返回
        return ResponseEntity.ok(TokenUtil.generateToken(user.getId()));
    }

    @Override
    public ResponseEntity<String> register(String email, String password) {
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
