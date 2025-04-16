package com.rouge.rouge_springboot.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rouge.rouge_springboot.mapper.TasksMapper;
import com.rouge.rouge_springboot.model.dto.tasks.TasksDTO;
import com.rouge.rouge_springboot.model.entity.Tasks;
import com.rouge.rouge_springboot.service.TasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TasksServiceImpl implements TasksService {

    @Autowired
    private TasksMapper tasksMapper;

    @Override
    public List<TasksDTO.TasksResponseDTO> searchTasks(TasksDTO.TasksQueryDTO queryDTO) {
        // 查询
        if (queryDTO.getUrl() == null)
            queryDTO.setUrl("");
        Page<TasksDTO.TasksResponseDTO> page = new Page<>(queryDTO.getPage(), queryDTO.getSize());
        IPage<TasksDTO.TasksResponseDTO> resultPage = tasksMapper.getTasks(page, queryDTO);
        return resultPage.getRecords();
    }

    @Override
    public boolean startTask(Long id) {
        // 启动任务
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
        tasks.setStatus("进行中");
        tasksMapper.insert(tasks);
        return tasks;
    }
}
