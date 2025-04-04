package com.rouge.rouge_springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rouge.rouge_springboot.model.Website;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WebsiteMapper extends BaseMapper<Website> {
}