package com.rouge.rouge_springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rouge.rouge_springboot.mapper.TasksMapper;
import com.rouge.rouge_springboot.model.entity.Tasks;
import com.rouge.rouge_springboot.service.TasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TasksServiceImpl implements TasksService {

    @Autowired private TasksMapper tasksMapper;
    @Override
    public List<Tasks> searchByUrl(String url) {
        //查询器
        LambdaQueryWrapper<Tasks> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Tasks::getFullUrl, url);
        return tasksMapper.selectList(queryWrapper);
    }
}
