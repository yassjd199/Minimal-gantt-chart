package com.yassjd199.GanttChart.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Task {
    @Id
    @GeneratedValue
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Project project;
    private String title;
    @Temporal(TemporalType.DATE)
    private LocalDate startDate;
    @Temporal(TemporalType.DATE)
    private LocalDate endDate;
    @Temporal(TemporalType.DATE)
    private LocalDate earlyStartDate;
    @Temporal(TemporalType.DATE)
    private LocalDate earlyEndDate;
    @Temporal(TemporalType.DATE)
    private LocalDate lateStartDate;
    @Temporal(TemporalType.DATE)
    private LocalDate lateEndDate;
    private Integer duration ;
    private Boolean isCritical;
    @ElementCollection
    private List <Integer> dep ;

    public Task() {
       // this.dep = new ArrayList<Integer>();
    }

    public Task(Integer id, Project project, String title, LocalDate startDate, LocalDate endDate, LocalDate earlyStartDate, LocalDate earlyEndDate, LocalDate lateStartDate, LocalDate lateEndDate, Integer duration, Boolean isCritical, List<Integer> dep) {
        this.id = id;
        this.project = project;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.earlyStartDate = earlyStartDate;
        this.earlyEndDate = earlyEndDate;
        this.lateStartDate = lateStartDate;
        this.lateEndDate = lateEndDate;
        this.duration = duration;
        this.isCritical = isCritical;
        this.dep = dep;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getEarlyStartDate() {
        return earlyStartDate;
    }

    public void setEarlyStartDate(LocalDate earlyStartDate) {
        this.earlyStartDate = earlyStartDate;
    }

    public LocalDate getEarlyEndDate() {
        return earlyEndDate;
    }

    public void setEarlyEndDate(LocalDate earlyEndDate) {
        this.earlyEndDate = earlyEndDate;
    }

    public LocalDate getLateStartDate() {
        return lateStartDate;
    }

    public void setLateStartDate(LocalDate lateStartDate) {
        this.lateStartDate = lateStartDate;
    }

    public LocalDate getLateEndDate() {
        return lateEndDate;
    }

    public void setLateEndDate(LocalDate lateEndDate) {
        this.lateEndDate = lateEndDate;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Boolean getCritical() {
        return isCritical;
    }

    public void setCritical(Boolean critical) {
        isCritical = critical;
    }

    public List<Integer> getDep() {
        return dep;
    }

    public void setDep(List<Integer> dep) {
        this.dep = dep;
    }
}
