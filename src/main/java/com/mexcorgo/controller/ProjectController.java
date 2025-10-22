package com.mexcorgo.controller;


import com.mexcorgo.datamodel.User;
import com.mexcorgo.dto.response.ProjectEmpResponse;
import com.mexcorgo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProjectController {

        private UserRepository userRepository;

    @Autowired
    public ProjectController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @GetMapping("/get/all/project-executives")
    @PreAuthorize("hasRole('Planning Executive')")
    public List<ProjectEmpResponse> getAllProjectExecutive(){
        List<User> projectExecutives = userRepository.findAllProjectExecutive();

        List<ProjectEmpResponse> projectEmpResponses = projectExecutives.stream()
                .map(projectExecutive -> {
                    ProjectEmpResponse projectEmpResponse = new ProjectEmpResponse();
                    projectEmpResponse.setUserId(projectExecutive.getUserId());
                    projectEmpResponse.setEmail(projectExecutive.getEmail());
                    projectEmpResponse.setUserName(projectExecutive.getUserName());
                    projectEmpResponse.setMobileNumber(projectExecutive.getMobileNumber());

                    return projectEmpResponse;
                }).toList();

        return projectEmpResponses;
    }
}
