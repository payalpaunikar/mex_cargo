package com.mexcorgo.service;


import com.mexcorgo.component.ServiceType;
import com.mexcorgo.datamodel.*;
import com.mexcorgo.dto.request.AddMasterWithMasterResponseDto;
import com.mexcorgo.dto.request.SendEmailToAccountExecutiveRequest;
import com.mexcorgo.dto.request.SendEmailToProjectExecutiveRequest;
import com.mexcorgo.dto.response.ForwardedExecutiveDTO;
import com.mexcorgo.dto.response.NeedEmailDetails;
import com.mexcorgo.dto.response.PlanningQuatation;
import com.mexcorgo.dto.response.QutationSelectedMasterForServiceResponse;
import com.mexcorgo.exception.CustomException;
import com.mexcorgo.exception.ResourceNotFoundException;
import com.mexcorgo.repository.*;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

@Service
public class PlanningExecutiveQuatationService {

       private QuatationRepository quatationRepository;

       private QuatationPlanningExecutiveRepository quatationPlanningExecutiveRepository;

       private PlanningSelectionMasterRepository planningSelectionMasterRepository;

       private LeadRepository leadRepository;

       private NotificationService notificationService;

       private UserRepository userRepository;

       private QuatationProjectExecutiveRepository quatationProjectExecutiveRepository;

       private QuatationAccountExecutiveRepository quatationAccountExecutiveRepository;

       private MasterRepository masterRepository;

       private QuatationMasterRepository quatationMasterRepository;

       private MasterQuatationResponseRepository masterQuatationResponseRepository;

    @Autowired
    public PlanningExecutiveQuatationService(QuatationRepository quatationRepository,
                                             QuatationPlanningExecutiveRepository quatationPlanningExecutiveRepository,
                                             LeadRepository leadRepository,NotificationService notificationService,
                                             PlanningSelectionMasterRepository planningSelectionMasterRepository,
                                             UserRepository userRepository,QuatationProjectExecutiveRepository quatationProjectExecutiveRepository,
                                             QuatationAccountExecutiveRepository quatationAccountExecutiveRepository,
                                             MasterRepository masterRepository,
                                             QuatationMasterRepository quatationMasterRepository,
                                             MasterQuatationResponseRepository masterQuatationResponseRepository) {
        this.quatationRepository = quatationRepository;
        this.quatationPlanningExecutiveRepository = quatationPlanningExecutiveRepository;
        this.leadRepository = leadRepository;
        this.notificationService = notificationService;
        this.planningSelectionMasterRepository = planningSelectionMasterRepository;
        this.userRepository = userRepository;
        this.quatationProjectExecutiveRepository = quatationProjectExecutiveRepository;
        this.quatationAccountExecutiveRepository = quatationAccountExecutiveRepository;
        this.masterRepository = masterRepository;
        this.quatationMasterRepository = quatationMasterRepository;
        this.masterQuatationResponseRepository = masterQuatationResponseRepository;
    }


    public List<PlanningQuatation> getPlanningExecutiveReceivedQuatation(Long planningExecutiveId){
     return quatationPlanningExecutiveRepository.findQuatationWhichReceivePlanningExecutiveByExecutiveId(planningExecutiveId);
    }


    public boolean areAllServicesSelected(Long quotationId) {
        List<ServiceType> selectedTypes = planningSelectionMasterRepository.findSelectedServiceTypesByQuotationId(quotationId);

        return selectedTypes.containsAll(Arrays.asList(
                ServiceType.PACKAGE,
                ServiceType.TRS,
                ServiceType.CAR,
                ServiceType.ADDITIONAL_SERVICE
        ));

    }


    public String sendEmailToProject(SendEmailToProjectExecutiveRequest sendEmailToProjectExecutiveRequest) {

        Lead lead = leadRepository.findById(sendEmailToProjectExecutiveRequest.getLeadId())
                .orElseThrow(()-> new ResourceNotFoundException("Lead with id : "+sendEmailToProjectExecutiveRequest.getLeadId()+" is not found"));

        Quatation quatation = quatationRepository.findByLead_LeadId(sendEmailToProjectExecutiveRequest.getLeadId());

        Need need = lead.getNeed();

        NeedEmailDetails needEmailDetails = new NeedEmailDetails(
                need.getSource(),
                need.getDestination(),
                need.getCommodity(),
                need.getSize(),
                need.getWeight(),
                need.getTypeOfTransporatation(),
                need.getMovingDateAndTime(),
                need.getOtherServices(),
                need.getCarTransport(),
                need.getCarMovingDate(),
                need.getCarMovingTime()
        );

        User projectExecutive = userRepository.findById(sendEmailToProjectExecutiveRequest.getProjectExecutiveIds())
                .orElseThrow(()-> new ResourceNotFoundException("user with id : "+sendEmailToProjectExecutiveRequest.getProjectExecutiveIds()+" not found"));

        List<QutationSelectedMasterForServiceResponse> qutationSelectedMasterForServiceResponses = planningSelectionMasterRepository.findByQuatationQuatationId(quatation.getQuatationId());


        QuatationProjectExecutive quatationProjectExecutive = new QuatationProjectExecutive();
        quatationProjectExecutive.setQuatation(quatation);
        quatationProjectExecutive.setProjectExecutive(projectExecutive);
        quatationProjectExecutive.setQuatationForwardDate(LocalDate.now());
        quatationProjectExecutive.setQuatationForwardTime(LocalTime.now());

        quatationProjectExecutiveRepository.save(quatationProjectExecutive);

        notificationService.sendEmailToProjectExecutives(lead,quatation,
                needEmailDetails,qutationSelectedMasterForServiceResponses,projectExecutive.getEmail());

        return "Email sent to Project Department.";
    }


