package com.mexcorgo.controller;


import com.mexcorgo.datamodel.EmailDetails;
import com.mexcorgo.datamodel.Meeting;
import com.mexcorgo.datamodel.PhysicalMeeting;
import com.mexcorgo.datamodel.User;
import com.mexcorgo.dto.request.MeetingRequest;
import com.mexcorgo.dto.request.PhysicalMeetingRequest;
import com.mexcorgo.security.MyUserDetails;
import com.mexcorgo.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class MeetingController {

       private MeetingService meetingService;

    @Autowired
    public MeetingController(MeetingService meetingService) {
        this.meetingService = meetingService;
    }


    @PostMapping("/add/meeting/details")
    @PreAuthorize("hasAnyRole('Admin','Head','Leader','Member')")
    public List<Meeting> addToDoMeetingDetails(@RequestParam("counter")Integer counter, @RequestBody List<MeetingRequest> meetingRequestList, Authentication authentication){

        User currentUser = ((MyUserDetails) authentication.getPrincipal()).getUser();

        return meetingService.addMeetingDetails(counter,meetingRequestList,currentUser);

    }

    @GetMapping("/get/todo/{toDoId}/meeting/details")
    @PreAuthorize("hasAnyRole('Admin','Head','Leader','Member')")
    public List<Meeting> getMeetingDetailsByToDoId(@PathVariable("toDoId") Long toDoId){
        return meetingService.getMeetingDetailsByToDoId(toDoId);
    }

    @GetMapping("/get/meeting/{meetingId}/details")
    @PreAuthorize("hasAnyRole('Admin','Head','Leader','Member')")
    public Meeting getMeetingDetailById(@PathVariable("meetingId") Long meetingId){
        return meetingService.getMeetingDetailsById(meetingId);
    }


    @PutMapping("/update/meeting/{meetingId}/details")
    @PreAuthorize("hasAnyRole('Admin','Head','Leader','Member')")
    public Meeting updateMeetingDetailsById(@PathVariable("meetingId")Long meetingId,MeetingRequest meetingRequest){
        return meetingService.updateMeetingDetailsById(meetingId,meetingRequest);
    }

    @DeleteMapping("/delete/meeting/{meetingId}/details")
    @PreAuthorize("hasAnyRole('Admin','Head','Leader','Member')")
    public ResponseEntity<String> deleteMeetingById(@PathVariable("meetingId")Long meetingId, @RequestParam("toDoId")Long toDoId){
        return ResponseEntity.ok(meetingService.deletedMeetingById(meetingId,toDoId));
    }
}
