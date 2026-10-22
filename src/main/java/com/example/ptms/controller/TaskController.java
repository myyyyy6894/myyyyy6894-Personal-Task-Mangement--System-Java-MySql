package com.example.ptms.controller;

import com.example.ptms.common.Result;
import com.example.ptms.dto.TaskBatchDeleteRequest;
import com.example.ptms.dto.TaskCreateRequest;
import com.example.ptms.dto.TaskStatusRequest;
import com.example.ptms.dto.TaskUpdateRequest;
import com.example.ptms.entity.Task;
import com.example.ptms.interceptor.UserContext;
import com.example.ptms.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }

    @GetMapping
    public Result<List<Task>> list(@RequestParam(required = false) Integer status){
        Long userId = UserContext.get();
        return Result.ok(taskService.list(userId,status));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Result<Task> create(@Valid @RequestBody TaskCreateRequest req){
        Long userId = UserContext.get();
        return Result.ok(taskService.create(userId,req));
    }

    @GetMapping("/{id}")
    public Result<Task> get(@PathVariable Long id){
        Long userId = UserContext.get();
        return Result.ok(taskService.getById(id,userId));
    }

    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Long id, @Valid @RequestBody TaskUpdateRequest req){
        Long userId = UserContext.get();
        taskService.update(id,userId,req);
        return Result.ok(null);
    }
    @PatchMapping("/{id}/status")
    public Result<Void> updateStatus(@PathVariable Long id, @Valid @RequestBody TaskStatusRequest req){
        Long userId = UserContext.get();
        taskService.updateStatus(id,userId, req.getStatus());
        return Result.ok(null);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        Long userId = UserContext.get();
        taskService.delete(id,userId);
    }

    @DeleteMapping("/batch")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void batchDelete(@Valid @RequestBody TaskBatchDeleteRequest req){
        Long userId = UserContext.get();
        taskService.batchDelete(req.getIds(),userId);
    }
}
