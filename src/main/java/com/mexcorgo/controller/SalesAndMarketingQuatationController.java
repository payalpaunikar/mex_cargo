package com.mexcorgo.controller;


import com.mexcorgo.datamodel.Quatation;
import com.mexcorgo.datamodel.QuatationParticularsAmount;
import com.mexcorgo.datamodel.User;
import com.mexcorgo.dto.request.AddParticularsRequestDto;
import com.mexcorgo.dto.request.AddQuatationWithPurchaseExcutive;
import com.mexcorgo.dto.request.NeedExtraDetailsRequestDto;
import com.mexcorgo.dto.request.SendEmailToPlanningRequest;
import com.mexcorgo.dto.response.*;
import com.mexcorgo.security.MyUserDetails;
import com.mexcorgo.service.SalesAndMarketingQuatationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/quatation")
@PreAuthorize("hasAnyRole('Admin','Head','Leader','Member')")
public class SalesAndMarketingQuatationController {

    private SalesAndMarketingQuatationService salesAndMarketingQuatationService;

    @Autowired
    public SalesAndMarketingQuatationController(SalesAndMarketingQuatationService salesAndMarketingQuatationService) {
        this.salesAndMarketingQuatationService = salesAndMarketingQuatationService;
    }

      @PostMapping("/add")
      public QuatationWithPurchaseExcutiveResponse addQuatation(@RequestBody AddQuatationWithPurchaseExcutive addQuatationWithPurchaseExcutive){
        return salesAndMarketingQuatationService.addQuatation(addQuatationWithPurchaseExcutive);
      }


      @GetMapping("/required/data")
      public List<SalesQuatation> getQuatationRequiredDataToSalesAndMarketingDepartment(Authentication authentication){

          User currentUser = ((MyUserDetails) authentication.getPrincipal()).getUser();

          return salesAndMarketingQuatationService.getQuatationRequiredDataToSalesAndMarketingDepartment(currentUser.getUserId());
      }


      @GetMapping("/{quatationId}/purchaseexecutives")
      public List<PurchaseEmpResponse> viewPurchaseExecutiveByQuatationId(@PathVariable("quatationId")Long quatationId){
        return salesAndMarketingQuatationService.viewPurchaseExecutiveByQuatationId(quatationId);
      }


    @GetMapping("/get/which-is-analyze-pricing")
    public List<SalesQuatation> getQuatationWhichPricingIsFinalized(Authentication authentication){
        User currentUser = ((MyUserDetails)authentication.getPrincipal()).getUser();
        return salesAndMarketingQuatationService.getQuatationWhichPricingIsFinalized(currentUser);
    }


//    @GetMapping("/{quatationId}/final/master-with-pricing")
//    public FinalQuatationPricingWithMasterDto viewMasterWithFinalPricing(@PathVariable("quatationId")Long quatationId){
//       return salesAndMarketingQuatationService.viewMasterWithFinalPricing(quatationId);
//    }


//    @PostMapping("/{quatationId}/add-particular/send-to-user")
//    public ApiResponse addQuatationParticularsAndAmountAndSendToEndUser(@PathVariable("quatationId")Long quatationId,
//                                                                        @RequestBody AddParticularsRequestDto addParticularsRequestDto){
//
//        return salesAndMarketingQuatationService.addQuatationParticularsAmountAndSendToUser(quatationId,addParticularsRequestDto);
//    }



      @PostMapping("/{quatationId}/add-particular-amount")
      public QuatationParticularsAmount addQuatationParticularAmount(@PathVariable("quatationId")Long quatationId,
                                                                     @RequestBody AddParticularsRequestDto addParticularsRequestDto){
        return salesAndMarketingQuatationService.addParticularAmount(quatationId,addParticularsRequestDto);
      }


    @GetMapping("/{quatationId}/get-particular-amount")
    public QuatationParticularsAmount getQuatationParticularAmount(@PathVariable("quatationId")Long quatationId){
        return salesAndMarketingQuatationService.getParticularAmount(quatationId);
    }

    @PutMapping("/{quatationId}/update-particular-amount")
    public QuatationParticularsAmount updateQuatationParticularAmount(@PathVariable("quatationId")Long quatationId,
                                                                      @RequestBody AddParticularsRequestDto particularsRequestDto){
        return salesAndMarketingQuatationService.updateParticularAmount(quatationId,particularsRequestDto);
    }

    @PostMapping("/{quatationId}/particular-amount/send-to-end-user")
    public ResponseEntity<ApiResponse> sendQuatationParticularAmountToTheUser(@PathVariable("quatationId")Long quatationId){
      return ResponseEntity.ok(salesAndMarketingQuatationService.sendParticularAmountToTheEndUser(quatationId));
    }

    @GetMapping("/{quatationId}/get/planning-executive")
    public List<PlanningEmpResponse> viewPlanningExecutivesByQuatationId(@PathVariable("quatationId")Long quatationId){
      return salesAndMarketingQuatationService.viewPlanningExecutivesByQuatationId(quatationId);
    }


    @GetMapping("/{quatationId}/get/account-executives")
    public List<AccountEmpResponse> viewAccountExecutivesByQuatationId(@PathVariable("quatationId")Long quatationId){
        return salesAndMarketingQuatationService.viewAccountExecutivesByQuatationId(quatationId);
    }




}
