package com.yassjd199.GanttChart.repository;

import com.yassjd199.GanttChart.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.*;

public interface ProjectRepo extends JpaRepository<Project, Integer> {
}
