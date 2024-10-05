package com.yassjd199.GanttChart.repository;

import com.yassjd199.GanttChart.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepo extends JpaRepository<Task, Integer> {

}
