package com.mexcorgo.controller;


import com.mexcorgo.dto.response.PricingEmpResponse;
import com.mexcorgo.service.PricingExecutiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PricingExecutiveController {

        private PricingExecutiveService pricingExecutiveService;

    @Autowired
    public PricingExecutiveController(PricingExecutiveService pricingExecutiveService) {
        this.pricingExecutiveService = pricingExecutiveService;
    }


    @GetMapping("/get/pricing/emp/list")
    @PreAuthorize("hasAnyRole('Admin','Head','Purchase Executive')")
    public List<PricingEmpResponse> getPricingEmpList(){
        return pricingExecutiveService.getListOfPricingExecutive();
    }
}
