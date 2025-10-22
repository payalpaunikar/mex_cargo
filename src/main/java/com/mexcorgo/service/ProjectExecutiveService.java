package com.mexcorgo.service;


import com.mexcorgo.datamodel.ProjectTask;
import com.mexcorgo.datamodel.QuatationProjectExecutive;
import com.mexcorgo.dto.response.PlanningQuatation;
import com.mexcorgo.dto.response.ProjectQuatation;
import com.mexcorgo.exception.ResourceNotFoundException;
import com.mexcorgo.repository.ProjectTaskRepository;
import com.mexcorgo.repository.QuatationProjectExecutiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectExecutiveService {

      private QuatationProjectExecutiveRepository quatationProjectExecutiveRepository;

      private ProjectTaskRepository projectTaskRepository;

    @Autowired
    public ProjectExecutiveService(QuatationProjectExecutiveRepository quatationProjectExecutiveRepository,
                                   ProjectTaskRepository projectTaskRepository) {
        this.quatationProjectExecutiveRepository = quatationProjectExecutiveRepository;
        this.projectTaskRepository = projectTaskRepository;
    }

    public List<ProjectQuatation> getProjectExecutiveReceivedQuatation(Long projectExecutiveId){
        return quatationProjectExecutiveRepository.findQuatationDataByProjectExecutiveId(projectExecutiveId);
    }

    public List<ProjectTask> getAllTask(Long quatationProjectExecutiveId){

        List<ProjectTask> projectTasks = projectTaskRepository
                .findByQuatationProjectExecutive_QuatationProjectExecutiveId(quatationProjectExecutiveId)
                .orElseThrow(()-> new ResourceNotFoundException("Quatataion project still not created the task"));


         return projectTasks;
    }


    public ProjectTask getTaskById(Long taskId){

     ProjectTask projectTask = projectTaskRepository.findById(taskId)
             .orElseThrow(()-> new ResourceNotFoundException("task not found"));

     return projectTask;
    }

}
