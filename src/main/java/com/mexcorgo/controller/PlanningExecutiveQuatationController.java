package com.mexcorgo.controller;


import com.mexcorgo.datamodel.PlanningSelectionMaster;
import com.mexcorgo.datamodel.Quatation;
import com.mexcorgo.datamodel.QuatationMaster;
import com.mexcorgo.dto.request.AddMasterWithMasterResponseDto;
import com.mexcorgo.dto.request.PlanningSelectionMasterRequest;
import com.mexcorgo.dto.request.SendEmailToAccountExecutiveRequest;
import com.mexcorgo.dto.request.SendEmailToProjectExecutiveRequest;
import com.mexcorgo.dto.response.ForwardedExecutiveDTO;
import com.mexcorgo.dto.response.PlanningQuatation;
import com.mexcorgo.dto.response.QutationSelectedMasterForServiceResponse;
import com.mexcorgo.exception.ResourceNotFoundException;
import com.mexcorgo.repository.PlanningSelectionMasterRepository;
import com.mexcorgo.repository.QuatationMasterRepository;
import com.mexcorgo.repository.QuatationRepository;
import com.mexcorgo.service.PlanningExecutiveQuatationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
public class PlanningExecutiveQuatationController {

          private PlanningExecutiveQuatationService planningExecutiveQuatationService;

          private PlanningSelectionMasterRepository planningSelectionMasterRepository;

          private QuatationRepository quatationRepository;

          private QuatationMasterRepository quatationMasterRepository;


    @Autowired
    public PlanningExecutiveQuatationController(PlanningExecutiveQuatationService planningExecutiveQuatationService, PlanningSelectionMasterRepository planningSelectionMasterRepository, QuatationRepository quatationRepository,
                                                QuatationMasterRepository quatationMasterRepository) {
        this.planningExecutiveQuatationService = planningExecutiveQuatationService;
        this.planningSelectionMasterRepository = planningSelectionMasterRepository;
        this.quatationRepository = quatationRepository;
        this.quatationMasterRepository = quatationMasterRepository;
    }

    @GetMapping("/planning-executive/{planningExecutiveId}/quatation")
    @PreAuthorize("hasAnyRole('Planning Executive')")
    public List<PlanningQuatation> getPlanningExecutiveQuatationByQuatationId(@PathVariable("planningExecutiveId")Long planningExecutiveId){
        return planningExecutiveQuatationService.getPlanningExecutiveReceivedQuatation(planningExecutiveId);
    }


    @PostMapping("/select/master-for-service")
    @PreAuthorize("hasAnyRole('Planning Executive')")
    public ResponseEntity<String> selectMasterForTheService(@RequestBody PlanningSelectionMasterRequest planningSelectionMasterRequest){

        boolean alreadyServiceAddedForQuatation = planningSelectionMasterRepository
                .findByQuatationQuatationIdAndServiceType(planningSelectionMasterRequest.getQuatationId(),planningSelectionMasterRequest.getServiceType())
                .isPresent();


          if(alreadyServiceAddedForQuatation){
              return ResponseEntity.badRequest().body("This service type is already selected for the quotation.");
          }

        Quatation quatation = quatationRepository.findById(planningSelectionMasterRequest.getQuatationId())
                .orElseThrow(()-> new ResourceNotFoundException("Quatation with id : "+planningSelectionMasterRequest.getQuatationId()+" is not found"));


        QuatationMaster quatationMaster = quatationMasterRepository.findById(planningSelectionMasterRequest.getQuatationMasterId())
                .orElseThrow(()-> new ResourceNotFoundException("Qutation Master with id : "+planningSelectionMasterRequest.getQuatationMasterId()+" is not found"));

        PlanningSelectionMaster planningSelectionMaster = new PlanningSelectionMaster();
        planningSelectionMaster.setQuatation(quatation);
        planningSelectionMaster.setQuatationMaster(quatationMaster);
        planningSelectionMaster.setSelectedDate(LocalDate.now());
        planningSelectionMaster.setSelectedTime(LocalTime.now());
        planningSelectionMaster.setServiceType(planningSelectionMasterRequest.getServiceType());
        planningSelectionMaster.setFinalServiceAmount(planningSelectionMasterRequest.getFinalServiceAmount());

        planningSelectionMasterRepository.save(planningSelectionMaster);

        return ResponseEntity.ok("Master selected for service: " + planningSelectionMaster.getServiceType());
    }


