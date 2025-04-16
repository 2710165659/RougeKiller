package com.rouge.rouge_springboot.mapper;

import com.rouge.rouge_springboot.model.entity.User;
// import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserMapperTest {
    @Autowired
    private UserMapper userMapper;

    // @Test
    void addExtensionUser() {
        User user = new User();
        user.setEmail("default@test.com");
        user.setSaltedPassword("test");
        user.setRole("管理员");
        user.setId(999L); // 保留999为扩展用户
        user.setName("Extension");
        userMapper.insert(user);
    }

}