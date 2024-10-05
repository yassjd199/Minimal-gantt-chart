package com.yassjd199.GanttChart.service;

import com.yassjd199.GanttChart.model.Task;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskService {
    Task getTaskById(Integer id);
    List<Task> getAllTasks();
    Task addTask(Task task);
    Task updateTask(Task task,Integer id);
    void deleteTask(Integer id);
    Task updateDep(List<Integer> dep, Integer id);
    List<Task> orderTask();
}
