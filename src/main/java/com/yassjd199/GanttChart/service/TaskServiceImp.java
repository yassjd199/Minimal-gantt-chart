package com.yassjd199.GanttChart.service;

import com.yassjd199.GanttChart.model.Project;
import com.yassjd199.GanttChart.model.Task;
import com.yassjd199.GanttChart.repository.ProjectRepo;
import com.yassjd199.GanttChart.repository.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.spi.CalendarDataProvider;

@Service
public class TaskServiceImp implements TaskService {
    @Autowired
    private TaskRepo taskRepo;
    @Autowired
    private ProjectRepo projectRepo;

    @Override
    public Task getTaskById(Integer id) {
        return taskRepo.findById(id).orElse(null);
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepo.findAll();
    }

    @Override
    public Task addTask(Task task) {
        return taskRepo.save(task);
    }

    @Override
    public Task updateTask(Task task, Integer id) {
        Task oldTask = taskRepo.findById(id).orElse(null);
        if (oldTask != null) {
            oldTask.setTitle(task.getTitle());
            oldTask.setCritical(task.getCritical());
            oldTask.setEndDate(task.getEndDate());
            oldTask.setStartDate(task.getStartDate());
            oldTask.setDuration(task.getDuration());
            oldTask.setDep(task.getDep());
            oldTask.setEarlyEndDate(task.getEarlyEndDate());
            oldTask.setEarlyStartDate(task.getEarlyStartDate());
            oldTask.setLateStartDate(task.getLateStartDate());
            oldTask.setLateEndDate(task.getLateEndDate());
            taskRepo.save(oldTask);
            return oldTask;
        } else throw new RuntimeException("Task not found");
    }

    @Override
    public void deleteTask(Integer id) {
        taskRepo.deleteById(id);
    }

    @Override
    public Task updateDep(List<Integer> dep, Integer id) {
        Task task = taskRepo.findById(id).orElse(null);
        if (task != null) task.setDep(dep);
        return taskRepo.save(task);
    }


    public void order(int curTaskId, int[] state, List<Integer> result, HashMap<Integer,ArrayList<Integer>> adj) throws Exception {
        state[curTaskId] = 1;
        for (int depTaskId : adj.get(curTaskId)) {
            if (state[depTaskId] == 1) {
                throw new Exception("Cycle detected involving task ID: " + depTaskId);
            }
            if (state[depTaskId] == 0) {
                order(depTaskId, state, result, adj);
            }
        }
        result.add(curTaskId);
        state[curTaskId] = 2;
    }

