package com.mexcorgo.service;


import com.mexcorgo.datamodel.*;
import com.mexcorgo.dto.QuatationMasterPricingResponseSqlDto;
import com.mexcorgo.dto.request.SendEmailToAccountExecutiveRequest;
import com.mexcorgo.dto.request.SendEmailToPlanningRequest;
import com.mexcorgo.dto.response.ApiResponse;
import com.mexcorgo.dto.response.ClientDetailsWhichWeTransferToAccountExcutiveDto;
import com.mexcorgo.exception.ResourceNotFoundException;
import com.mexcorgo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SalesAndMarketingTaskService {

      private SalesAndMarketingTaskRepository salesAndMarketingTaskRepository;

      private LeadRepository leadRepository;
      private QuatationRepository quatationRepository;
      private UserRepository userRepository;
      private MasterQuatationResponseRepository masterQuatationResponseRepository;
      private NotificationService notificationService;
      private QuatationPlanningExecutiveRepository quatationPlanningExecutiveRepository;

     private QuatationParticularsAmountRepository quatationParticularsAmountRepository;

     private QuatationAccountExecutiveRepository quatationAccountExecutiveRepository;

     @Autowired
     public SalesAndMarketingTaskService(SalesAndMarketingTaskRepository salesAndMarketingTaskRepository, LeadRepository leadRepository, QuatationRepository quatationRepository, UserRepository userRepository, MasterQuatationResponseRepository masterQuatationResponseRepository, NotificationService notificationService, QuatationPlanningExecutiveRepository quatationPlanningExecutiveRepository,
                                         QuatationParticularsAmountRepository quatationParticularsAmountRepository,
                                         QuatationAccountExecutiveRepository quatationAccountExecutiveRepository) {
          this.salesAndMarketingTaskRepository = salesAndMarketingTaskRepository;
          this.leadRepository = leadRepository;
          this.quatationRepository = quatationRepository;
          this.userRepository = userRepository;
          this.masterQuatationResponseRepository = masterQuatationResponseRepository;
          this.notificationService = notificationService;
          this.quatationPlanningExecutiveRepository = quatationPlanningExecutiveRepository;
          this.quatationParticularsAmountRepository = quatationParticularsAmountRepository;
          this.quatationAccountExecutiveRepository = quatationAccountExecutiveRepository;
     }

     public List<SalesAndMarketingTasks> getTasksByLead(Long leadId) {
          return salesAndMarketingTaskRepository.findByLead_LeadId(leadId);
     }


     @Transactional
     public ApiResponse sendEndUserDetailsNeedWithQuatationDetailsToPlanningExecutive(SendEmailToPlanningRequest sendEmailToPlanningRequest){

          Lead lead = leadRepository.findById(sendEmailToPlanningRequest.getLeadId())
                  .orElseThrow(()-> new ResourceNotFoundException("Lead with id : "+sendEmailToPlanningRequest.getLeadId()+" is not found"));

          Quatation quatation = quatationRepository.findByLead_LeadId(sendEmailToPlanningRequest.getLeadId());
          List<QuatationMasterPricingResponseSqlDto> quatationMasterPricingResponseSqlDtoList = masterQuatationResponseRepository.findQuatationAndMasterPricingByQuatationId(quatation.getQuatationId());

          Need need = lead.getNeed();
          EndUserDetails endUserDetails = lead.getEndUserDetails();

          Set<User> getPlanningExcutiveList =  userRepository.findUsersByIds(sendEmailToPlanningRequest.getPlanningExecutiveIds());

          List<QuatationPlanningExecutive> quatationPlanningExecutiveList = new ArrayList<>();

          for (User planningExecutive : getPlanningExcutiveList){
               QuatationPlanningExecutive quatationPlanningExecutive = new QuatationPlanningExecutive();
               quatationPlanningExecutive.setQuatation(quatation);
               quatationPlanningExecutive.setPlanningExceutive(planningExecutive);
               quatationPlanningExecutive.setQuatationForwardDate(LocalDate.now());
               quatationPlanningExecutive.setQuatationForwardTime(LocalTime.now());

               quatationPlanningExecutiveList.add(quatationPlanningExecutive);
          }

          quatationPlanningExecutiveRepository.saveAll(quatationPlanningExecutiveList);


          notificationService.sendEndUserDetailsWithNeedWithQuatationDetailsToPlanningExecutive(
                  getPlanningExcutiveList,
                  lead,
                  quatation,
                  quatationMasterPricingResponseSqlDtoList,
                  need,
                  endUserDetails
          );

          Map<String,Object> responseData = new HashMap<>();
          responseData.put("QuationId",quatation.getQuatationId());
          responseData.put("notifiedUserEmail",getPlanningExcutiveList.stream().map(User::getEmail).collect(Collectors.toList()));

          return new ApiResponse(true,"Send EndUser Details with Qutation Details to Planning Executives",responseData);
     }

     @Transactional
     public ApiResponse sendParticularAmountToAccountExcutives(SendEmailToAccountExecutiveRequest accountExecutiveRequest){
        Lead lead = leadRepository.findById(accountExecutiveRequest.getLeadId())
                .orElseThrow(()-> new ResourceNotFoundException("Lead with id : "+accountExecutiveRequest.getLeadId()+" is not found"));

        Quatation quatation = quatationRepository.findByLead_LeadId(accountExecutiveRequest.getLeadId());

        User accountExecutive = userRepository.findById(accountExecutiveRequest.getAccountExecutiveId())
                .orElseThrow(()-> new ResourceNotFoundException("Account executive not found with id : "+accountExecutiveRequest.getAccountExecutiveId()));

        EndUserDetails endUserDetails = lead.getEndUserDetails();

          ClientDetailsWhichWeTransferToAccountExcutiveDto clientDetails = new ClientDetailsWhichWeTransferToAccountExcutiveDto();
          clientDetails.setLeadReferenceNo(lead.getLeadReferenceNo());
          clientDetails.setUserName(endUserDetails.getUserName());
          clientDetails.setMailId(endUserDetails.getMailId());
          clientDetails.setContactNo(endUserDetails.getContactNo());

          QuatationAccountExecutive quatationAccountExecutive = new QuatationAccountExecutive();
          quatationAccountExecutive.setQuatation(quatation);
          quatationAccountExecutive.setAccountExecutive(accountExecutive);
          quatationAccountExecutive.setQuatationForwardDate(LocalDate.now());
          quatationAccountExecutive.setQuatationForwardTime(LocalTime.now());

          quatationAccountExecutiveRepository.save(quatationAccountExecutive);

          QuatationParticularsAmount quatationParticularsAmount = quatationParticularsAmountRepository.findByQuatation_QuatationId(quatation.getQuatationId());


          notificationService.sendClientDetailsWithParticularAmountToAccountExecutive(
                  clientDetails,
                  quatationParticularsAmount,
                  accountExecutive
          );


          Map<String,Object> responseData = new HashMap<>();
          responseData.put("QuationId",quatation.getQuatationId());
          responseData.put("notifiedUserEmail",accountExecutive.getEmail());

          return new ApiResponse(true,"Send Client Particular amount details To Account Executive",responseData);


     }


}
