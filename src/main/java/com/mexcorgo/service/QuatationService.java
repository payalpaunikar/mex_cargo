package com.mexcorgo.service;


import com.mexcorgo.datamodel.Quatation;
import com.mexcorgo.dto.response.AddQuatationAnalyzePricingResponse;
import com.mexcorgo.exception.ResourceNotFoundException;
import com.mexcorgo.repository.QuatationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuatationService {

       private QuatationRepository quatationRepository;

    @Autowired
    public QuatationService(QuatationRepository quatationRepository) {
        this.quatationRepository = quatationRepository;
    }


    public AddQuatationAnalyzePricingResponse getQuatationAnalyzePricingByQuatationId(Long quatationId){
        Quatation quatation = quatationRepository.findById(quatationId)
                .orElseThrow(()-> new ResourceNotFoundException("Quatation with id : "+quatationId+" is not found"));

        AddQuatationAnalyzePricingResponse addQuatationAnalyzePricingResponse = new AddQuatationAnalyzePricingResponse();
        addQuatationAnalyzePricingResponse.setQuatationId(quatation.getQuatationId());
        addQuatationAnalyzePricingResponse.setAnalyzePackageCost(quatation.getAnalyzePackageCost());
        addQuatationAnalyzePricingResponse.setAnalyzeTrsCost(quatation.getAnalyzeTrsCost());
        addQuatationAnalyzePricingResponse.setAnalyzeCarServiceCost(quatation.getAnalyzeCarServiceCost());
        addQuatationAnalyzePricingResponse.setAnalyzeAdditionalServiceCost(quatation.getAnalyzeAdditionalServiceCost());

        return addQuatationAnalyzePricingResponse;
    }
}
