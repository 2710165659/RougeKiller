package com.rouge.rouge_springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rouge.rouge_springboot.model.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("select * from users where email = #{email}")
    User selectByUserEmail(String email);
}
