package com.mexcorgo.service;


import com.mexcorgo.datamodel.Master;
import com.mexcorgo.datamodel.MasterQuatationResponse;
import com.mexcorgo.dto.request.MasterRequest;
import com.mexcorgo.dto.response.MasterResponse;
import com.mexcorgo.exception.ResourceNotFoundException;
import com.mexcorgo.repository.MasterQuatationResponseRepository;
import com.mexcorgo.repository.MasterRepository;
import com.mexcorgo.repository.QuatationMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MasterService {

           private MasterRepository masterRepository;

           private QuatationMasterRepository quatationMasterRepository;

           private MasterQuatationResponseRepository masterQuatationResponseRepository;

    @Autowired
    public MasterService(MasterRepository masterRepository, QuatationMasterRepository quatationMasterRepository, MasterQuatationResponseRepository masterQuatationResponseRepository) {
        this.masterRepository = masterRepository;
        this.quatationMasterRepository = quatationMasterRepository;
        this.masterQuatationResponseRepository = masterQuatationResponseRepository;
    }

    public MasterResponse addMaster(MasterRequest masterRequest){

        Master newMaster = new Master();
        newMaster = convertMasterRequestIntoMaster(newMaster,masterRequest);

        Master saveMaster = masterRepository.save(newMaster);

        MasterResponse newMasterResponse = new MasterResponse();
        newMasterResponse = convertMasterIntoMasterResponse(saveMaster,newMasterResponse);

        return newMasterResponse;
    }

    public List<MasterResponse> getListOfMaster(){

        List<Master> masterList = masterRepository.findAll();

        List<MasterResponse> masterResponseList = masterList.stream()
                .map(master -> {
                    MasterResponse masterResponse = new MasterResponse();
                    masterResponse = convertMasterIntoMasterResponse(master,masterResponse);
                    return masterResponse;
                }).toList();

        return masterResponseList;

    }


    public MasterResponse updateMaster(Long masterId,MasterRequest masterRequest){
        Master existingMaster = masterRepository.findById(masterId)
                .orElseThrow(()-> new ResourceNotFoundException("Master with id : "+masterId+" is not found"));

        existingMaster = convertMasterRequestIntoMaster(existingMaster,masterRequest);

        Master saveMaster = masterRepository.save(existingMaster);

        MasterResponse masterResponse = new MasterResponse();
        masterResponse = convertMasterIntoMasterResponse(saveMaster,masterResponse);

        return masterResponse;
    }


    public String deleteMaster(Long masterId){
       Master existingMaster = masterRepository.findById(masterId)
               .orElseThrow(()-> new ResourceNotFoundException("Master with id : "+masterId+" is not found"));

       masterRepository.delete(existingMaster);

       return "Master deleted succefully";
    }



    public List<MasterResponse> getQuatationMasterByQuatationId(Long quatationId){
        return quatationMasterRepository.findMasterByQuatationId(quatationId)
                .orElseThrow(()->new ResourceNotFoundException("Master is not added in the quation id : "+quatationId));
    }


    public MasterQuatationResponse getMasterResponse(Long quatationMasterId){
        MasterQuatationResponse masterQuatationResponse = masterQuatationResponseRepository
                .findMasterQuatationResponseByQuatationMasterId(quatationMasterId);

        return masterQuatationResponse;
    }


    private Master convertMasterRequestIntoMaster(Master newMaster,MasterRequest masterRequest){
        newMaster.setAssociateCode(masterRequest.getAssociateCode());
        newMaster.setServiceSector(masterRequest.getServiceSector());
        newMaster.setCompanyName(masterRequest.getCompanyName());
        newMaster.setContactName(masterRequest.getContactName());
        newMaster.setContactNumber(masterRequest.getContactNumber());
        newMaster.setEmailId(masterRequest.getEmailId());
        newMaster.setGrade(masterRequest.getGrade());
        newMaster.setLocation(masterRequest.getLocation());
        newMaster.setHub(masterRequest.getHub());
        newMaster.setState(masterRequest.getState());

        return newMaster;
    }


    private MasterResponse convertMasterIntoMasterResponse(Master master,MasterResponse masterResponse){
        masterResponse.setMasterId(master.getMasterId());
        masterResponse.setAssociateCode(master.getAssociateCode());
        masterResponse.setServiceSector(master.getServiceSector());
        masterResponse.setCompanyName(master.getCompanyName());
        masterResponse.setContactName(master.getContactName());
        masterResponse.setContactNumber(master.getContactNumber());
        masterResponse.setEmailId(master.getEmailId());
        masterResponse.setGrade(master.getGrade());
        masterResponse.setLocation(master.getLocation());
        masterResponse.setHub(master.getHub());
        masterResponse.setState(master.getState());

        return masterResponse;
    }
}
