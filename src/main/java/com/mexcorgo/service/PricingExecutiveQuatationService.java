package com.mexcorgo.service;


import com.mexcorgo.component.QuatationStatus;
import com.mexcorgo.datamodel.*;
import com.mexcorgo.dto.request.AddQuatationAnalyzePricingDto;
import com.mexcorgo.dto.request.FinalizeQuatationRequest;
import com.mexcorgo.dto.response.*;
import com.mexcorgo.exception.ResourceNotFoundException;
import com.mexcorgo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PricingExecutiveQuatationService {

        private QuatationRepository quatationRepository;

        private LeadRepository leadRepository;

        private SendEmailService sendEmailService;

       private QuatationMasterRepository quatationMasterRepository;

       private MasterRepository masterRepository;

       private QuatationPricingExecutiveRepository quatationPricingExecutiveRepository;

       private MasterQuatationResponseRepository masterQuatationResponseRepository;

       private NotificationService notificationService;

    @Autowired
    public PricingExecutiveQuatationService(QuatationRepository quatationRepository, LeadRepository leadRepository, SendEmailService sendEmailService, QuatationMasterRepository quatationMasterRepository, MasterRepository masterRepository, QuatationPricingExecutiveRepository quatationPricingExecutiveRepository, MasterQuatationResponseRepository masterQuatationResponseRepository, NotificationService notificationService) {
        this.quatationRepository = quatationRepository;
        this.leadRepository = leadRepository;
        this.sendEmailService = sendEmailService;
        this.quatationMasterRepository = quatationMasterRepository;
        this.masterRepository = masterRepository;
        this.quatationPricingExecutiveRepository = quatationPricingExecutiveRepository;
        this.masterQuatationResponseRepository = masterQuatationResponseRepository;
        this.notificationService = notificationService;
    }

    public List<PricingQuatation> getQuatationByPricingExecutiveId(Long pricingExecutiveId){

        List<PricingQuatation> getQuatations = quatationPricingExecutiveRepository
                .findQuatationDataByPricingExecutiveId(pricingExecutiveId);

        return getQuatations;
    }


//    @Transactional
//    public ApiResponse finalizedQuatation(Long quatationId, FinalizeQuatationRequest finalizeQuatationRequest){
//
//        // fetch quatation
//        Quatation existingQuatation = quatationRepository.findById(quatationId)
//                .orElseThrow(()-> new ResourceNotFoundException("Quatation with id : "+quatationId+" is not found"));
//
//        //fetch the quatation master
//        QuatationMaster finalizedMaster = quatationMasterRepository.findById(finalizeQuatationRequest.getQuatationMasterId())
//                .orElseThrow(()-> new ResourceNotFoundException("Quatation Master With id : "+finalizeQuatationRequest.getQuatationMasterId()+" is not found"));
//
//        EmailReceiverInfo quatationCreatorInfo = quatationRepository.findQuatationCreatorEmailByQuotationId(quatationId);
//
////        // Set Finalized Quatation Master
////         existingQuatation.setFinalizedMasterResponse(finalizedMaster);
//
//         // Set Comapany Pricing
//        existingQuatation.setAnalyzePackageCost(finalizeQuatationRequest.getCompanyPackageCost());
//        existingQuatation.setAnalyzeTrsCost(finalizeQuatationRequest.getCompanyTrsCost());
//        existingQuatation.setAnalyzeCarServiceCost(finalizeQuatationRequest.getCompanyCarServiceCost());
//        existingQuatation.setAnalyzeAdditionalServiceCost(finalizeQuatationRequest.getCompanyAdditionalServiceCost());
//
//        // Set quatation reveiving pricing date and time
//        existingQuatation.setQuatationReceivedPricingDate(LocalDate.now());
//        existingQuatation.setQuatationReceivedPricingTime(LocalTime.now());
//        existingQuatation.setQuatationStatus(QuatationStatus.PRICING_FINALIZED_AND_SENT_TO_SALES);
//
//        // Save Updated Quotation
//        quatationRepository.save(existingQuatation);
//
//
////        // Fetch Master Response
//        Master master = finalizedMaster.getMaster();
//        MasterQuatationResponse masterResponse = masterQuatationResponseRepository.findMasterQuatationResponseByQuatationMasterId(finalizeQuatationRequest.getQuatationMasterId());
//
//        // Build Email Content
//        String subject = "Finalized Quotation Details - " + existingQuatation.getQuatationReferenceNo();
//        String body = buildEmailContent(existingQuatation, master, masterResponse,quatationCreatorInfo.getName());
//
//        // Send Email
//        sendEmailService.sendEmail(quatationCreatorInfo.getEmail(),subject,body);
//
//        // ✅ Prepare Response Data
//        Map<String, Object> responseData = new HashMap<>();
//        responseData.put("QuatationReferenceNo",existingQuatation.getQuatationReferenceNo());
//        responseData.put("creatorNotified",quatationCreatorInfo.getEmail());
//
//        return new ApiResponse(true,"Quotation request sent successfully",responseData);
//    }
//

    public List<QuatationMasterWithMasterResponse> getQuatationMasterWithMasterResponseByQuatationId(Long quatationId){
      return masterQuatationResponseRepository.findQuatationMastersWithMasterResponseByQuatationId(quatationId);
    }

    public AddQuatationAnalyzePricingResponse addQuatationAnalyzePricing(Long quatationId, AddQuatationAnalyzePricingDto addQuatationAnalyzePricingDto){
        Quatation quatation = quatationRepository.findById(quatationId)
                .orElseThrow(()-> new ResourceNotFoundException("Quatation with id : "+quatationId+" is not found"));

        quatation.setAnalyzePackageCost(addQuatationAnalyzePricingDto.getAnalyzePackageCost());
        quatation.setAnalyzeTrsCost(addQuatationAnalyzePricingDto.getAnalyzeTrsCost());
        quatation.setAnalyzeCarServiceCost(addQuatationAnalyzePricingDto.getAnalyzeCarServiceCost());
        quatation.setAnalyzeAdditionalServiceCost(addQuatationAnalyzePricingDto.getAnalyzeAdditionalServiceCost());

       Quatation saveQuatation =  quatationRepository.save(quatation);

       AddQuatationAnalyzePricingResponse addQuatationAnalyzePricingResponse = new AddQuatationAnalyzePricingResponse();
       addQuatationAnalyzePricingResponse.setQuatationId(saveQuatation.getQuatationId());
       addQuatationAnalyzePricingResponse.setAnalyzePackageCost(saveQuatation.getAnalyzePackageCost());
       addQuatationAnalyzePricingResponse.setAnalyzeTrsCost(saveQuatation.getAnalyzeTrsCost());
       addQuatationAnalyzePricingResponse.setAnalyzeCarServiceCost(saveQuatation.getAnalyzeCarServiceCost());
       addQuatationAnalyzePricingResponse.setAnalyzeAdditionalServiceCost(saveQuatation.getAnalyzeAdditionalServiceCost());

       return addQuatationAnalyzePricingResponse;
    }



    public AddQuatationAnalyzePricingResponse upadteQuatationAnalyzePricing(Long quatationId, AddQuatationAnalyzePricingDto addQuatationAnalyzePricingDto){
        Quatation quatation = quatationRepository.findById(quatationId)
                .orElseThrow(()-> new ResourceNotFoundException("Quatation with id : "+quatationId+" is not found"));

        quatation.setAnalyzePackageCost(addQuatationAnalyzePricingDto.getAnalyzePackageCost());
        quatation.setAnalyzeTrsCost(addQuatationAnalyzePricingDto.getAnalyzeTrsCost());
        quatation.setAnalyzeCarServiceCost(addQuatationAnalyzePricingDto.getAnalyzeCarServiceCost());
        quatation.setAnalyzeAdditionalServiceCost(addQuatationAnalyzePricingDto.getAnalyzeAdditionalServiceCost());

        Quatation saveQuatation =  quatationRepository.save(quatation);

        AddQuatationAnalyzePricingResponse addQuatationAnalyzePricingResponse = new AddQuatationAnalyzePricingResponse();
        addQuatationAnalyzePricingResponse.setQuatationId(saveQuatation.getQuatationId());
        addQuatationAnalyzePricingResponse.setAnalyzePackageCost(saveQuatation.getAnalyzePackageCost());
        addQuatationAnalyzePricingResponse.setAnalyzeTrsCost(saveQuatation.getAnalyzeTrsCost());
        addQuatationAnalyzePricingResponse.setAnalyzeCarServiceCost(saveQuatation.getAnalyzeCarServiceCost());
        addQuatationAnalyzePricingResponse.setAnalyzeAdditionalServiceCost(saveQuatation.getAnalyzeAdditionalServiceCost());

        return addQuatationAnalyzePricingResponse;
    }


    public ApiResponse sendQuatationAnalyzePricingToQuatationCreator(Long quatationId){
        Quatation quatation = quatationRepository.findById(quatationId)
                .orElseThrow(()-> new ResourceNotFoundException("Quatation with id : "+quatationId+" is not found"));

        Lead lead = quatation.getLead();

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

        // after finalized pricing send email to lead creator (Sales department person)
        notificationService.sendEmailQuatationAfterPricingToSalesExecutives(
                lead,
                quatation,
                needEmailDetails,
                lead.getUser().getUserName(),
                lead.getUser().getEmail()
        );

        // set quatation receive pricing date and time
        quatation.setQuatationReceivedPricingDate(LocalDate.now());
        quatation.setQuatationReceivedPricingTime(LocalTime.now());
        quatation.setQuatationStatus(QuatationStatus.PRICING_FINALIZED_AND_SENT_TO_SALES);
        quatationRepository.save(quatation);

        // ✅ Prepare Response Data
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("leadReferenceNo", lead.getLeadReferenceNo());
        responseData.put("quatationCreatorNotifiedEmailId",lead.getUser().getEmail());

        return new ApiResponse(true,"Quotation Analyze Pricing Send Succefully.",responseData);
    }


}
