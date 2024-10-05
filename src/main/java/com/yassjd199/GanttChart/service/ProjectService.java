package com.yassjd199.GanttChart.service;

import com.yassjd199.GanttChart.model.Project;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
@Service
public interface ProjectService {
     LocalDate getProjectDateById(Integer id);
     //Project createProject(Project project);
     Project getProjectById();
     Project updateProject(Project project);


}
