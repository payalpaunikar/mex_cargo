package com.mexcorgo.controller;


import com.mexcorgo.datamodel.User;
import com.mexcorgo.dto.response.PurchaseEmpResponse;
import com.mexcorgo.service.PurchaseService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/purchase")
@PreAuthorize("hasAnyRole('Admin','Head','Leader','Member')")
public class PurchaseController {

    private PurchaseService purchaseService;

    @Autowired
    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @GetMapping("/emp/list")
    public List<PurchaseEmpResponse> getListOfPurchaseExecutive(){
        return purchaseService.getAllPurchaseExecutiveList();
    }
}
