package com.mexcorgo.service;


import com.mexcorgo.datamodel.User;
import com.mexcorgo.dto.response.PurchaseEmpResponse;
import com.mexcorgo.repository.DepartmentRepository;
import com.mexcorgo.repository.RoleRepository;
import com.mexcorgo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseService {


       private UserRepository userRepository;

       private DepartmentRepository departmentRepository;

       private RoleRepository roleRepository;

    @Autowired
    public PurchaseService(UserRepository userRepository, DepartmentRepository departmentRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.departmentRepository = departmentRepository;
        this.roleRepository = roleRepository;
    }


    public List<PurchaseEmpResponse> getAllPurchaseExecutiveList(){
        List<User> purchaseExecutiveList = userRepository.findAllPurchaseExecutive();

        List<PurchaseEmpResponse> purchaseEmpResponseList = purchaseExecutiveList.stream()
                .map(purchaseExecutive->{
                    PurchaseEmpResponse purchaseEmpResponse = new PurchaseEmpResponse();
                    purchaseEmpResponse.setUserId(purchaseExecutive.getUserId());
                    purchaseEmpResponse.setEmail(purchaseExecutive.getEmail());
                    purchaseEmpResponse.setUserName(purchaseExecutive.getUserName());
                    purchaseEmpResponse.setMobileNumber(purchaseExecutive.getMobileNumber());
                    return purchaseEmpResponse;
                }).toList();

        return purchaseEmpResponseList;
    }
}
