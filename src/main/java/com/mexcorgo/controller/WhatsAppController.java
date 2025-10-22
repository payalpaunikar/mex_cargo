package com.mexcorgo.controller;


import com.mexcorgo.datamodel.*;
import com.mexcorgo.dto.request.PhoneDetailsRequest;
import com.mexcorgo.dto.request.WhatsAppDetailsRequest;
import com.mexcorgo.security.MyUserDetails;
import com.mexcorgo.service.WhatsAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class WhatsAppController {

       private WhatsAppService whatsAppService;

    @Autowired
    public WhatsAppController(WhatsAppService whatsAppService) {
        this.whatsAppService = whatsAppService;
    }


    @PostMapping("/add/whatsApp/details")
    @PreAuthorize("hasAnyRole('Admin','Head','Leader','Member')")
    public List<WhatsAppDetails> addToDoWhatsAppList(@RequestParam("counter")Integer counter, @RequestBody List<WhatsAppDetailsRequest> whatsAppDetailsRequestList, Authentication authentication){

        User currentUser = ((MyUserDetails) authentication.getPrincipal()).getUser();

        return whatsAppService.addWhatsAppDetailsList(counter,whatsAppDetailsRequestList,currentUser);
    }


    @GetMapping("/get/todo/{toDoId}/whatsapp/details")
    @PreAuthorize("hasAnyRole('Admin','Head','Leader','Member')")
    public List<WhatsAppDetails> getWhatsAppDetailsByToDoId(@PathVariable("toDoId") Long toDoId){
        return whatsAppService.getWhatsAppDetailsByToDoId(toDoId);
    }

    @GetMapping("/get/whatsApp/{whatsAppId}/details")
    @PreAuthorize("hasAnyRole('Admin','Head','Leader','Member')")
    public WhatsAppDetails getWhatsAppDetailById(@PathVariable("whatsAppId") Long whatsAppId){
        return whatsAppService.getWhatsAppDetailsById(whatsAppId);
    }


    @PutMapping("/update/whatsApp/{whatsAppId}/details")
    @PreAuthorize("hasAnyRole('Admin','Head','Leader','Member')")
    public WhatsAppDetails updateWhatsAppDetailsById(@PathVariable("whasAppId")Long whatsAppId,WhatsAppDetailsRequest whatsAppDetailsRequest){
      return whatsAppService.updateWhatsAppDetails(whatsAppId,whatsAppDetailsRequest);
    }

    @DeleteMapping("/delete/whasApp/{whatsAppId}/details")
    @PreAuthorize("hasAnyRole('Admin','Head','Leader','Member')")
    public ResponseEntity<String> deleteWhatsAppDetailById(@PathVariable("whatsAppId")Long whatsAppId,
                                                           @RequestParam("toDoId")Long toDoId){
        return ResponseEntity.ok(whatsAppService.deleteWhatsAppDetailById(whatsAppId,toDoId));
    }
}
