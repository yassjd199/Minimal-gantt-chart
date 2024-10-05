package com.yassjd199.GanttChart.controller;

import com.yassjd199.GanttChart.dto.TaskDto;
import com.yassjd199.GanttChart.dto.TaskMapper;
import com.yassjd199.GanttChart.model.Task;
import com.yassjd199.GanttChart.service.TaskService;
import com.yassjd199.GanttChart.service.TaskServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private  final TaskServiceImp taskServiceImp;

    public TaskController(TaskServiceImp taskServiceImp) {
        this.taskServiceImp = taskServiceImp;
    }

    @PostMapping()
    private Task addTask(@RequestBody TaskDto taskDto) {
        return taskServiceImp.addTask(TaskMapper.toEntity(taskDto));
    }
    @GetMapping()
    private List<Task> getTasks() {
        return  taskServiceImp.getAllTasks();
    }
    @GetMapping("/ordered")
    private List<Task> getOrderedTasks() {
        //List<Task> tasks=  taskServiceImp.getAllTasks();

        return taskServiceImp.orderTask();
    }
    @GetMapping("/{task-id}")
    private Task getTask(@PathVariable("task-id") int taskId) {
        return taskServiceImp.getTaskById(taskId);
    }
    @DeleteMapping("/{task-id}")
    private void deleteTask(@PathVariable("task-id") Integer taskId) {
        taskServiceImp.deleteTask(taskId);
    }
    @PutMapping("/{task-id}")
    private Task updateTask(@PathVariable("task-id") Integer taskId,@RequestBody Task task) {
        return taskServiceImp.updateTask(task,taskId);
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @PutMapping("/dependency-update/{task-id}")
    private Task updateTaskDep (@RequestBody List<Integer> dep,@PathVariable("task-id") Integer id) {
        return  taskServiceImp.updateDep(dep,id);
    }
}