    // ✅ Get all selected masters for a quotation
    @GetMapping("/get/selected-master/for/quotation/{quotationId}")
    @PreAuthorize("hasAnyRole('Planning Executive')")
    public ResponseEntity<List<QutationSelectedMasterForServiceResponse>> getSelections(@PathVariable Long quotationId) {
        List<QutationSelectedMasterForServiceResponse> selections = planningSelectionMasterRepository.findByQuatationQuatationId(quotationId);
        return ResponseEntity.ok(selections);
    }


    @PostMapping("/send-email-to-project")
    @PreAuthorize("hasRole('Planning Executive')")
    public ResponseEntity<String> sendEmailToProject(@RequestBody SendEmailToProjectExecutiveRequest sendEmailToProjectExecutiveRequest) {

        Quatation quatation = quatationRepository.findByLead_LeadId(sendEmailToProjectExecutiveRequest.getLeadId());

        if (!planningExecutiveQuatationService.areAllServicesSelected(quatation.getQuatationId())) {
            return ResponseEntity.badRequest().body("Cannot send email. All 4 services are not selected for the quotation.");
        }

        return ResponseEntity.ok(planningExecutiveQuatationService.sendEmailToProject(sendEmailToProjectExecutiveRequest));
    }


    @PostMapping("/send-email-to-account")
    @PreAuthorize("hasRole('Planning Executive')")
    public ResponseEntity<String> sendEmailToAccount(@RequestBody SendEmailToAccountExecutiveRequest sendEmailToAccountExecutiveRequest) {

        Quatation quatation = quatationRepository.findByLead_LeadId(sendEmailToAccountExecutiveRequest.getLeadId());

        if (!planningExecutiveQuatationService.areAllServicesSelected(quatation.getQuatationId())) {
            return ResponseEntity.badRequest().body("Cannot send email. All 4 services are not selected for the quotation.");
        }

        return ResponseEntity.ok(planningExecutiveQuatationService.sendEmailToAccount(sendEmailToAccountExecutiveRequest));
    }


    @GetMapping("/view-forwarded/project-executives/{quotationId}")
    @PreAuthorize("hasRole('Planning Executive')")
    public ResponseEntity<List<ForwardedExecutiveDTO>> getProjectExecutives(@PathVariable Long quotationId) {
        return ResponseEntity.ok(planningExecutiveQuatationService.getAllProjectExecutivesForQuotation(quotationId));
    }


    @GetMapping("/view-forwarded/account-executives/{quotationId}")
    @PreAuthorize("hasRole('Planning Executive')")
    public ResponseEntity<List<ForwardedExecutiveDTO>> getAccountExecutives(@PathVariable Long quotationId) {
        return ResponseEntity.ok(planningExecutiveQuatationService.getAccountExecutives(quotationId));
    }


    @PostMapping("/add/maste/master-response/in/quatation/{quatationId}")
    @PreAuthorize("hasRole('Planning Executive')")
    public ResponseEntity<String> addMasterWithMasterReponseInTheQuatation(@PathVariable("quatationId")Long quatationId,@RequestBody AddMasterWithMasterResponseDto addMasterWithMasterResponseDto){
        return ResponseEntity.ok(planningExecutiveQuatationService.addMasterWithMasterResponseInTheQuatation(quatationId,addMasterWithMasterResponseDto));
    }

}
