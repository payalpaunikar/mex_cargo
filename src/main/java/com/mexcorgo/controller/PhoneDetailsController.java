package com.mexcorgo.controller;


import com.mexcorgo.datamodel.Miscellaneous;
import com.mexcorgo.datamodel.PhoneDetails;
import com.mexcorgo.datamodel.User;
import com.mexcorgo.dto.request.PhoneDetailsRequest;
import com.mexcorgo.security.MyUserDetails;
import com.mexcorgo.service.PhoneDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class PhoneDetailsController {

          private PhoneDetailsService phoneDetailsService;

    @Autowired
    public PhoneDetailsController(PhoneDetailsService phoneDetailsService) {
        this.phoneDetailsService = phoneDetailsService;
    }

    @PostMapping("/add/phone/details")
    @PreAuthorize("hasAnyRole('Admin','Head','Leader','Member')")
    public List<PhoneDetails> addToDoPhoneDetailsList(@RequestParam("counter")Integer counter, @RequestBody List<PhoneDetailsRequest> phoneDetailsRequestList, Authentication authentication){

        User currentUser = ((MyUserDetails) authentication.getPrincipal()).getUser();

        return phoneDetailsService.addPhoneDetails(counter,phoneDetailsRequestList,currentUser);
    }


    @GetMapping("/get/todo/{toDoId}/phone/details")
    @PreAuthorize("hasAnyRole('Admin','Head','Leader','Member')")
    public List<PhoneDetails> getPhoneDetailsByToDoId(@PathVariable("toDoId") Long toDoId){
        return phoneDetailsService.getPhoneDetailsByToDoId(toDoId);
    }


    @GetMapping("/get/phone/{phoneId}/details")
    @PreAuthorize("hasAnyRole('Admin','Head','Leader','Member')")
    public PhoneDetails getPhoneDetailById(@PathVariable("phoneId") Long phoneId){
        return phoneDetailsService.getPhoneDetailsById(phoneId);
    }


    @PutMapping("/update/phone/{phoneId}/details")
    @PreAuthorize("hasAnyRole('Admin','Head','Leader','Member')")
    public PhoneDetails updatePhoneDetailsById(@PathVariable("phoneId")Long phoneId,PhoneDetailsRequest phoneDetailsRequest){
        return phoneDetailsService.updatePhoneDetails(phoneId,phoneDetailsRequest);
    }


    @DeleteMapping("/delete/phone/{phoneId}/details")
    @PreAuthorize("hasAnyRole('Admin','Head','Leader','Member')")
    public ResponseEntity<String> deletePhoneDetailsById(@PathVariable("phoneId")Long phoneId,
                                                         @RequestParam("toDoId")Long toDoId){
        return ResponseEntity.ok(phoneDetailsService.deletePhoneDetailsById(phoneId,toDoId));
    }
}
