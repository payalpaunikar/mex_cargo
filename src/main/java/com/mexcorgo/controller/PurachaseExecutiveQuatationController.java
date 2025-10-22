package com.mexcorgo.controller;


import com.mexcorgo.datamodel.MasterQuatationResponse;
import com.mexcorgo.datamodel.User;
import com.mexcorgo.dto.request.MasterQuatationResponseDto;
import com.mexcorgo.dto.request.SendQuatationRequestToMaster;
import com.mexcorgo.dto.request.SendQuatationToPricingExecutiveRequest;
import com.mexcorgo.dto.response.ApiResponse;
import com.mexcorgo.dto.response.PurchaseQuatation;
import com.mexcorgo.dto.response.SalesQuatation;
import com.mexcorgo.dto.response.SendQuatationPricingEmpResponse;
import com.mexcorgo.security.MyUserDetails;
import com.mexcorgo.service.PurchaseExecutiveQuatationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping("/purchaseexecutive/quatation")
public class PurachaseExecutiveQuatationController {

    private PurchaseExecutiveQuatationService purchaseExecutiveQuatationService;

    Logger logger = LoggerFactory.getLogger(PurachaseExecutiveQuatationController.class);

    @Autowired
    public PurachaseExecutiveQuatationController(PurchaseExecutiveQuatationService purchaseExecutiveQuatationService) {
        this.purchaseExecutiveQuatationService = purchaseExecutiveQuatationService;
    }


    @GetMapping("/purchaseexecutive/quatation/data")
    @PreAuthorize("hasAnyRole('Admin','Head','Purchase Executive')")
    public List<PurchaseQuatation> getQuatationsWhichBelongToPurchaseExecutive(Authentication authentication){
        logger.info("get quatation which belong to purchase executive is hit");
        User currentUser = ((MyUserDetails) authentication.getPrincipal()).getUser();
       return purchaseExecutiveQuatationService.getQuatationFromSalesAndMarketing(currentUser.getUserId());
    }


    @PostMapping("/purchaseexecutive/quatation/send-to-masters")
    @PreAuthorize("hasAnyRole('Admin','Head','Purchase Executive','Planning Executive')")
    public ResponseEntity<ApiResponse> sendQuatationToMaster(@RequestBody SendQuatationRequestToMaster sendQuatationRequestToMaster){
        return ResponseEntity.ok(purchaseExecutiveQuatationService.sendQuatationToMaster(sendQuatationRequestToMaster));
    }


    @PostMapping("/purchaseexecutive/quatation/add/master-response")
    @PreAuthorize("hasAnyRole('Admin','Head','Purchase Executive','Planning Executive')")
    public ResponseEntity<MasterQuatationResponse> addMasterResponse(
            @RequestBody MasterQuatationResponseDto dto) {
        MasterQuatationResponse response = purchaseExecutiveQuatationService.addMasterResponse(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/purchaseexecutive/quatation/master/response/{masterQuatationResponseId}/update")
    @PreAuthorize("hasAnyRole('Admin','Head','Purchase Executive','Planning Executive')")
    public MasterQuatationResponse updateMasterResponse(@PathVariable("masterQuatationResponseId") Long masterQuatationResponseId,@RequestBody MasterQuatationResponseDto dto){
      return purchaseExecutiveQuatationService.updateMasterResponseByMasterQuatationResponseId(masterQuatationResponseId,dto);
    }

    @PostMapping("/purchaseexecutive/quatation/send-to-pricing-executive")
    @PreAuthorize("hasAnyRole('Admin','Head','Purchase Executive')")
    public ApiResponse sendQuatationWithMastersPricingResponseToPricingExcutive(@RequestBody SendQuatationToPricingExecutiveRequest sendQuatationToPricingExecutiveRequest){
      return purchaseExecutiveQuatationService.sendQuatationWithPricingToPricingExecutive(sendQuatationToPricingExecutiveRequest);
    }


     @GetMapping("/purchaseexecutive/quatation/which/send-to-pricing-executive")
     @PreAuthorize("hasAnyRole('Admin','Head','Purchase Executive')")
     public List<PurchaseQuatation> getQuatationWhichAreWeSendToPricingExecutive(Authentication authentication){
        User currentUser = ((MyUserDetails)authentication.getPrincipal()).getUser();
       return purchaseExecutiveQuatationService.getQuatationWhichAreWeSendToPricingExecutive(currentUser.getUserId());
     }


     @GetMapping("/purchaseexecutive/quatation/{quatationId}/list-pricing-excutive")
     @PreAuthorize("hasAnyRole('Admin','Head','Purchase Executive')")
     public List<SendQuatationPricingEmpResponse> viewPricingExecutiveListByQuatationId(@PathVariable("quatationId")Long quatationId){
       return purchaseExecutiveQuatationService.viewPricingExecutiveListByQuatationId(quatationId);
     }


}
