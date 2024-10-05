package com.yassjd199.GanttChart.service;

import com.yassjd199.GanttChart.model.Project;
import com.yassjd199.GanttChart.repository.ProjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

@Service
public class ProjectServiceImp implements ProjectService {
    @Autowired
    private ProjectRepo projectRepo;

    @Override
    public Project getProjectById() {
        Optional<Project> projectOptional = projectRepo.findById(1);
        if(projectOptional.isPresent()){return projectOptional.get();}
        else return new Project();
    }

    @Override
    public Project updateProject(Project project) {
        Optional<Project> projectOptional = projectRepo.findById(1);
        if (projectOptional.isPresent()) {
            projectOptional.get().setId(1);
            projectOptional.get().setName(project.getName());
            projectOptional.get().setStartDate(project.getStartDate());
            return projectRepo.save(projectOptional.get());
        }
        else {
            Project newProject = new Project();
            newProject.setName(project.getName());
            newProject.setStartDate(project.getStartDate());
            newProject.setId(1);
            return projectRepo.save(newProject);
        }
    }

    @Override
    public LocalDate getProjectDateById(Integer id) {
        return null;
    }
}
