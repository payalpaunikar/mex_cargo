package com.mexcorgo.controller;

import com.mexcorgo.dto.response.PlanningEmpResponse;
import com.mexcorgo.service.PlanningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PlanningController {

    private PlanningService planningService;

    @Autowired
    public PlanningController(PlanningService planningService) {
        this.planningService = planningService;
    }

    @GetMapping("/get/planning-executive-list")
    @PreAuthorize("hasAnyRole('Admin','Head','Leader','Member')")
    public List<PlanningEmpResponse> getAllPlanningExecutiveList(){
        return planningService.getAllPlanningExecutiveList();
    }
}
