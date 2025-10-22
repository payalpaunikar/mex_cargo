package com.mexcorgo.controller;


import com.mexcorgo.datamodel.*;
import com.mexcorgo.dto.request.PhoneDetailsRequest;
import com.mexcorgo.dto.request.PhysicalMeetingRequest;
import com.mexcorgo.security.MyUserDetails;
import com.mexcorgo.service.PhysicalMeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class PhysicalMeetingController {

       private PhysicalMeetingService physicalMeetingService;

    @Autowired
    public PhysicalMeetingController(PhysicalMeetingService physicalMeetingService) {
        this.physicalMeetingService = physicalMeetingService;
    }


    @PostMapping("/add/physicalmeeting/details")
    @PreAuthorize("hasAnyRole('Admin','Head','Leader','Member')")
    public List<PhysicalMeeting> addToDoPhysicalMeetingList(@RequestParam("counter")Integer counter, @RequestBody List<PhysicalMeetingRequest> physicalMeetingRequestList, Authentication authentication){

        User currentUser = ((MyUserDetails) authentication.getPrincipal()).getUser();

        return physicalMeetingService.addPhysicalMeeting(counter,physicalMeetingRequestList,currentUser);

    }


    @GetMapping("/get/todo/{toDoId}/physicalmeeting/details")
    @PreAuthorize("hasAnyRole('Admin','Head','Leader','Member')")
    public List<PhysicalMeeting> getPhysicalMeetingDetailsByToDoId(@PathVariable("toDoId") Long toDoId){
        return physicalMeetingService.getPhysicalDetailsByToDoId(toDoId);
    }

    @GetMapping("/get/physicalmeeting/{physicalMeetingId}/details")
    @PreAuthorize("hasAnyRole('Admin','Head','Leader','Member')")
    public PhysicalMeeting getPhysicalMeetingDetailById(@PathVariable("physicalMeetingId") Long physicalMeetingId){
        return physicalMeetingService.getPhysicalMeetingDetailsById(physicalMeetingId);
    }

    @PutMapping("/update/physicalmeeting/{physicalMeetingId}/details")
    @PreAuthorize("hasAnyRole('Admin','Head','Leader','Member')")
    public PhysicalMeeting updatePhysicalMeetingDetailsById(@PathVariable("physicalMeetingId")Long physicalMeetingId,PhysicalMeetingRequest physicalMeetingRequest){
       return physicalMeetingService.updatePhysicalMeetingDetails(physicalMeetingId,physicalMeetingRequest);
    }


     @DeleteMapping("/delete/physicalmeeting/{physicalMeetingId}/details")
     @PreAuthorize("hasAnyRole('Admin','Head','Leader','Member')")
     public ResponseEntity<String> deletePhysicalMeetingById(@PathVariable("physicalMeetingId")Long physicalMeetingId,
                                                             @RequestParam("toDoId")Long toDoId){
        return ResponseEntity.ok(physicalMeetingService.deletePhysicalMeetingById(physicalMeetingId,toDoId));
     }
}
