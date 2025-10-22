package com.mexcorgo.service;


import com.mexcorgo.datamodel.User;
import com.mexcorgo.dto.response.AccountEmpResponse;
import com.mexcorgo.dto.response.PlanningEmpResponse;
import com.mexcorgo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

       private UserRepository userRepository;

    @Autowired
    public AccountService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<AccountEmpResponse> getAllAccountExecutiveList(){

        List<User> accountHead = userRepository.findAllAccountHead();

        List<AccountEmpResponse> accountEmpResponses = accountHead.stream()
                .map(accountExecutiveList-> {
                    AccountEmpResponse accountEmpResponse = new AccountEmpResponse();
                    accountEmpResponse.setUserId(accountExecutiveList.getUserId());
                    accountEmpResponse.setEmail(accountExecutiveList.getEmail());
                    accountEmpResponse.setUserName(accountExecutiveList.getUserName());
                    accountEmpResponse.setMobileNumber(accountExecutiveList.getMobileNumber());
                    return accountEmpResponse;
                }).toList();

        return accountEmpResponses;
    }
}