    public String sendEmailToAccount(SendEmailToAccountExecutiveRequest sendEmailToAccountExecutiveRequest) {

        Lead lead = leadRepository.findById(sendEmailToAccountExecutiveRequest.getLeadId())
                .orElseThrow(()-> new ResourceNotFoundException("Lead with id : "+sendEmailToAccountExecutiveRequest.getLeadId()+" is not found"));

        Quatation quatation = quatationRepository.findByLead_LeadId(sendEmailToAccountExecutiveRequest.getLeadId());

        Need need = lead.getNeed();

        NeedEmailDetails needEmailDetails = new NeedEmailDetails(
                need.getSource(),
                need.getDestination(),
                need.getCommodity(),
                need.getSize(),
                need.getWeight(),
                need.getTypeOfTransporatation(),
                need.getMovingDateAndTime(),
                need.getOtherServices(),
                need.getCarTransport(),
                need.getCarMovingDate(),
                need.getCarMovingTime()
        );

        User accountExecutive = userRepository.findById(sendEmailToAccountExecutiveRequest.getAccountExecutiveId())
                .orElseThrow(()-> new ResourceNotFoundException("user with id : "+sendEmailToAccountExecutiveRequest.getAccountExecutiveId()+" not found"));

        List<QutationSelectedMasterForServiceResponse> qutationSelectedMasterForServiceResponses = planningSelectionMasterRepository.findByQuatationQuatationId(quatation.getQuatationId());

        QuatationAccountExecutive quatationAccountExecutive = quatationAccountExecutiveRepository.findByQuatationQuatationId(quatation.getQuatationId())
                        .orElseThrow(()-> new ResourceNotFoundException("Quatation Account executive not found"));

        quatationAccountExecutive.setIsServiceDataForwarded(true);
        quatationAccountExecutive.setServiceForwardedDate(LocalDate.now());
        quatationAccountExecutive.setServiceForwardedTime(LocalTime.now());

        quatationAccountExecutiveRepository.save(quatationAccountExecutive);

        notificationService.sendEmailToAccountExecutive(
                lead,
                quatation,
                needEmailDetails,
                qutationSelectedMasterForServiceResponses,
                accountExecutive.getEmail()
        );

        return "Email sent to Account Department.";
    }


    public List<ForwardedExecutiveDTO> getAllProjectExecutivesForQuotation(Long quotationId) {
       return quatationProjectExecutiveRepository.findByProjectExecutiveByQutationId(quotationId);
    }


    public List<ForwardedExecutiveDTO> getAccountExecutives(Long quotationId) {
        List<ForwardedExecutiveDTO> list = quatationAccountExecutiveRepository.getAllAccountExecutivesWithForwardedService(quotationId);

        if (list.isEmpty()) {
            throw new CustomException("Service data not forwarded yet for this quotation.");
        }

        return list;
    }


    public String addMasterWithMasterResponseInTheQuatation(Long quatationId, AddMasterWithMasterResponseDto addMasterWithMasterResponseDto){

        Quatation quatation = quatationRepository.findById(quatationId)
                .orElseThrow(()-> new ResourceNotFoundException("Quatation with id : "+quatationId+" is not found"));

        Master master = new Master();
        master.setAssociateCode(addMasterWithMasterResponseDto.getAssociateCode());
        master.setServiceSector(addMasterWithMasterResponseDto.getServiceSector());
        master.setCompanyName(addMasterWithMasterResponseDto.getCompanyName());
        master.setContactName(addMasterWithMasterResponseDto.getContactName());
        master.setContactNumber(addMasterWithMasterResponseDto.getContactNumber());
        master.setEmailId(addMasterWithMasterResponseDto.getEmailId());
        master.setHub(addMasterWithMasterResponseDto.getHub());
        master.setState(addMasterWithMasterResponseDto.getState());
        master.setGrade(addMasterWithMasterResponseDto.getGrade());
        master.setLocation(addMasterWithMasterResponseDto.getLocation());

        Master saveMaster =  masterRepository.save(master);

        QuatationMaster quatationMaster = new QuatationMaster();
        quatationMaster.setMaster(saveMaster);
        quatationMaster.setQuatation(quatation);
        quatationMaster.setQuatationForwardDate(LocalDate.now());
        quatationMaster.setQuatationForwardTime(LocalTime.now());

       QuatationMaster saveQuatationMaster = quatationMasterRepository.save(quatationMaster);

       MasterQuatationResponse masterQuatationResponse = new MasterQuatationResponse();
       masterQuatationResponse.setQuatationMaster(saveQuatationMaster);
       masterQuatationResponse.setResponseDate(LocalDateTime.now());
       masterQuatationResponse.setOriginalPackageCost(addMasterWithMasterResponseDto.getOriginalPackageCost());
       masterQuatationResponse.setOriginalTrsCost(addMasterWithMasterResponseDto.getOriginalTrsCost());
       masterQuatationResponse.setOriginalCarServiceCost(addMasterWithMasterResponseDto.getOriginalCarServiceCost());
       masterQuatationResponse.setOriginalAdditionalServiceCost(addMasterWithMasterResponseDto.getOriginalAdditionalServiceCost());
       masterQuatationResponse.setFinalPackageCost(addMasterWithMasterResponseDto.getFinalPackageCost());
       masterQuatationResponse.setFinalTrsCost(addMasterWithMasterResponseDto.getFinalTrsCost());
       masterQuatationResponse.setFinalCarServiceCost(addMasterWithMasterResponseDto.getFinalCarServiceCost());
       masterQuatationResponse.setFinalAdditionalServiceCost(addMasterWithMasterResponseDto.getFinalAdditionalServiceCost());

       masterQuatationResponseRepository.save(masterQuatationResponse);


       return "Master with master response add succuefully in the quatation";
    }


}
