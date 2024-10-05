package com.yassjd199.GanttChart.dto;

import com.yassjd199.GanttChart.dto.TaskDto;
import com.yassjd199.GanttChart.model.Task;

public class TaskMapper {
    public static TaskDto toDto(Task task) {
        return new TaskDto(
                task.getTitle(),
                task.getDuration()
        );
    }

    public static Task toEntity(TaskDto taskDto) {
        Task task = new Task();
        task.setTitle(taskDto.title());
        task.setDuration(taskDto.duration());
        return task;
    }
}
