package com.mexcorgo.controller;


import com.mexcorgo.datamodel.ProjectTask;
import com.mexcorgo.datamodel.QuatationProjectExecutive;
import com.mexcorgo.dto.request.ProjectTaskCreateDto;
import com.mexcorgo.dto.request.ProjectTaskUpdateDto;
import com.mexcorgo.dto.response.ProjectQuatation;
import com.mexcorgo.repository.ProjectTaskRepository;
import com.mexcorgo.repository.QuatationProjectExecutiveRepository;
import com.mexcorgo.service.ProjectExecutiveService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProjectExecutiveQuatationController {

    private ProjectExecutiveService projectExecutiveService;

    private ProjectTaskRepository projectTaskRepository;

    private QuatationProjectExecutiveRepository quatationProjectExecutiveRepository;

    @Autowired
    public ProjectExecutiveQuatationController(ProjectExecutiveService projectExecutiveService, ProjectTaskRepository projectTaskRepository, QuatationProjectExecutiveRepository quatationProjectExecutiveRepository) {
        this.projectExecutiveService = projectExecutiveService;
        this.projectTaskRepository = projectTaskRepository;
        this.quatationProjectExecutiveRepository = quatationProjectExecutiveRepository;
    }

    @GetMapping("/get/project-executive/{projectExecutiveId}/quatation")
    @PreAuthorize("hasRole('Project Executive')")
    public List<ProjectQuatation> getProjectExecutiveReceivedQuatation(@PathVariable("projectExecutiveId") Long projectExecutiveId){
        return projectExecutiveService.getProjectExecutiveReceivedQuatation(projectExecutiveId);
    }


    @PostMapping("/project-executive/create/task")
    @PreAuthorize("hasRole('Project Executive')")
    public ResponseEntity<?> createTask(@RequestBody ProjectTaskCreateDto dto) {
        Optional<QuatationProjectExecutive> qpeOpt = quatationProjectExecutiveRepository.findById(dto.getQuatationProjectExecutiveId());
        if (qpeOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid Quotation Project Executive ID");
        }

        ProjectTask task = new ProjectTask();
        task.setTaskName(dto.getTaskName());
        task.setIdealDate(dto.getIdealDate());
        task.setIdealTime(dto.getIdealTime());
        task.setImplementedDate(dto.getImplementedDate());
        task.setImplementedTime(dto.getImplementedTime());
        task.setQuatationProjectExecutive(qpeOpt.get());

        ProjectTask saved = projectTaskRepository.save(task);
        return ResponseEntity.ok(saved);
    }


    @PutMapping("/project-executive/update/task/{taskId}")
    @PreAuthorize("hasRole('Project Executive')")
    public ResponseEntity<?> updateTask(@PathVariable Long taskId, @RequestBody ProjectTaskUpdateDto dto) {
        Optional<ProjectTask> taskOpt = projectTaskRepository.findById(taskId);
        if (taskOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
        }

        Optional<QuatationProjectExecutive> qpeOpt = quatationProjectExecutiveRepository.findById(dto.getQuatationProjectExecutiveId());
        if (qpeOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Quotation Project Executive ID");
        }

        ProjectTask task = taskOpt.get();
        task.setTaskName(dto.getTaskName());
        task.setIdealDate(dto.getIdealDate());
        task.setIdealTime(dto.getIdealTime());
        task.setImplementedDate(dto.getImplementedDate());
        task.setImplementedTime(dto.getImplementedTime());
        task.setQuatationProjectExecutive(qpeOpt.get());

        ProjectTask updated = projectTaskRepository.save(task);
        return ResponseEntity.ok(updated);
    }


    @DeleteMapping("/project-executive/delete/task/{taskId}")
    @PreAuthorize("hasRole('Project Executive')")
    public ResponseEntity<?> deleteTask(@PathVariable Long taskId) {
        Optional<ProjectTask> taskOpt = projectTaskRepository.findById(taskId);
        if (taskOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
        }

        projectTaskRepository.delete(taskOpt.get());
        return ResponseEntity.ok("Task deleted successfully");
    }


    @GetMapping("/project-executive/{quatationProjectExceutiveId}/project-tasks")
    @PreAuthorize("hasRole('Project Executive')")
    public List<ProjectTask> getAllTask(@PathVariable Long quatationProjectExceutiveId){
       return projectExecutiveService.getAllTask(quatationProjectExceutiveId);
    }

    @GetMapping("/project-executive/project-task/{taskId}")
    @PreAuthorize("hasRole('Project Executive')")
    public ProjectTask getTaskById(@PathVariable Long taskId){
      return projectExecutiveService.getTaskById(taskId);
    }
}
