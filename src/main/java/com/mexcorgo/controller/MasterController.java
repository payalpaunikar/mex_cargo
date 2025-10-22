package com.mexcorgo.controller;


import com.mexcorgo.datamodel.MasterQuatationResponse;
import com.mexcorgo.dto.request.MasterRequest;
import com.mexcorgo.dto.response.MasterResponse;
import com.mexcorgo.service.MasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MasterController {

       private MasterService masterService;

    @Autowired
    public MasterController(MasterService masterService) {
        this.masterService = masterService;
    }


    @PostMapping("/add/master")
    @PreAuthorize("hasRole('Admin')")
    public MasterResponse addMaster(@RequestBody MasterRequest masterRequest){
      return masterService.addMaster(masterRequest);
    }

    @PutMapping("/update/master/{masterId}")
    @PreAuthorize("hasRole('Admin')")
    public MasterResponse updateMaster(@PathVariable("masterId")Long masterId,@RequestBody MasterRequest masterRequest){
       return masterService.updateMaster(masterId,masterRequest);
    }


    @DeleteMapping("/delete/master/{masterId}")
    @PreAuthorize("hasRole('Admin')")
    public String deleteMaster(@PathVariable("masterId")Long masterId){
        return masterService.deleteMaster(masterId);
    }

    @GetMapping("/get/master/list")
    @PreAuthorize("hasAnyRole('Admin','Purchase Executive','Planning Executive')")
    public List<MasterResponse> getListOfMasterResponse(){
        return masterService.getListOfMaster();
    }


    @GetMapping("/get/quatation/{quatationId}/master")
    @PreAuthorize("hasAnyRole('Admin','Purchase Executive','Pricing Executive')")
    public List<MasterResponse> getQuatationMasterByQuatationId(@PathVariable("quatationId")Long quatationId){
        return masterService.getQuatationMasterByQuatationId(quatationId);
    }


    @GetMapping("/get/quatationMasterId/{quatationMasterId}/response")
    public MasterQuatationResponse getMasterResponse(@PathVariable("quatationMasterId")Long quatationMasterId){
        return masterService.getMasterResponse(quatationMasterId);
    }

}
