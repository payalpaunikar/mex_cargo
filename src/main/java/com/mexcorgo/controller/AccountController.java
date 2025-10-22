package com.mexcorgo.controller;


import com.mexcorgo.dto.response.AccountEmpResponse;
import com.mexcorgo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AccountController {

     private AccountService accountService;


    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }


    @GetMapping("/get/account-executive-list")
    @PreAuthorize("hasAnyRole('Admin','Head','Leader','Member','Planning Executive')")
    public List<AccountEmpResponse> getAllAccountExecutiveList(){
        return accountService.getAllAccountExecutiveList();
    }


}
