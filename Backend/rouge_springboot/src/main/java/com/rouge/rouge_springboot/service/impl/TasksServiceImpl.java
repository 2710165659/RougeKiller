package com.rouge.rouge_springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rouge.rouge_springboot.mapper.TasksMapper;
import com.rouge.rouge_springboot.model.dto.tasks.TasksDTO;
import com.rouge.rouge_springboot.model.entity.Tasks;
import com.rouge.rouge_springboot.service.TasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TasksServiceImpl implements TasksService {

    @Autowired private TasksMapper tasksMapper;
    @Override
    public List<TasksDTO> searchByUrl(String url) {
        //查询
        if(url==null) url="";
        return tasksMapper.getTasksByUrl(url);
    }
}
