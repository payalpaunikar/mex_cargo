package com.mexcorgo.service;


import com.mexcorgo.datamodel.User;
import com.mexcorgo.dto.response.PlanningEmpResponse;
import com.mexcorgo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanningService {

    private UserRepository userRepository;

    @Autowired
    public PlanningService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<PlanningEmpResponse> getAllPlanningExecutiveList(){

       List<User> planningExecutiveList = userRepository.findAllPlanningExecutive();

       List<PlanningEmpResponse> planningEmpResponseList = planningExecutiveList.stream()
               .map(planningExecutive-> {
                       PlanningEmpResponse planningEmpResponse = new PlanningEmpResponse();
                       planningEmpResponse.setUserId(planningExecutive.getUserId());
                       planningEmpResponse.setEmail(planningExecutive.getEmail());
                       planningEmpResponse.setUserName(planningExecutive.getUserName());
                       planningEmpResponse.setMobileNumber(planningExecutive.getMobileNumber());
                       return planningEmpResponse;
               }).toList();

       return planningEmpResponseList;
    }
}
