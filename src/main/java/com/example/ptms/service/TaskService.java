package com.example.ptms.service;

import com.example.ptms.dto.TaskCreateRequest;
import com.example.ptms.dto.TaskUpdateRequest;
import com.example.ptms.entity.Task;
import com.example.ptms.mapper.TaskMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.ptms.common.BusinessException;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {
    private final TaskMapper taskMapper;
    public TaskService(TaskMapper taskMapper){
        this.taskMapper = taskMapper;
    }

    public List<Task> list(Long userId, Integer status) {
        List<Task> tasks = taskMapper.findAllByUserId(userId);
        if (status == null) {
            return tasks;
        }
        List<Task> result = new ArrayList<>();
        for (Task t : tasks) {
            if (status.equals(t.getStatus())) {
                result.add(t);
            }
        }
        return result;
    }


    public Task getById(Long id,Long userId){
        Task task = taskMapper.findByIdAndUserId(id,userId);
        if(task == null){
            throw new BusinessException(404,"任务不存在");
        }
        return task;
    }
    public Task create(Long userId, TaskCreateRequest req){
        Task task = new Task();
        task.setUserId(userId);
        task.setTitle(req.getTitle());
        task.setContent(req.getContent());
        taskMapper.insert(task);
        return taskMapper.findByIdAndUserId(task.getId(),userId);
    }

    public void update(Long id, Long userId, TaskUpdateRequest req){
        Task task = new Task();
        task.setId(id);
        task.setUserId(userId);
        task.setTitle(req.getTitle());
        task.setContent(req.getContent());
        int rows = taskMapper.update(task);
        if(rows == 0){
            throw new BusinessException(404,"任务不存在");

        }
    }

    public void updateStatus(Long id ,Long userId ,Integer status){
        int rows = taskMapper.updateStatus(id, userId, status);
        if(rows == 0){
            throw new BusinessException(404,"任务不存在");
        }
    }

    public void delete(Long id ,Long userId){
        int rows = taskMapper.delete(id, userId);
        if(rows == 0){
            throw new BusinessException(404,"任务不存在");
        }
    }

    @Transactional
    public void batchDelete(List<Long> ids,Long userId){
        for(Long id : ids){
            int rows = taskMapper.delete(id,userId);
            if(rows == 0){
                throw new BusinessException(404,"任务不存在或无权操作：" + id);
            }
        }
    }

}