    // Inside your orderTask() method
    @Override
    public List<Task> orderTask() {
        List<Task> tasks = taskRepo.findAll();
        int sz = 200;
        for(Task task : tasks) {
            task.setEarlyEndDate(null);
            task.setEarlyStartDate(null);
            task.setLateStartDate(null);
            task.setLateEndDate(null);
            task.setStartDate(null);
            task.setEndDate(null);
            task.setCritical(false);
            sz=Math.max(sz,task.getId());
        }
        Optional<Project> project = projectRepo.findById(1);

        if (!project.isPresent()) return tasks;

        // Assuming project.getStartDate() returns a LocalDate directly
        LocalDate projectStartDate = project.get().getStartDate();


        List<Integer> result = new ArrayList<>();
        //List<Integer>[] adj = new ArrayList[sz + 1];
        HashMap<Integer,ArrayList<Integer>> adj = new HashMap<>();

        int[] state = new int[sz + 1];

        // Build adjacency list
        for(Task task : tasks) {
            adj.put(task.getId(), new ArrayList<>());
        }
        for (Task task : tasks) {
            List<Integer> dep = task.getDep();
            for (int depTaskId : dep) {
                ArrayList<Integer> adjTask = adj.get(depTaskId);
                adjTask.add(task.getId());
                adj.put(depTaskId,adjTask);
            }
        }

        // Perform topological sort
        for (Task task : tasks) {
            int curTaskId = task.getId();
            if (state[curTaskId] == 0) {
                try {
                    order(curTaskId, state, result, adj);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        Collections.reverse(result);

        // Step 3: Initialize earlyStart and earlyEnd
        for (Integer cur : result) {
            Task task = taskRepo.findById(cur).orElse(null);
            List<Integer> dep = task.getDep();
            if (dep.isEmpty()) {
                // No dependencies, set earlyStart and earlyEnd based on projectStartDate
                task.setEarlyStartDate(projectStartDate);
                LocalDate earlyEndDate = projectStartDate.plusDays(task.getDuration());
                task.setEarlyEndDate(earlyEndDate);
                task.setStartDate(projectStartDate);
                task.setEndDate(earlyEndDate);
            }
        }

        // Step 4: Update earlyStart and earlyEnd for tasks with dependencies
        for (Integer cur : result) {
            Task task = taskRepo.findById(cur).orElse(null);
            if (task.getEarlyStartDate() != null && task.getEarlyEndDate() != null) {
                continue; // Skip if earlyStart and earlyEnd are already set
            }

            List<Integer> dep = task.getDep();
            if (!dep.isEmpty()) {
                LocalDate maxEarlyEnd = projectStartDate;
                for (Integer depTaskId : dep) {
                    Task depTask = taskRepo.findById(depTaskId).orElse(null);
                    if (depTask.getEarlyEndDate().isAfter(maxEarlyEnd)) {
                        maxEarlyEnd = depTask.getEarlyEndDate();
                    }
                }

                task.setEarlyStartDate(maxEarlyEnd);
                LocalDate earlyEndDate = maxEarlyEnd.plusDays(task.getDuration());
                task.setEarlyEndDate(earlyEndDate);
                task.setStartDate(maxEarlyEnd);
                task.setEndDate(earlyEndDate);
            }
        }


        // Step 5: Initialize lateStart and lateEnd for leaf tasks
        Collections.reverse(result); // Reversing list to process leaf nodes
        for (Integer cur : result) {
            Task task = taskRepo.findById(cur).orElse(null);
            List<Integer> dependedTasks = adj.get(cur);

            if (dependedTasks.isEmpty()) {
                // This is a leaf task
                task.setLateEndDate(task.getEarlyEndDate());
                LocalDate lateStartDate = task.getLateEndDate().minusDays(task.getDuration());
                task.setLateStartDate(lateStartDate);
            }
        }

        // Step 6: Update lateStart and lateEnd for non-leaf tasks
        for (Integer cur : result) {
            Task task = taskRepo.findById(cur).orElse(null);
            if (task.getLateStartDate() != null && task.getLateEndDate() != null) {
                if (task.getEarlyEndDate().isEqual(task.getLateEndDate()) &&
                        task.getEarlyStartDate().isEqual(task.getLateStartDate())) {
                    task.setCritical(true);
                }
                else task.setCritical(false);
                continue; // Skip if lateStart and lateEnd are already set
            }

            List<Integer> dependedTasks = adj.get(cur);
            if (!dependedTasks.isEmpty()) {
                LocalDate minLateStart = LocalDate.now().plusYears(1); // set to next year

                for (Integer dep : dependedTasks) {
                    Task depTask = taskRepo.findById(dep).orElse(null);
                    if (depTask.getLateStartDate().isBefore(minLateStart)) {
                        minLateStart = depTask.getLateStartDate();
                    }
                }

                task.setLateEndDate(minLateStart);
                LocalDate lateStartDate = minLateStart.minusDays(task.getDuration());
                task.setLateStartDate(lateStartDate);

                if (task.getEarlyEndDate().isEqual(task.getLateEndDate()) &&
                        task.getEarlyStartDate().isEqual(task.getLateStartDate())) {
                    task.setCritical(true);
                }
                else task.setCritical(false);
            }
        }
        return tasks; // Return the ordered task list after processing
    }






}

