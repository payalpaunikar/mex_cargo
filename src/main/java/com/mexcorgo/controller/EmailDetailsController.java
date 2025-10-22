package com.mexcorgo.controller;


import com.mexcorgo.datamodel.EmailDetails;
import com.mexcorgo.datamodel.PhoneDetails;
import com.mexcorgo.datamodel.User;
import com.mexcorgo.dto.request.EmailDetailsRequest;
import com.mexcorgo.dto.request.PhoneDetailsRequest;
import com.mexcorgo.security.MyUserDetails;
import com.mexcorgo.service.EmailDetailsService;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class EmailDetailsController {

    private EmailDetailsService emailDetailsService;

    @Autowired
    public EmailDetailsController(EmailDetailsService emailDetailsService) {
        this.emailDetailsService = emailDetailsService;
    }


    @PostMapping("/add/email/details")
    @PreAuthorize("hasAnyRole('Admin','Head','Leader','Member')")
    public List<EmailDetails> addToDoEmailDetailsLst(@RequestParam("counter")Integer counter, @RequestBody List<EmailDetailsRequest> emailDetailsRequestList, Authentication authentication){

        User currentUser = ((MyUserDetails) authentication.getPrincipal()).getUser();

        return emailDetailsService.addEmailDetails(counter,emailDetailsRequestList,currentUser);
    }

    @GetMapping("/get/todo/{toDoId}/email/details")
    @PreAuthorize("hasAnyRole('Admin','Head','Leader','Member')")
    public List<EmailDetails> getEmailDetailsByToDoId(@PathVariable("toDoId") Long toDoId){
        return emailDetailsService.getEmailsDetailsByToDoId(toDoId);
    }


    @GetMapping("/get/email/{emailId}/details")
    @PreAuthorize("hasAnyRole('Admin','Head','Leader','Member')")
    public EmailDetails getEmailDetailById(@PathVariable("emailId") Long emailId){
     return emailDetailsService.getEmailDetailsById(emailId);
    }


    @PutMapping("/update/email/{emailId}/details")
    @PreAuthorize("hasAnyRole('Admin','Head','Leader','Member')")
    public EmailDetails updateEmailDetailsById(@PathVariable("emailId")Long emailId, EmailDetailsRequest emailDetailsRequest){
        return emailDetailsService.updateEmailDetailsById(emailId,emailDetailsRequest);
    }


    @DeleteMapping("/delete/email/{emailId}/details")
    @PreAuthorize("hasAnyRole('Admin','Head','Leader','Member')")
    public ResponseEntity<String> deleteEmailDetailsById(@PathVariable("emailId")Long emailId,
                                                         @RequestParam("toDoId")Long toDoId){
        return ResponseEntity.ok(emailDetailsService.deleteEmailDetailsById(emailId,toDoId));
    }
}
