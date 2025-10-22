package com.mexcorgo.service;


import com.mexcorgo.datamodel.User;
import com.mexcorgo.dto.response.PricingEmpResponse;
import com.mexcorgo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PricingExecutiveService {

       private UserRepository userRepository;

    @Autowired
    public PricingExecutiveService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<PricingEmpResponse> getListOfPricingExecutive(){
        List<User> pricingExecutiveList = userRepository.findAllPricingExecutive();

        List<PricingEmpResponse> pricingEmpResponseList = pricingExecutiveList.stream()
                .map(pricingExecutive->{
                    PricingEmpResponse pricingEmpResponse = new PricingEmpResponse();
                    pricingEmpResponse.setUserId(pricingExecutive.getUserId());
                    pricingEmpResponse.setUserName(pricingExecutive.getUserName());
                    pricingEmpResponse.setEmail(pricingEmpResponse.getEmail());
                    pricingEmpResponse.setMobileNumber(pricingExecutive.getMobileNumber());

                    return pricingEmpResponse;
                }).toList();

        return pricingEmpResponseList;
    }
}
