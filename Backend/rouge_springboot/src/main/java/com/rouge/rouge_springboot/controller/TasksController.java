package com.rouge.rouge_springboot.controller;

import com.rouge.rouge_springboot.model.entity.Tasks;
import com.rouge.rouge_springboot.service.TasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TasksController {
    @Autowired
    private TasksService tasksService;
    @GetMapping
    public ResponseEntity<List<Tasks>> searchByUrl(String url) {
        List<Tasks> tasks = tasksService.searchByUrl(url);
        return ResponseEntity.ok(tasks);
    }

}
