package com.yassjd199.GanttChart.controller;

import com.yassjd199.GanttChart.model.Project;
import com.yassjd199.GanttChart.service.ProjectServiceImp;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/projects")
@CrossOrigin(origins = "http://localhost:5173")
public class ProjectController {

    private final ProjectServiceImp projectServiceImp;

    public ProjectController(ProjectServiceImp projectServiceImp) {
        this.projectServiceImp = projectServiceImp;
    }

    @PutMapping()
    private Project updateProject(@RequestBody Project project){
        return projectServiceImp.updateProject(project);
    }
    @GetMapping()
    private Project getProject(){
        return projectServiceImp.getProjectById();
    }

}
