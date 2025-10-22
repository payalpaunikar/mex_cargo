package com.mexcorgo.controller;


import com.mexcorgo.datamodel.User;
import com.mexcorgo.dto.request.EmployeeRequest;
import com.mexcorgo.dto.response.EmployeeResponse;
import com.mexcorgo.security.MyUserDetails;
import com.mexcorgo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class EmployeeController {

         private EmployeeService employeeService;


    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @PostMapping("/emp/register")
    @PreAuthorize("hasAnyRole('Admin','Head')")
    public ResponseEntity<EmployeeResponse> registerTheEmployee(@RequestBody EmployeeRequest employeeRequest, Authentication authentication){
        MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
        User currentUser = myUserDetails.getUser();

        return ResponseEntity.ok(employeeService.registerEmployee(employeeRequest,currentUser));
    }


    @GetMapping("/available/member")
    @PreAuthorize("hasAnyRole('Admin','Head')")
    public List<EmployeeResponse> getListOfAvailableMember(){
       return employeeService.getListOfAvailableMember();
    }


    @GetMapping("/available/leader")
    @PreAuthorize("hasAnyRole('Admin','Head')")
    public List<EmployeeResponse> getListOfAvailableLeader(){
        return employeeService.getListOfAvailableLeaders();
    }


}
