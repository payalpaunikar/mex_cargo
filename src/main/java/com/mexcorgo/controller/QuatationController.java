package com.mexcorgo.controller;


import com.mexcorgo.dto.response.AddQuatationAnalyzePricingResponse;
import com.mexcorgo.service.QuatationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuatationController {


       private QuatationService quatationService;

    @Autowired
    public QuatationController(QuatationService quatationService) {
        this.quatationService = quatationService;
    }

    @GetMapping("/get/quatation/{quatationId}/analyze-pricing")
    @PreAuthorize("hasAnyRole('Admin','Head','Leader','Member','Pricing Executive')")
    public AddQuatationAnalyzePricingResponse getQuatationAnalyzePricingByQuatationId(@PathVariable("quatationId")Long quatationId){

        return quatationService.getQuatationAnalyzePricingByQuatationId(quatationId);
    }
}
