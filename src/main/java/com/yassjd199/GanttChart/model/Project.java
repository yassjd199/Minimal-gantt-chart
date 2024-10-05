package com.yassjd199.GanttChart.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
public class Project {

    @Id
    private Integer id;
    private String name;  // Primary Key

    @OneToMany(mappedBy = "project")
    private List<Task> tasks;

    @Temporal(TemporalType.DATE)  // Specifies the Date format
    @Column(nullable = false)
    private LocalDate startDate;

    // Constructors
    public Project() {
    }

    public Project(String name, String key, LocalDate startDate) {
        this.name = name;
        this.startDate = startDate;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    // toString Method
    @Override
    public String toString() {
        return "Project{" +
                "name='" + name + '\'' +
                ", startDate=" + startDate +
                '}';
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
}
