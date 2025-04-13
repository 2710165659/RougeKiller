package com.rouge.rouge_springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rouge.rouge_springboot.mapper.TasksMapper;
import com.rouge.rouge_springboot.model.dto.tasks.TasksDTO;
import com.rouge.rouge_springboot.model.entity.Tasks;
import com.rouge.rouge_springboot.service.TasksService;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Timestamp;
import java.util.Date;
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

    @Override
    public boolean startTask(Long id) {
        //启动任务
        Tasks task = tasksMapper.selectById(id);
        task.setStatus("进行中");
        return tasksMapper.updateById(task) > 0;
    }

    @Override
    public boolean deleteTask(Long id) {
        return tasksMapper.deleteById(id) > 0;
    }

    @Override
    public Tasks addTask(Tasks tasks) {
        //获取用户id
        tasks.setStatus("进行中");
        tasksMapper.insert(tasks);
        return tasks;
    }
}
