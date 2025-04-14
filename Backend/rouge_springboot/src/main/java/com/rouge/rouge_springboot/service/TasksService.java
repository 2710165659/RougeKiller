package com.rouge.rouge_springboot.service;

import com.rouge.rouge_springboot.model.dto.tasks.TasksDTO;
import com.rouge.rouge_springboot.model.entity.Tasks;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface TasksService {
    /**
     * 根据url查询任务
     * @param url
     * @return
     */
    List<TasksDTO> searchByUrl(String url);

    /**
     * 开始任务
     * @param id
     * @return
     */
    boolean startTask(Long id);

    /**
     * 删除任务
     * @param id
     * @return
     */
    boolean deleteTask(Long id);

    /**
     * 添加任务
     * @param tasks
     * @return
     */
    Tasks addTask(Tasks tasks);

}
