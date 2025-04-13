package com.rouge.rouge_springboot.controller;

import com.rouge.rouge_springboot.model.dto.tasks.TasksDTO;
import com.rouge.rouge_springboot.model.entity.Tasks;
import com.rouge.rouge_springboot.service.TasksService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
public class TasksController {
    @Autowired
    private TasksService tasksService;
    @GetMapping
    public ResponseEntity<List<TasksDTO>> searchByUrl(String url) {
        List<TasksDTO> tasks = tasksService.searchByUrl(url);
        return ResponseEntity.ok(tasks);
    }

    @PostMapping("/start")
    public ResponseEntity<Boolean> startTask(@RequestBody Map<String, Object> params) {
        Long id = Long.parseLong(params.get("id").toString());
        boolean success = tasksService.startTask(id);
        return ResponseEntity.ok(success);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteTask(@PathVariable("id") Long id) {
        boolean success = tasksService.deleteTask(id);
        return ResponseEntity.ok(success);
    }

    @PostMapping
    public ResponseEntity<Tasks> addTask(HttpServletRequest request, @RequestBody Tasks tasks) {
        Long userId = Long.parseLong((String) request.getAttribute("userId"));
        tasks.setCreatedBy(userId);
        Tasks newTask = tasksService.addTask(tasks);
        return ResponseEntity.ok(newTask);
    }
}
