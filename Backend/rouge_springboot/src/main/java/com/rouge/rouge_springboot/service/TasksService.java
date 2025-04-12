package com.rouge.rouge_springboot.service;

import com.rouge.rouge_springboot.model.entity.Tasks;

import java.util.List;

public interface TasksService {
    /**
     * 根据url查询任务
     * @param url
     * @return
     */
    List<Tasks> searchByUrl(String url);
}
